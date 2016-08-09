package com.example.android.mileage.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.mileage.Adapters.MileageListAdapter;
import com.example.android.mileage.Adapters.VehiclesSpinnerAdapter;
import com.example.android.mileage.Interfaces.AdapterToActivityCallBack;
import com.example.android.mileage.Models.MileageItemModel;
import com.example.android.mileage.Models.VehicleItemModel;
import com.example.android.mileage.R;
import com.example.android.mileage.Utilities.Extras;
import com.example.android.mileage.Utilities.SQLiteHelper;
import com.inmobi.ads.InMobiBanner;
import com.inmobi.sdk.InMobiSdk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterToActivityCallBack
{
    private static final int ACCESS_FINE_LOCATION = 123;
    private List<MileageItemModel> listMileageItems = new ArrayList<MileageItemModel>();
    private SQLiteHelper db = new SQLiteHelper(this,null,null,0);
    private MileageListAdapter mileageListAdapter;
    private Spinner spinner;
    private VehiclesSpinnerAdapter vehiclesSpinnerAdapter;
    private List<VehicleItemModel> listVehicleItems ;
    int vehiclePosition;
    int vehicleCountStored = 0;
    boolean isLocationRequestGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        InMobiSdk.init(this, Extras.ADS_API_ID);
        setContentView(R.layout.activity_main);

        InMobiBanner banner = (InMobiBanner) findViewById(R.id.banner);
        banner.load();

        updateNoItemsTxt();

        RecyclerView recylerVw = (RecyclerView)findViewById(R.id.recylerViewPhotoProducts);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recylerVw.setLayoutManager(mLayoutManager);
        recylerVw.setItemAnimator(new DefaultItemAnimator());
        mileageListAdapter = new MileageListAdapter(this);
        recylerVw.setAdapter(mileageListAdapter);


        spinner = (Spinner)findViewById(R.id.vehicleSpinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                    vehiclePosition = position;
                    VehicleItemModel vehicleItem = listVehicleItems.get(position);

                    if (vehicleItem.getVehicleId() != getResources().getString(R.string.add_new_vehicle_item_id)) {
                        displayMlgItemsBasedOnId(vehicleItem.getVehicleId());
                    }else{
                        startActivity(new Intent(MainActivity.this, AddVehicleActivity.class));
                        setVehicleSpinnerSelection();
                    }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private int toPixelUnits(int dipUnit) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dipUnit * density);
    }

    private void updateNoItemsTxt() {
        if(listMileageItems.size() == 0)
            ((TextView)findViewById(R.id.noItemsTxt)).setVisibility(View.VISIBLE);
        else
            ((TextView)findViewById(R.id.noItemsTxt)).setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        listVehicleItems = new ArrayList<VehicleItemModel>();

        //Vehicle items
        List<VehicleItemModel> lst = db.getAllVehicleItems();
        for (int i = 0; i < lst.size()  ; i++) {
            listVehicleItems.add(lst.get(i));
        }
        //Add new vehicle item
        VehicleItemModel addNewVehicleItem = new VehicleItemModel();
        addNewVehicleItem.setVehicleName(getResources().getString(R.string.add_new_vehicle));
        addNewVehicleItem.setVehicleType("add_icon_black");
        addNewVehicleItem.setVehicleId(getResources().getString(R.string.add_new_vehicle_item_id));
        listVehicleItems.add(addNewVehicleItem);

        vehiclesSpinnerAdapter = new VehiclesSpinnerAdapter(this,"Main");
        vehiclesSpinnerAdapter.UpdateData(listVehicleItems);
        spinner.setAdapter(vehiclesSpinnerAdapter);

        if(vehicleCountStored != listVehicleItems.size()) {
            setVehicleSpinnerSelection();
            vehicleCountStored = listVehicleItems.size();
        }
        else
            spinner.setSelection(vehiclePosition);
    }

    private void setVehicleSpinnerSelection() {

        if(listVehicleItems.size() == 1)
            spinner.setSelection(0);
        else
            spinner.setSelection(listVehicleItems.size()-2);
    }

    private void displayMlgItemsBasedOnId(String Id)
    {
        //Mileage items
        listMileageItems = db.getAllMileageItemsBasedOnId(Id);
        Collections.reverse(listMileageItems);
        mileageListAdapter.UpdateData(listMileageItems);

        updateNoItemsTxt();
    }

    @Override
    public void DeleteVehicle(int position)
    {
        VehicleItemModel vehicleItem = listVehicleItems.get(position);
        db.deleteVehicleItem(vehicleItem);
        listVehicleItems.remove(position);

        for (int i = 0; i < listMileageItems.size(); i++) {
            if(listMileageItems.get(i).getVehicleId().equalsIgnoreCase(vehicleItem.getVehicleId()))
                db.deleteMileageItem(listMileageItems.get(i));
        }

        mileageListAdapter.UpdateData(listMileageItems);
        vehiclesSpinnerAdapter.UpdateData(listVehicleItems);

        setVehicleSpinnerSelection();
        vehicleCountStored = 0;

        if(listVehicleItems.size() == 1)
        {
            startActivity(new Intent(MainActivity.this, AddVehicleActivity.class));
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Intent intent = new Intent(this, MapActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(this,getResources().getString(R.string.mas_no_map_permission),Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Bundle b = new Bundle();
        b.putInt("vehicleIdPos", vehiclePosition);

        //noinspection SimplifiableIfStatement
        if(id == R.id.action_add){
            Intent intent = new Intent(this, FormActivity.class);
            intent.putExtras(b);
            startActivity(intent);
        }else if(id == R.id.action_graph){
            Intent intent = new Intent(this, GraphActivity.class);
            intent.putExtras(b);
            startActivity(intent);
        }
        else if(id == R.id.action_map){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                    && (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},123);
            }else{
                Intent intent = new Intent(this, MapActivity.class);
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
