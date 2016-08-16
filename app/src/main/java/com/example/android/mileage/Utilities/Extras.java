package com.example.android.mileage.Utilities;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Sunil Kumar on 7/22/2016.
 */
public class Extras {
//    public static final String PREFERENCE_DIRECTORY  = "mileage_details";
//
//    public static final String PETROL_PRICE_KEY  = "petrol_price_key";
//    public static final String LAST_METER_READING_KEY  = "last_meter_reading_key";
//    public static final String LAST_METER_READING_DATE_KEY  = "last_meter_reading_date_key";
//
//    public static float PETROL_PRICE ;
//    public static float LAST_METER_READING ;
//    public static Date LAST_METER_READING_DATE;

    private static Extras extraClassObj;
    public static final int NOTIFICATION_ID  = 1001;
    public static final int ALARM_MANAGER_ID  = 1100;
    public static final long Ads_PLACEMENT_ID = 1469048772162L;
    public static final String MAP_API_KEY = "AIzaSyCAitSkMY_koWNH_jhFAWR9RgVkwQ_WgpI";
    public static final String ADS_API_ID = "a9cd59f0b0de49ea8f315504a7a46709";
    public static final int PERMISSION_REQUEST_CODE_LOCATION = 1;
    public static final int PERMISSION_REQUEST_CODE_GPS_SETTINGS = 2;


    public static Extras getExtraClassObj() {
        if (extraClassObj == null)
            extraClassObj = new Extras();

        return extraClassObj;
    }

    public String getCurrentFormattedDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        return df.format(c.getTime());
    }

    public Date getCurrentDate() {
        Calendar c = Calendar.getInstance();
        return c.getTime();
    }

    public String getFormattedDate(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        return df.format(date);
    }

    public String getFormattedShortDateForGraph(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("ddMMMyy");
        return df.format(date);
    }

    public String getFormattedDateFromYMD(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year,month,day);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        return df.format(cal.getTime());
    }

    public Date getDateFromStr(String strDate)
    {
        Date date = null;
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        df.setLenient(false);
        try {
            date =  df.parse(strDate);
        } catch (ParseException e) {}

        return date;
    }

    public Integer getImageId( Context context, String name) {
       Integer id = context.getResources().getIdentifier(name, "drawable", context.getPackageName());
        return id;
    }

    public static boolean checkPermission(String strPermission, Context _c, Activity _a) {
        int result = ContextCompat.checkSelfPermission(_c, strPermission);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
}