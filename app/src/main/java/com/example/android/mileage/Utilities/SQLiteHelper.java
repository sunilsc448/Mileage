package com.example.android.mileage.Utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.mileage.Models.MileageItemModel;
import com.example.android.mileage.Models.VehicleItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sunil Kumar on 7/22/2016.
 */
public class SQLiteHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "mileageDB";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_MILEAGES = "mileages";
    private static final String TABLE_VEHICLES = "vehicles";

    private static final String MILEAGE_ID = "MileageId";
    private static final String FROM_DATE = "FromDate";
    private static final String TO_DATE = "ToDate";
    private static final String MILEAGE = "Mileage";
    private static final String DISTANCE_TRAVELLED = "DistanceTravelled";
    private static final String PETROL_FILLED = "PetrolFilled";
    private static final String AMOUNT_OF_PETROL_FILLED = "AmountOfPetrolFilled";
    private static final String PETROLEUM_BRAND = "PetroleumBrand";
    private static final String LAST_METER_READING = "LastMeterReading";
    private static final String CURRENT_METER_READING = "CurrentMeterReading";
    private static final String PETROL_PRICE = "PetrolPrice";
    private static final String IS_MILEAGE_CHECKED = "IsMileageChecked";
    private static final String VEHICLE_ID = "VehicleId";
    private static final String VEHICLE_TYPE = "VehicleType";
    private static final String VEHICLE_NAME = "VehicleName";


    private static final String[] COLUMNS_MILEAGE = { MILEAGE_ID, FROM_DATE, TO_DATE, MILEAGE,
                                              DISTANCE_TRAVELLED, PETROL_FILLED,
                                              AMOUNT_OF_PETROL_FILLED,PETROLEUM_BRAND,
                                              LAST_METER_READING,CURRENT_METER_READING,
                                              PETROL_PRICE,IS_MILEAGE_CHECKED,
                                              VEHICLE_ID,VEHICLE_TYPE,VEHICLE_NAME};

    private static final String[] COLUMNS_VEHICLE = {VEHICLE_ID,VEHICLE_NAME,VEHICLE_TYPE};

    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_MILEAGES);
        this.onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        createMileageTable(db);
        createVehicleTable(db);
    }

    //Mileage Table Methods
    private void createMileageTable(SQLiteDatabase db) {
        String CREATE_MILEAGE_TABLE = "CREATE TABLE "+TABLE_MILEAGES+" "+
                "( " +
                MILEAGE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                FROM_DATE+" DATETIME, "+
                TO_DATE+" DATETIME, "+
                MILEAGE+" REAL, "+
                DISTANCE_TRAVELLED+" REAL, " +
                PETROL_FILLED+" REAL, " +
                AMOUNT_OF_PETROL_FILLED+" REAL, " +
                PETROLEUM_BRAND+" TEXT NOT NULL, " +
                LAST_METER_READING+" REAL, " +
                CURRENT_METER_READING+" REAL, " +
                PETROL_PRICE+" REAL, " +
                IS_MILEAGE_CHECKED+" INTEGER, " +
                VEHICLE_ID+" TEXT NOT NULL, " +
                VEHICLE_TYPE+" TEXT NOT NULL, " +
                VEHICLE_NAME+" TEXT NOT NULL" +
                " )";
        db.execSQL(CREATE_MILEAGE_TABLE);
    }

    public void createMileageItem(MileageItemModel mileageItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        // make values to be inserted
        ContentValues values = new ContentValues();

        if(mileageItem.getFromDate() != null)
            values.put(FROM_DATE, Extras.getExtraClassObj().getFormattedDate(mileageItem.getFromDate()));
        else
            values.put(FROM_DATE,"");
        if(mileageItem.getToDate() != null)
            values.put(TO_DATE, Extras.getExtraClassObj().getFormattedDate(mileageItem.getToDate()));
        else
            values.put(TO_DATE,"");
        values.put(MILEAGE, mileageItem.getMileage());
        values.put(DISTANCE_TRAVELLED, mileageItem.getDistanceTravelled());
        values.put(PETROL_FILLED, mileageItem.getPetrolFilled());
        values.put(AMOUNT_OF_PETROL_FILLED, mileageItem.getAmountOfPetrolFilled());
        values.put(PETROLEUM_BRAND, mileageItem.getPetroleumBrand());
        values.put(LAST_METER_READING, mileageItem.getLastmeterReading());
        values.put(CURRENT_METER_READING, mileageItem.getCurrentmeterReading());
        values.put(PETROL_PRICE, mileageItem.getPetrolPrice());
        values.put(IS_MILEAGE_CHECKED, mileageItem.getIsMileageChecked());
        values.put(VEHICLE_ID, mileageItem.getVehicleId());
        values.put(VEHICLE_TYPE, mileageItem.getVehicleType());
        values.put(VEHICLE_NAME, mileageItem.getVehicleName());

        // insert mileage item
        db.insert(TABLE_MILEAGES, null, values);

        db.close();
    }

    public int updateMileageItem(MileageItemModel mileageItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        // make values to be inserted
        ContentValues values = new ContentValues();
        values.put(MILEAGE_ID, mileageItem.getMileageId());
        if(mileageItem.getFromDate() != null)
            values.put(FROM_DATE, Extras.getExtraClassObj().getFormattedDate(mileageItem.getFromDate()));
        else
            values.put(FROM_DATE,"");
        if(mileageItem.getToDate() != null)
            values.put(TO_DATE, Extras.getExtraClassObj().getFormattedDate(mileageItem.getToDate()));
        else
            values.put(TO_DATE,"");
        values.put(MILEAGE, mileageItem.getMileage());
        values.put(DISTANCE_TRAVELLED, mileageItem.getDistanceTravelled());
        values.put(PETROL_FILLED, mileageItem.getPetrolFilled());
        values.put(AMOUNT_OF_PETROL_FILLED, mileageItem.getAmountOfPetrolFilled());
        values.put(PETROLEUM_BRAND, mileageItem.getPetroleumBrand());
        values.put(LAST_METER_READING, mileageItem.getLastmeterReading());
        values.put(CURRENT_METER_READING, mileageItem.getCurrentmeterReading());
        values.put(PETROL_PRICE, mileageItem.getPetrolPrice());
        values.put(IS_MILEAGE_CHECKED, mileageItem.getIsMileageChecked());
        values.put(VEHICLE_ID, mileageItem.getVehicleId());
        values.put(VEHICLE_TYPE, mileageItem.getVehicleType());
        values.put(VEHICLE_NAME, mileageItem.getVehicleName());

        // update
        int i = db.update(TABLE_MILEAGES, values, MILEAGE_ID + " = ?", new String[] { String.valueOf(mileageItem.getMileageId()) });
        db.close();
        return i;
    }

    public MileageItemModel getMileageItem(int mileageId) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(TABLE_MILEAGES,
                COLUMNS_MILEAGE, MILEAGE_ID+" = ?", new String[] { String.valueOf(mileageId) }, null, null, null, null);

        // if results !=null, parse the first one
        if (cursor != null)
            cursor.moveToFirst();

        MileageItemModel mileageItem = new MileageItemModel();
        mileageItem.setMileageId(cursor.getInt(0));
        mileageItem.setFromDate(Extras.getExtraClassObj().getDateFromStr(cursor.getString(1)));
        mileageItem.setToDate(Extras.getExtraClassObj().getDateFromStr(cursor.getString(2)));
        mileageItem.setMileage(cursor.getFloat(3));
        mileageItem.setDistanceTravelled(cursor.getFloat(4));
        mileageItem.setPetrolFilled(cursor.getFloat(5));
        mileageItem.setAmountOfPetrolFilled(cursor.getFloat(6));
        mileageItem.setPetroleumBrand(cursor.getString(7));
        mileageItem.setLastmeterReading(cursor.getFloat(8));
        mileageItem.setCurrentmeterReading(cursor.getFloat(9));
        mileageItem.setPetrolPrice(cursor.getFloat(10));
        mileageItem.setIsMileageChecked(cursor.getInt(11));
        mileageItem.setVehicleId(cursor.getString(12));
        mileageItem.setVehicleType(cursor.getString(13));
        mileageItem.setVehicleName(cursor.getString(14));

        return mileageItem;
    }

    public List<MileageItemModel> getAllMileageItems() {
        List<MileageItemModel> mileageItems = new ArrayList<MileageItemModel>();

        // select mileages query
        String query = "SELECT  * FROM " + TABLE_MILEAGES;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        // parse all results
        if (cursor.moveToFirst()) {
            do {
                MileageItemModel mileageItem = new MileageItemModel();
                mileageItem.setMileageId(cursor.getInt(0));
                mileageItem.setFromDate(Extras.getExtraClassObj().getDateFromStr(cursor.getString(1)));
                mileageItem.setToDate(Extras.getExtraClassObj().getDateFromStr(cursor.getString(2)));
                mileageItem.setMileage(cursor.getFloat(3));
                mileageItem.setDistanceTravelled(cursor.getFloat(4));
                mileageItem.setPetrolFilled(cursor.getFloat(5));
                mileageItem.setAmountOfPetrolFilled(cursor.getFloat(6));
                mileageItem.setPetroleumBrand(cursor.getString(7));
                mileageItem.setLastmeterReading(cursor.getFloat(8));
                mileageItem.setCurrentmeterReading(cursor.getFloat(9));
                mileageItem.setPetrolPrice(cursor.getFloat(10));
                mileageItem.setIsMileageChecked(cursor.getInt(11));
                mileageItem.setVehicleId(cursor.getString(12));
                mileageItem.setVehicleType(cursor.getString(13));
                mileageItem.setVehicleName(cursor.getString(14));

                mileageItems.add(mileageItem);
            } while (cursor.moveToNext());
        }
        return mileageItems;
    }

    public List<MileageItemModel> getAllMileageItemsBasedOnId(String vehicleid) {
        List<MileageItemModel> mileageItems = new ArrayList<MileageItemModel>();

        // select mileages query
        String query = "SELECT  * FROM "+TABLE_MILEAGES +" where "+VEHICLE_ID+" = '"+vehicleid+"'" ;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        // parse all results
        if (cursor.moveToFirst()) {
            do {
                MileageItemModel mileageItem = new MileageItemModel();
                mileageItem.setMileageId(cursor.getInt(0));
                mileageItem.setFromDate(Extras.getExtraClassObj().getDateFromStr(cursor.getString(1)));
                mileageItem.setToDate(Extras.getExtraClassObj().getDateFromStr(cursor.getString(2)));
                mileageItem.setMileage(cursor.getFloat(3));
                mileageItem.setDistanceTravelled(cursor.getFloat(4));
                mileageItem.setPetrolFilled(cursor.getFloat(5));
                mileageItem.setAmountOfPetrolFilled(cursor.getFloat(6));
                mileageItem.setPetroleumBrand(cursor.getString(7));
                mileageItem.setLastmeterReading(cursor.getFloat(8));
                mileageItem.setCurrentmeterReading(cursor.getFloat(9));
                mileageItem.setPetrolPrice(cursor.getFloat(10));
                mileageItem.setIsMileageChecked(cursor.getInt(11));
                mileageItem.setVehicleId(cursor.getString(12));
                mileageItem.setVehicleType(cursor.getString(13));
                mileageItem.setVehicleName(cursor.getString(14));

                mileageItems.add(mileageItem);
            } while (cursor.moveToNext());
        }
        return mileageItems;
    }


    public void deleteMileageItem(MileageItemModel mileageItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MILEAGES, MILEAGE_ID + " = ?", new String[]{String.valueOf(mileageItem.getMileageId())});
        db.close();
    }

    public void deleteAllMileageItems() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_MILEAGES);
        db.close();
    }

    //Vehicle Table Methods
    private void createVehicleTable(SQLiteDatabase db) {
        String CREATE_VEHICLE_TABLE = "CREATE TABLE "+TABLE_VEHICLES+" "+
                "( " +
                VEHICLE_ID+" TEXT PRIMARY KEY, "+
                VEHICLE_NAME+" TEXT NOT NULL, "+
                VEHICLE_TYPE+" TEXT NOT NULL" +
                " )";
        db.execSQL(CREATE_VEHICLE_TABLE);
    }

    public void createVehicleItem(VehicleItemModel vehicleItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        // make values to be inserted
        ContentValues values = new ContentValues();

        values.put(VEHICLE_ID, vehicleItem.getVehicleId());
        values.put(VEHICLE_NAME, vehicleItem.getVehicleName());
        values.put(VEHICLE_TYPE, vehicleItem.getVehicleType());

        // insert vehicle item
        db.insert(TABLE_VEHICLES, null, values);

        db.close();
    }

    public int updateVehicleItem(VehicleItemModel vehicleItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        // make values to be inserted
        ContentValues values = new ContentValues();

        values.put(VEHICLE_ID, vehicleItem.getVehicleId());
        values.put(VEHICLE_NAME, vehicleItem.getVehicleName());
        values.put(VEHICLE_TYPE, vehicleItem.getVehicleType());

        // update
        int i = db.update(TABLE_VEHICLES, values, VEHICLE_ID + " = ?", new String[] { String.valueOf(vehicleItem.getVehicleId()) });
        db.close();
        return i;
    }

    public VehicleItemModel getVehicleItem(int vehicleId) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(TABLE_VEHICLES,
                COLUMNS_VEHICLE, VEHICLE_ID+" = ?", new String[] { String.valueOf(vehicleId) }, null, null, null, null);

        // if results !=null, parse the first one
        if (cursor != null)
            cursor.moveToFirst();

        VehicleItemModel vehicleItem = new VehicleItemModel();
        vehicleItem.setVehicleId(cursor.getString(0));
        vehicleItem.setVehicleName(cursor.getString(1));
        vehicleItem.setVehicleType(cursor.getString(2));

        return vehicleItem;
    }

    public List<VehicleItemModel> getAllVehicleItems() {
        List<VehicleItemModel> vehicleItems = new ArrayList<VehicleItemModel>();

        // select mileages query
        String query = "SELECT  * FROM " + TABLE_VEHICLES;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        // parse all results
        if (cursor.moveToFirst()) {
            do {
                VehicleItemModel vehicleItem = new VehicleItemModel();
                vehicleItem.setVehicleId(cursor.getString(0));
                vehicleItem.setVehicleName(cursor.getString(1));
                vehicleItem.setVehicleType(cursor.getString(2));

                vehicleItems.add(vehicleItem);
            } while (cursor.moveToNext());
        }
        return vehicleItems;
    }

    public void deleteVehicleItem(VehicleItemModel vehicleItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_VEHICLES, VEHICLE_ID + " = ?", new String[]{String.valueOf(vehicleItem.getVehicleId())});
        db.close();
    }

    public void deleteAllVehicleItems() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_VEHICLES);
        db.close();
    }
}
