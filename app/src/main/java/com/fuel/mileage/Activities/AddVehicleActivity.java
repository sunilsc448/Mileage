package com.fuel.mileage.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.fuel.mileage.Adapters.PetroleumBrandsSpinnerAdapter;
import com.fuel.mileage.Models.VehicleItemModel;
import com.fuel.mileage.R;
import com.fuel.mileage.Utilities.Extras;
import com.fuel.mileage.Utilities.SQLiteHelper;
import com.inmobi.ads.InMobiBanner;
import com.inmobi.sdk.InMobiSdk;

import java.util.List;

public class AddVehicleActivity extends AppCompatActivity {
    private String vehicleType;
    private SQLiteHelper db = new SQLiteHelper(this,null,null,0);
    private List<VehicleItemModel> lst_vehicles ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InMobiSdk.init(this, Extras.ADS_API_ID);
        setContentView(R.layout.activity_pre_main);

        InMobiBanner banner = (InMobiBanner) findViewById(R.id.banner);
        banner.load();

        lst_vehicles = db.getAllVehicleItems();

        Spinner spinner1 = (Spinner)findViewById(R.id.vehicleTypeSpinner);
        spinner1.setAdapter(new PetroleumBrandsSpinnerAdapter(this, 1));
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                vehicleType = "v" + position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button saveeBtn = (Button)findViewById(R.id.saveBtn);
        saveeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String vehicleName = ((EditText)findViewById(R.id.vehicleName)).getText().toString();
                final String vehicleId = ((EditText)findViewById(R.id.vehicleId)).getText().toString();

                if(!vehicleName.isEmpty() && !vehicleId.isEmpty())
                {
                    for (int i = 0; i < lst_vehicles.size(); i++) {
                        if(vehicleId.equalsIgnoreCase(lst_vehicles.get(i).getVehicleId()))
                        {
                            Toast.makeText(AddVehicleActivity.this, getResources().getString(R.string.msg_vehicle_already_exist),Toast.LENGTH_LONG).show();
                            return;
                        }
                    }

                    VehicleItemModel vehicleItem = new VehicleItemModel();
                    vehicleItem.setVehicleName(vehicleName);
                    vehicleItem.setVehicleId(vehicleId);
                    vehicleItem.setVehicleType(vehicleType);

                    db.createVehicleItem(vehicleItem);

                    finish();
                }else{
                    Toast.makeText(AddVehicleActivity.this, getResources().getString(R.string.msg_enter_the_details_fully), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
