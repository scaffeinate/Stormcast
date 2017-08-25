package io.stormcast.app.stormcast.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import io.stormcast.app.stormcast.common.models.LocationModel;
import io.stormcast.app.stormcast.forecast.ForecastFragment;

/**
 * Created by sudhar on 8/15/17.
 */

public class HomeViewPagerAdapter extends FragmentPagerAdapter {

    private List<LocationModel> mLocationModels;

    public HomeViewPagerAdapter(FragmentManager fragmentManager, List<LocationModel> locationModels) {
        super(fragmentManager);
        this.mLocationModels = locationModels;
    }

    @Override
    public Fragment getItem(int position) {
        LocationModel locationModel = mLocationModels.get(position);
        return ForecastFragment.newInstance(locationModel);
    }

    @Override
    public int getCount() {
        return mLocationModels.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        LocationModel locationModel = mLocationModels.get(position);
        return locationModel.getName();
    }

    public void setLocations(List<LocationModel> locationModels) {
        this.mLocationModels = locationModels;
    }
}
