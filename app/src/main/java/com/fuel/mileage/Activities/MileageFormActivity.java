package com.fuel.mileage.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fuel.mileage.Interfaces.AdapterToActivityCallBack;
import com.fuel.mileage.Models.MileageItemModel;
import com.fuel.mileage.R;
import com.fuel.mileage.Utilities.Extras;
import com.fuel.mileage.Utilities.SQLiteHelper;
import com.inmobi.ads.InMobiBanner;
import com.inmobi.sdk.InMobiSdk;

import java.util.Calendar;

public class MileageFormActivity extends AppCompatActivity implements AdapterToActivityCallBack {

    SQLiteHelper db = new SQLiteHelper(this,null,null,0);

    private EditText curretMeterReadinginKms;
    private EditText toDatePickerBox;
    private Button mileageBtn;
    private LinearLayout mileageLyt;
    private TextView mileageResult;

    private MileageItemModel mileageItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InMobiSdk.init(this, Extras.ADS_API_ID);
        setContentView(R.layout.activity_mileage_form);

        InMobiBanner banner = (InMobiBanner) findViewById(R.id.banner);
        banner.load();

        Intent intent = getIntent();
        if(intent!=null)
            mileageItem =  (MileageItemModel)intent.getSerializableExtra("mileageItem");

        mileageBtn = (Button)findViewById(R.id.mileageBtn);
        mileageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentMtrReading = curretMeterReadinginKms.getText().toString();
                if( !currentMtrReading.isEmpty())
                {
                    if(Float.parseFloat(currentMtrReading) > mileageItem.getLastmeterReading()) {
                        Float distTravlled = Float.parseFloat(currentMtrReading) - mileageItem.getLastmeterReading();
                        mileageItem.setCurrentmeterReading(Float.parseFloat(currentMtrReading));
                        mileageItem.setDistanceTravelled(distTravlled);
                        Float mlg = distTravlled / mileageItem.getPetrolFilled();
                        mileageItem.setMileage(mlg);
                        mileageItem.setIsMileageChecked(1);
                        mileageItem.setToDate(Extras.getExtraClassObj().getDateFromStr(toDatePickerBox.getText().toString()));
                        mileageResult.setText(getResources().getString(R.string.mileage_is) +
                                String.format("%.2f", mlg) +
                                getResources().getString(R.string.kms_per_ltr));
                        Toast.makeText(MileageFormActivity.this, getResources().getString(R.string.mileage_is) +
                                String.format("%.2f", mlg) +
                                getResources().getString(R.string.kms_per_ltr), Toast.LENGTH_SHORT).show();
                        mileageLyt.setVisibility(View.VISIBLE);
                    }else{
                        Toast.makeText(MileageFormActivity.this, getResources().getString(R.string.current_should_greater_than_previous), Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(MileageFormActivity.this, getResources().getString(R.string.msg_enter_the_details_fully), Toast.LENGTH_LONG).show();
                }
            }
        });

        mileageResult = (TextView)findViewById(R.id.mileageResult);
        mileageLyt = (LinearLayout)findViewById(R.id.mileageLyt);
        curretMeterReadinginKms = (EditText)findViewById(R.id.curretMeterReadinginKms);
        toDatePickerBox = (EditText)findViewById(R.id.toDatePickerBox);
        toDatePickerBox.setText(Extras.getExtraClassObj().getCurrentFormattedDate());
        toDatePickerBox.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Calendar c = Calendar.getInstance();
                new DatePickerDialog(MileageFormActivity.this, datePickerListener, c
                        .get(Calendar.YEAR), c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

//        ((ImageView)findViewById(R.id.vehicleTypeImg)).setImageResource(Extras.getExtraClassObj().getImageId(this, mileageItem.getVehicleType()));

        ((TextView)findViewById(R.id.vehicleNameAndNo)).setText(mileageItem.getVehicleName()+" - "+mileageItem.getVehicleId());

        ((ImageView) findViewById(R.id.petroleumCompanyImg)).setImageResource(Extras.getExtraClassObj().getImageId(this,mileageItem.getPetroleumBrand()));

        ((EditText)findViewById(R.id.lastMeterReadinginKms)).setText(String.format("%.1f",mileageItem.getLastmeterReading()));

        ((EditText)findViewById(R.id.ptrlPricePerLtr)).setText(String.format("%.1f",mileageItem.getPetrolPrice()));

        ((EditText)findViewById(R.id.amntOfPtrlFilled)).setText(String.format("%.0f",mileageItem.getAmountOfPetrolFilled()));

        ((EditText)findViewById(R.id.ptrlFilledInLtrs)).setText(String.format("%.2f",mileageItem.getPetrolFilled()));

        ((EditText)findViewById(R.id.fromDatePickerBox)).setText( Extras.getExtraClassObj().getFormattedDate(mileageItem.getFromDate()));

        //Mileage already checked
        if(mileageItem.getIsMileageChecked() == 1)
        {
            mileageBtn.setVisibility(View.GONE);
            curretMeterReadinginKms.setText(String.format("%.1f", mileageItem.getCurrentmeterReading()));
            curretMeterReadinginKms.setEnabled(false);
            toDatePickerBox.setText(Extras.getExtraClassObj().getFormattedDate(mileageItem.getToDate()));
            toDatePickerBox.setEnabled(false);
            getSupportActionBar().hide();
            mileageResult.setText(getResources().getString(R.string.mileage_is) +
                    String.format("%.2f", mileageItem.getMileage()) +
                    getResources().getString(R.string.kms_per_ltr));
            mileageLyt.setVisibility(View.VISIBLE);
        }
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            toDatePickerBox.setText(Extras.getExtraClassObj().getFormattedDateFromYMD(year,monthOfYear,dayOfMonth));
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if(id == R.id.action_save)
        {
            updateDBData();
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateDBData() {
        String currentMtrReading = curretMeterReadinginKms.getText().toString();
        if(!currentMtrReading.isEmpty() && mileageLyt.getVisibility() == View.VISIBLE) {
            db.updateMileageItem(mileageItem);
            finish();
        }else{
            Toast.makeText(MileageFormActivity.this, getResources().getString(R.string.msg_enter_the_details_fully_or_calculate_mileage_and_save), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void DeleteVehicle(int position) {

    }
}
