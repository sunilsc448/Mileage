//package com.example.android.mileage.Utilities;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//
//import java.util.Date;
//
///**
// * Created by Sunil Kumar on 7/25/2016.
// */
//public class SharedPreferencesHelper
//{
//    private static SharedPreferencesHelper preferenceHelperObj;
//    public static SharedPreferencesHelper getPreferenceHelperObj()
//    {
//        if(preferenceHelperObj == null)
//            preferenceHelperObj = new SharedPreferencesHelper();
//
//        return preferenceHelperObj;
//    }
//
//    public void getAllTheValues(Context context)
//    {
//        SharedPreferences prefs = context.getSharedPreferences(Extras.PREFERENCE_DIRECTORY, 0);
//        Extras.PETROL_PRICE = prefs.getFloat(Extras.PETROL_PRICE_KEY,65.0f);
//        Extras.LAST_METER_READING = prefs.getFloat(Extras.LAST_METER_READING_KEY,0f);
//        Extras.LAST_METER_READING_DATE = new Date(prefs.getLong(Extras.LAST_METER_READING_DATE_KEY,0));
//    }
//
//    public void saveAllThEvalues(Context context,float pricePerLr,float currentMtrReading,long currentdate)
//    {
//        SharedPreferences.Editor editor = context.getSharedPreferences(Extras.PREFERENCE_DIRECTORY, 0).edit();
//        editor.putFloat(Extras.PETROL_PRICE_KEY, pricePerLr);
//        editor.putFloat(Extras.LAST_METER_READING_KEY, currentMtrReading);
//        editor.putLong(Extras.LAST_METER_READING_DATE_KEY, currentdate);
//        editor.commit();
//
//        getAllTheValues(context);
//    }
//
//}
