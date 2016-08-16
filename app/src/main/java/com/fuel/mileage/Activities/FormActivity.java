package com.fuel.mileage.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.fuel.mileage.Adapters.PetroleumBrandsSpinnerAdapter;
import com.fuel.mileage.Adapters.VehiclesSpinnerAdapter;
import com.fuel.mileage.Interfaces.AdapterToActivityCallBack;
import com.fuel.mileage.Models.MileageItemModel;
import com.fuel.mileage.Models.VehicleItemModel;
import com.fuel.mileage.R;
import com.fuel.mileage.Utilities.Extras;
import com.fuel.mileage.Utilities.SQLiteHelper;
import com.inmobi.ads.InMobiBanner;
import com.inmobi.sdk.InMobiSdk;

import java.util.Calendar;
import java.util.List;

public class FormActivity extends AppCompatActivity implements AdapterToActivityCallBack{

    SQLiteHelper db = new SQLiteHelper(this,null,null,0);

    private EditText amntOfPtrlFilled;
    private EditText ptrlPricePerLtr;
    private EditText ptrlFilledInLtrs;
    private Button saveBtn;
    private EditText curretMeterReadinginKms;
    private EditText datePickerBox;
    private String petroleumBrand;
    private int vehiclePos;
    private List<VehicleItemModel> vehicles ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InMobiSdk.init(this, Extras.ADS_API_ID);
        setContentView(R.layout.activity_form);

        InMobiBanner banner = (InMobiBanner) findViewById(R.id.banner);
        banner.load();

        Intent intent = getIntent();
        if(intent!=null)
            vehiclePos =  intent.getIntExtra("vehicleIdPos",0);

        vehicles = db.getAllVehicleItems();
        VehiclesSpinnerAdapter vehiclesSpinnerAdapter = new VehiclesSpinnerAdapter(this,"Form");
        vehiclesSpinnerAdapter.UpdateData(vehicles);
        Spinner spinner1 = (Spinner)findViewById(R.id.vehicleSpinner);
        spinner1.setAdapter(vehiclesSpinnerAdapter);

        spinner1.setSelection(vehiclePos);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                vehiclePos = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner spinner2 = (Spinner)findViewById(R.id.petroleumCompanySpinner);
        spinner2.setAdapter(new PetroleumBrandsSpinnerAdapter(this,2));
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                petroleumBrand = "p"+position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        amntOfPtrlFilled = (EditText)findViewById(R.id.amntOfPtrlFilled);
        amntOfPtrlFilled.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                calculatePriceForPetrol();
            }
        });

        ptrlPricePerLtr = (EditText)findViewById(R.id.ptrlPricePerLtr);
        ptrlPricePerLtr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                calculatePriceForPetrol();
            }
        });

        curretMeterReadinginKms = (EditText)findViewById(R.id.curretMeterReadinginKms);

        ptrlFilledInLtrs = (EditText)findViewById(R.id.ptrlFilledInLtrs);

        saveBtn = (Button)findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveTheDataToDB();
            }
        });

        datePickerBox = (EditText)findViewById(R.id.datePickerBox);
        datePickerBox.setText(Extras.getExtraClassObj().getCurrentFormattedDate());
        datePickerBox.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Calendar c = Calendar.getInstance();
                new DatePickerDialog(FormActivity.this, datePickerListener, c
                        .get(Calendar.YEAR), c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            datePickerBox.setText(Extras.getExtraClassObj().getFormattedDateFromYMD(year, monthOfYear, dayOfMonth));
        }
    };

    private void calculatePriceForPetrol()
    {
        String amountPaid = amntOfPtrlFilled.getText().toString();
        String pricePerLtr = ptrlPricePerLtr.getText().toString();
        if(!amountPaid.isEmpty() && !pricePerLtr.isEmpty())
        {
            Float flt = Float.parseFloat(amountPaid)/Float.parseFloat(pricePerLtr);
            ptrlFilledInLtrs.setText(String.format("%.2f", flt));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if(id == R.id.action_save)
        {
            SaveTheDataToDB();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void SaveTheDataToDB() {
        String amnt = amntOfPtrlFilled.getText().toString();
        String strtDt = datePickerBox.getText().toString();
        String crrntMtrRdng = curretMeterReadinginKms.getText().toString();
        String ptrlFlld = ptrlFilledInLtrs.getText().toString();
        String pricePerLtr = ptrlPricePerLtr.getText().toString();
        if(!amnt.isEmpty() && !strtDt.isEmpty() && !crrntMtrRdng.isEmpty() && !ptrlFlld.isEmpty() && !pricePerLtr.isEmpty()) {
            MileageItemModel mileageItem = new MileageItemModel();
            mileageItem.setAmountOfPetrolFilled(Float.parseFloat(amnt));
            mileageItem.setFromDate(Extras.getExtraClassObj().getDateFromStr(strtDt));
            mileageItem.setPetroleumBrand(petroleumBrand);
            mileageItem.setVehicleType(vehicles.get(vehiclePos).getVehicleType());
            mileageItem.setVehicleId(vehicles.get(vehiclePos).getVehicleId());
            mileageItem.setVehicleName(vehicles.get(vehiclePos).getVehicleName());
            mileageItem.setPetrolFilled(mileageItem.getAmountOfPetrolFilled() / Float.parseFloat((pricePerLtr)));
            mileageItem.setLastmeterReading(Float.parseFloat(crrntMtrRdng));
            mileageItem.setPetrolPrice(Float.parseFloat(pricePerLtr));
            mileageItem.setCurrentmeterReading(0);
            mileageItem.setDistanceTravelled(0);
            mileageItem.setMileage(0);
            mileageItem.setToDate(null);
            mileageItem.setIsMileageChecked(0);
            db.createMileageItem(mileageItem);
            finish();
        }else{
            Toast.makeText(this, getResources().getString(R.string.msg_enter_the_details_fully_or_calculate_mileage_and_save), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void DeleteVehicle(int position) {

    }
}
