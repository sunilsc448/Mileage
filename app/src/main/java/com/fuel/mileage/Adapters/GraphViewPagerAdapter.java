package com.fuel.mileage.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;

import com.fuel.mileage.Fragments.GraphFragment;

/**
 * Created by Sunil Kumar on 8/1/2016.
 */
public class GraphViewPagerAdapter extends FragmentPagerAdapter{

    private String vehicleId;
    SparseArray<GraphFragment> lstFragments;

    public GraphViewPagerAdapter(FragmentManager fm) {
        super(fm);
        lstFragments = new SparseArray<GraphFragment>();

    }

    public void updateVehicleId(String id)
    {
        vehicleId = id;
    }

    public GraphFragment getLstFragments(int pos)
    {
        return lstFragments.get(pos);
    }

    @Override
    public Fragment getItem(int position) {
        GraphFragment grpFrgmnt = new GraphFragment();
        lstFragments.put(position,grpFrgmnt);
        Bundle bundle = new Bundle();
        bundle.putInt("pos", position);
        bundle.putString("vehicleId", vehicleId);
        grpFrgmnt.setArguments(bundle);
        return grpFrgmnt;
    }

    @Override
    public int getCount() {
        return 3;
    }

    private String tabTitles[] = new String[]{"Monthly", "Filled", "Mileage"};
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

}
