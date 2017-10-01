package io.stormcast.app.stormcast.home;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import io.stormcast.app.stormcast.common.models.LocationModel;
import io.stormcast.app.stormcast.forecast.ForecastFragment;

/**
 * Created by sudhar on 8/15/17.
 */

public class HomeViewPagerAdapter extends FragmentStatePagerAdapter {

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
    public int getItemPosition(Object object) {
        return POSITION_NONE;
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

    @Override
    public Parcelable saveState() {
        return null;
    }
}
