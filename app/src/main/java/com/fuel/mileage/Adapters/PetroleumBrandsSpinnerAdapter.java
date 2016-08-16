package com.fuel.mileage.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.fuel.mileage.R;

/**
 * Created by Sunil Kumar on 7/26/2016.
 */
public class PetroleumBrandsSpinnerAdapter extends BaseAdapter
{
    private Integer[] images ;
    private Context context;
    public PetroleumBrandsSpinnerAdapter(Context context, int type)
    {
      this.context = context;

        if(type == 1)
        {
            images = new Integer[]{R.drawable.v0,R.drawable.v1};
        }else{
            images = new Integer[]{R.drawable.p0,R.drawable.p1,R.drawable.p2,
                    R.drawable.p3,R.drawable.p4,R.drawable.p5,R.drawable.p6};
        }
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int position) {
        return images[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        ViewHolder holder;

        if(itemView == null)
        {
            itemView = LayoutInflater.from(context).inflate(R.layout.layout_petroleum_brand_item,parent,false);
            holder = new ViewHolder();
            holder.img = (ImageView)itemView.findViewById(R.id.spinnerImg);
            itemView.setTag(holder);
        }else{
            holder = (ViewHolder)itemView.getTag();
        }

        holder.img.setImageResource(images[position]);
        return itemView;
    }

    private static class ViewHolder
    {
        ImageView img;
    }
}
