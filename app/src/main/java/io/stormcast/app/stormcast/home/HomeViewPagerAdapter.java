package io.stormcast.app.stormcast.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import io.stormcast.app.stormcast.forecast.ForecastFragment;

/**
 * Created by sudhar on 8/15/17.
 */

public class HomeViewPagerAdapter extends FragmentPagerAdapter {

    private int numPages = 0;
    private long baseId = 0;

    public HomeViewPagerAdapter(FragmentManager fragmentManager, int numPages) {
        super(fragmentManager);
        this.numPages = numPages;
    }

    @Override
    public Fragment getItem(int position) {
        return ForecastFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return numPages;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public long getItemId(int position) {
        return baseId + position;
    }

    public void shifIds(int n) {
        this.baseId += getCount() + n;
    }

    public void setNumPages(int numPages) {
        this.numPages = numPages;
    }
}
