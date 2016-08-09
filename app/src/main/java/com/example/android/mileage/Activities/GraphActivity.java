package com.example.android.mileage.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.android.mileage.Adapters.GraphViewPagerAdapter;
import com.example.android.mileage.Adapters.VehiclesSpinnerAdapter;
import com.example.android.mileage.Fragments.GraphFragment;
import com.example.android.mileage.Interfaces.AdapterToActivityCallBack;
import com.example.android.mileage.Models.VehicleItemModel;
import com.example.android.mileage.R;
import com.example.android.mileage.Utilities.Extras;
import com.example.android.mileage.Utilities.SQLiteHelper;
import com.inmobi.ads.InMobiBanner;
import com.inmobi.sdk.InMobiSdk;

import java.util.List;

public class GraphActivity extends AppCompatActivity implements AdapterToActivityCallBack {
    private String vehicleId;
    private int viewPagePos;
    private List<VehicleItemModel> vehicles ;
    SQLiteHelper db = new SQLiteHelper(this,null,null,0);
    GraphViewPagerAdapter graphViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InMobiSdk.init(this, Extras.ADS_API_ID);
        setContentView(R.layout.activity_graph);

        InMobiBanner banner = (InMobiBanner) findViewById(R.id.banner);
        banner.load();

        Intent intent = getIntent();
        int vehiclePos = 0;
        if(intent!=null)
            vehiclePos = intent.getIntExtra("vehicleIdPos", 0);

        vehicles = db.getAllVehicleItems();
        vehicleId = vehicles.get(vehiclePos).getVehicleId();
        VehiclesSpinnerAdapter vehiclesSpinnerAdapter = new VehiclesSpinnerAdapter(this,"Form");
        vehiclesSpinnerAdapter.UpdateData(vehicles);
        Spinner spinner1 = (Spinner)findViewById(R.id.vehicleSpinner);
        spinner1.setAdapter(vehiclesSpinnerAdapter);
        spinner1.setSelection(vehiclePos);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                vehicleId = vehicles.get(position).getVehicleId();
                graphViewPagerAdapter.updateVehicleId(vehicleId);
                GraphFragment fragment = graphViewPagerAdapter.getLstFragments(viewPagePos);
                fragment.renderBar(viewPagePos, vehicleId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        graphViewPagerAdapter  = new GraphViewPagerAdapter(getSupportFragmentManager());
        graphViewPagerAdapter.updateVehicleId(vehicleId);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPagerLyt);
        viewPager.setAdapter(graphViewPagerAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPagePos = position;
                graphViewPagerAdapter.updateVehicleId(vehicleId);
                GraphFragment fragment = graphViewPagerAdapter.getLstFragments(viewPagePos);
                fragment.renderBar(viewPagePos, vehicleId);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLyt);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void DeleteVehicle(int position) {

    }
}
