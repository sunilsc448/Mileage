package com.example.android.mileage.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.mileage.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally
                {
//                    SharedPreferences prefs = getSharedPreferences(Extras.PREFERENCE_DIRECTORY, 0);

//                    if (prefs.getBoolean("first_time_app_launch", true)) {
//                        prefs.edit().putBoolean("first_time_app_launch", false).commit();
//
//                        Intent intent = new Intent(SplashActivity.this, AddVehicleActivity.class);
//                        Bundle b = new Bundle();
//                        b.putBoolean("isNoteVisible", true);
//                        intent.putExtras(b);
//                        startActivity(intent);
//                    }
//                    else{
//                        SharedPreferencesHelper.getPreferenceHelperObj().getAllTheValues(SplashActivity.this);
//                        startActivity(new Intent(SplashActivity.this,MainActivity.class));
//                     }

                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                    finish();
                }
            }
        };

        timerThread.start();
    }
}
