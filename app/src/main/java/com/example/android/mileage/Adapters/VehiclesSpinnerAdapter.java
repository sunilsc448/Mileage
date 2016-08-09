package com.example.android.mileage.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.mileage.Interfaces.AdapterToActivityCallBack;
import com.example.android.mileage.Models.VehicleItemModel;
import com.example.android.mileage.R;
import com.example.android.mileage.Utilities.Extras;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sunil Kumar on 8/2/2016.
 */
public class VehiclesSpinnerAdapter extends BaseAdapter
{
    private Context context;
    private AdapterToActivityCallBack adapterToActivityCallBack;
    private List<VehicleItemModel> vehicles = new ArrayList<VehicleItemModel>();
    String type;
    public  VehiclesSpinnerAdapter(Context context, String type)
    {
        this.context = context;
        adapterToActivityCallBack = (AdapterToActivityCallBack) context;
        this.type = type;
    }

    @Override
    public int getCount() {
//        return vehicles.size();
        return vehicles.size();
    }

    @Override
    public Object getItem(int position) {
        return vehicles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        ViewHolder holder;

        if(itemView == null)
        {
            itemView = LayoutInflater.from(context).inflate(R.layout.layout_vehicle_type,parent,false);
            holder = new ViewHolder();
            holder.vehicleNameAndNo = (TextView)itemView.findViewById(R.id.vehicleNameAndNo);
            holder.vehicleTypeImg = (ImageView)itemView.findViewById(R.id.vehicleTypeImg);
            holder.deleteVehicleBtn = (ImageView)itemView.findViewById(R.id.deleteVehicleBtn);

            itemView.setTag(holder);
        }else{
            holder = (ViewHolder)itemView.getTag();
        }

        if(type == "Form" || vehicles.get(position).getVehicleId().equalsIgnoreCase(context.getResources().getString(R.string.add_new_vehicle_item_id)))
        {
            holder.deleteVehicleBtn.setVisibility(View.GONE);
        }else{
            holder.deleteVehicleBtn.setVisibility(View.VISIBLE);
            holder.deleteVehicleBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapterToActivityCallBack.DeleteVehicle(position);
                }
            });
        }

        if(vehicles.get(position).getVehicleId() != context.getResources().getString(R.string.add_new_vehicle_item_id))
            holder.vehicleNameAndNo.setText(vehicles.get(position).getVehicleName()+" - "+vehicles.get(position).getVehicleId());
        else
            holder.vehicleNameAndNo.setText(vehicles.get(position).getVehicleName());

        holder.vehicleTypeImg.setImageResource(Extras.getExtraClassObj().getImageId(context, vehicles.get(position).getVehicleType() + "_"));
        return itemView;
    }

    public void UpdateData(List<VehicleItemModel> listVehicleItems) {
        vehicles = listVehicleItems;
        notifyDataSetChanged();
    }

    private static class ViewHolder
    {
        TextView vehicleNameAndNo;
        ImageView vehicleTypeImg;
        ImageView deleteVehicleBtn;
    }
}
