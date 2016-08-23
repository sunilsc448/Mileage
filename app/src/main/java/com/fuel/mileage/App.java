package com.fuel.mileage;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;

import com.fuel.mileage.Utilities.Extras;

import java.util.Calendar;

/**
 * Created by Sunil Kumar on 7/25/2016.
 */
public class App extends Application
{
    @Override
    public void onCreate() {
        super.onCreate();
        setNotification();
    }

    public void setNotification() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(this.ALARM_SERVICE);

        Intent notificationIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
        notificationIntent.addCategory("android.intent.category.DEFAULT");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, Extras.ALARM_MANAGER_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 4);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 4 * 86400000, pendingIntent);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
