package com.example.android.mileage.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.android.mileage.Models.Place;
import com.example.android.mileage.Models.PlacesService;
import com.example.android.mileage.R;
import com.example.android.mileage.Utilities.Extras;
import com.example.android.mileage.Utilities.GPSTracker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.inmobi.ads.InMobiBanner;
import com.inmobi.sdk.InMobiSdk;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    private Spinner myspinner;
    private String[] places;
    private double latitude, longitude;
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InMobiSdk.init(this, Extras.ADS_API_ID);
        setContentView(R.layout.activity_map);

        InMobiBanner banner = (InMobiBanner) findViewById(R.id.banner);
        banner.load();

        places = getResources().getStringArray(R.array.placesTypeArray);

        initCompo();

        gpsFunction();

        myspinner = (Spinner) findViewById(R.id.mySpinner);
        myspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(latitude != 0) {
                    String selectedPlaceType = adapterView.getItemAtPosition(i).toString().toLowerCase().replace(" ", "_");
                    mMap.clear();
                    new GetPlaces(MapActivity.this, selectedPlaceType).execute();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(latitude == 0)
            gpsFunction();
    }

    private void gpsFunction() {
        GPSTracker gps = new GPSTracker(this);
        // gps enabled} // return boolean true/false
        if(gps.canGetLocation())
        {
            if(latitude != 0) {
                latitude = gps.getLatitude(); // returns latitude
                longitude = gps.getLongitude(); // returns longitude
                new GetPlaces(this,places[0].toLowerCase().replace(" ", "_")).execute();
            }
        }else{
            gps.showSettingsAlert();
        }
    }

    private void initCompo() {

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.setTrafficEnabled(true);
        map.setIndoorEnabled(true);
        map.setBuildingsEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
    }

    private class GetPlaces extends AsyncTask<Void, Void,ArrayList<Place>>
    {
        private ProgressDialog dialog;
        private Context context;
        private String places;

        public GetPlaces(Context context, String places) {
            this.context = context;
            this.places = places;
        }

        @Override
        protected void onPostExecute(ArrayList<Place> result) {
            super.onPostExecute(result);
           if (dialog.isShowing()) {
                dialog.dismiss();
            }
            for (int i = 0; i < result.size(); i++) {
                mMap.addMarker(new MarkerOptions()
                        .title(result.get(i).getName())
                        .position(new LatLng(result.get(i).getLatitude(), result.get(i).getLongitude()))
                        .icon(BitmapDescriptorFactory
                                .fromResource(R.drawable.mapmarker))
                        .snippet(result.get(i).getVicinity()));
            }
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(result.get(0).getLatitude(), result
                            .get(0).getLongitude())) // Sets the center of the map to
                    // Mountain View
                    .zoom(14) // Sets the zoom
                    .tilt(30) // Sets the tilt of the camera to 30 degrees
                    .build(); // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new ProgressDialog(context);
            dialog.setCancelable(false);
            dialog.setMessage("Loading..");
            dialog.isIndeterminate();
            dialog.show();
        }

        @Override
        protected ArrayList<Place> doInBackground(Void... arg0) {
            PlacesService service = new PlacesService(Extras.MAP_API_KEY);
            ArrayList<Place> findPlaces = service.findPlaces(latitude, longitude, places);

            for (int i = 0; i < findPlaces.size(); i++) {

                Place placeDetail = findPlaces.get(i);
                Log.e(TAG, "places : " + placeDetail.getName());
            }
            return findPlaces;
        }
    }
}
