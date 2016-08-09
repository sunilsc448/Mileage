package com.example.android.mileage.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.mileage.Models.MileageItemModel;
import com.example.android.mileage.R;
import com.example.android.mileage.Utilities.Extras;
import com.example.android.mileage.Utilities.SQLiteHelper;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class GraphFragment extends Fragment {

    List<MileageItemModel> lst_mileage_items;

    String[] arry_xAxis;
    BarChart barChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_graph, container, false);

        Bundle bundle = getArguments();
        int pos = bundle.getInt("pos");
        String vehicleId = bundle.getString("vehicleId");

        barChart = (BarChart) view.findViewById(R.id.chartLyt);

        renderBar(pos, vehicleId);

        return view;
    }

    public void renderBar(int pos, String vehicleId) {

        SQLiteHelper db = new SQLiteHelper(getContext(), null, null, 0);
        lst_mileage_items = db.getAllMileageItemsBasedOnId(vehicleId);

        ArrayList<BarDataSet> dataSets = new ArrayList<>();
        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarDataSet barDataSet = null;
        List<String> arry_xAxis = new ArrayList(){};

        switch(pos)
        {
            case 0:
                arry_xAxis = Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
                float[] month_values = new float[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                for (int i = 0; i < lst_mileage_items.size(); i++) {
                    MileageItemModel item = lst_mileage_items.get(i);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(item.getFromDate());
                    int month = cal.get(Calendar.MONTH);
                    month_values[month] += item.getAmountOfPetrolFilled();
                }

                for (int i = 0; i < month_values.length; i++) {
                    BarEntry barEntry = new BarEntry(month_values[i], i);
                    valueSet1.add(barEntry);
                }

                barDataSet = new BarDataSet(valueSet1, getResources().getString(R.string.amount_spent_per_month));
                break;
            case 1:
                arry_xAxis = new ArrayList(){};
                for (int i = 0; i < lst_mileage_items.size(); i++) {
                    MileageItemModel item = lst_mileage_items.get(i);
                    arry_xAxis.add(Extras.getExtraClassObj().getFormattedDate(item.getFromDate()));

                    BarEntry barEntry = new BarEntry(item.getAmountOfPetrolFilled(), i);
                    valueSet1.add(barEntry);
                }

                barDataSet = new BarDataSet(valueSet1, getResources().getString(R.string.amount_of_fuel_filled));
                break;
            case 2:
                arry_xAxis = new ArrayList(){};
                List<MileageItemModel> lst = new ArrayList<>();

                for (int i = 0; i < lst_mileage_items.size(); i++) {
                    if (lst_mileage_items.get(i).getIsMileageChecked() == 1)
                        lst.add(lst_mileage_items.get(i));
                }

                for (int i = 0; i < lst.size(); i++)
                {
                    MileageItemModel item = lst.get(i);

                    arry_xAxis.add( Extras.getExtraClassObj().getFormattedShortDateForGraph(item.getFromDate())+"-"+
                                    Extras.getExtraClassObj().getFormattedShortDateForGraph(item.getToDate()));
                    BarEntry barEntry = new BarEntry(item.getMileage(), i);
                    valueSet1.add(barEntry);

                }
                barDataSet = new BarDataSet(valueSet1, getResources().getString(R.string.mileage));
                break;
            default:
                System.out.println("");
                break;
        }

        barDataSet.setColor(ContextCompat.getColor(getContext(), R.color.menubg));
        dataSets.add(barDataSet);

        barChart.setData(new BarData(arry_xAxis, dataSets));
        barChart.animateXY(2000, 2000);
        barChart.invalidate();
        barChart.setDescription("");
        barChart.setPinchZoom(false);
    }
}
