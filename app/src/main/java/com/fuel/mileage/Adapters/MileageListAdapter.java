package com.fuel.mileage.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fuel.mileage.Activities.MileageFormActivity;
import com.fuel.mileage.Models.MileageItemModel;
import com.fuel.mileage.R;
import com.fuel.mileage.Utilities.Extras;
import com.fuel.mileage.Utilities.SQLiteHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sunil Kumar on 7/22/2016.
 */
public class MileageListAdapter extends RecyclerView.Adapter<MileageListAdapter.ViewHolder>
{
    private Context context;
    private List<MileageItemModel> lst_mileageItms = new ArrayList<MileageItemModel>();
    public MileageListAdapter(Context context)
    {
        this.context = context;
    }

    public void UpdateData(List<MileageItemModel> lst)
    {
        lst_mileageItms = lst;
        notifyDataSetChanged();
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_mileage_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return lst_mileageItms.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {
        holder.mainLyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MileageFormActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("mileageItem",lst_mileageItms.get(position));
                intent.putExtras(b);
                context.startActivity(intent);
            }
        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteHelper db = new SQLiteHelper(context,null,null,0);
                db.deleteMileageItem(lst_mileageItms.get(position));
                lst_mileageItms.remove(position);
                notifyDataSetChanged();
            }
        });

        Typeface segoFont = Typeface.createFromAsset(context.getAssets(), "font/segoeui.ttf");
        //view.setTypeface(boldTypeface);

        holder.txtVw_fromDate.setText(Extras.getExtraClassObj().getFormattedDate(lst_mileageItms.get(position).getFromDate()));
        holder.txtVw_fromDate.setTypeface(segoFont);
        holder.txtVw_litresOfPetrol.setText(String.format("%.2f", lst_mileageItms.get(position).getPetrolFilled()) +
                context.getResources().getString(R.string.ltrs));
        holder.txtVw_litresOfPetrol.setTypeface(segoFont);
        holder.txtVw_rupees.setText(context.getResources().getString(R.string.rs) +
                String.format("%.0f", lst_mileageItms.get(position).getAmountOfPetrolFilled()));
        holder.txtVw_rupees.setTypeface(segoFont);
        holder.petroleumCompanyImg.setImageResource(Extras.getExtraClassObj().getImageId(context, lst_mileageItms.get(position).getPetroleumBrand()));
        holder.vehicleTypeImg.setImageResource(Extras.getExtraClassObj().getImageId(context,lst_mileageItms.get(position).getVehicleType()));

        if(lst_mileageItms.get(position).getIsMileageChecked() == 1)
        {
            holder.txtVw_toDate.setText(Extras.getExtraClassObj().getFormattedDate(lst_mileageItms.get(position).getToDate()));
            holder.txtVw_toDate.setTypeface(segoFont);
            holder.txtVw_mileage.setText(String.format("%.2f", lst_mileageItms.get(position).getMileage())+
             context.getResources().getString(R.string.kms_per_ltr));
            holder.txtVw_mileage.setTypeface(segoFont);
            holder.txtVw_distanceTravelled.setText(String.format("%.1f", lst_mileageItms.get(position).getDistanceTravelled()) +
                    context.getResources().getString(R.string.kms));
            holder.txtVw_distanceTravelled.setTypeface(segoFont);
            holder.statusStick.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGreenBar));
        }
        else
        {
            holder.txtVw_toDate.setText(context.getResources().getString(R.string.dash_marks));
            holder.txtVw_distanceTravelled.setText("");
            holder.txtVw_mileage.setText(context.getResources().getString(R.string.dash_marks)+
                    context.getResources().getString(R.string.kms_per_ltr));
            holder.statusStick.setBackgroundColor(ContextCompat.getColor(context, R.color.colorRedBar));
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtVw_fromDate;
        TextView txtVw_toDate;
        TextView txtVw_rupees;
        TextView txtVw_litresOfPetrol;
        TextView txtVw_mileage;
        TextView txtVw_distanceTravelled;
        ImageView petroleumCompanyImg;
        LinearLayout mainLyt;
        View statusStick;
        ImageView deleteBtn;
        ImageView vehicleTypeImg;

        public ViewHolder(View view) {
            super(view);
            txtVw_fromDate = (TextView)view.findViewById(R.id.txtVw_fromDate);
            txtVw_toDate = (TextView)view.findViewById(R.id.txtVw_toDate);
            txtVw_rupees = (TextView)view.findViewById(R.id.txtVw_rupees);
            txtVw_litresOfPetrol = (TextView)view.findViewById(R.id.txtVw_litresOfPetrol);
            txtVw_mileage = (TextView)view.findViewById(R.id.txtVw_mileage);
            txtVw_distanceTravelled = (TextView)view.findViewById(R.id.txtVw_distanceTravelled);
            petroleumCompanyImg = (ImageView)view.findViewById(R.id.petroleumCompanyImg);
            mainLyt = (LinearLayout)view.findViewById(R.id.mainLyt);
            statusStick = (View)view.findViewById(R.id.statusStick);
            deleteBtn = (ImageView)view.findViewById(R.id.deleteBtn);
            vehicleTypeImg = (ImageView)view.findViewById(R.id.vehicleTypeImg);
        }
    }
}

