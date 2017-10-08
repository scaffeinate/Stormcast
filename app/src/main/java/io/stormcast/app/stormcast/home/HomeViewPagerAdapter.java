package io.stormcast.app.stormcast.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import io.stormcast.app.stormcast.forecast.ForecastFragment;

/**
 * Created by sudhar on 8/15/17.
 */

public class HomeViewPagerAdapter extends FragmentStatePagerAdapter {

    private int numPages = 0;

    public HomeViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        return ForecastFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return numPages;
    }


    public void setNumPages(int numPages) {
        this.numPages = numPages;
        notifyDataSetChanged();
    }
}
