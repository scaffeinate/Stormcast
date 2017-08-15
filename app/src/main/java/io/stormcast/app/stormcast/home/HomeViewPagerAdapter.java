package io.stormcast.app.stormcast.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import io.stormcast.app.stormcast.common.Location;
import io.stormcast.app.stormcast.location.LocationFragment;

/**
 * Created by sudhar on 8/15/17.
 */

public class HomeViewPagerAdapter extends FragmentPagerAdapter {

	private List<Location> mLocations;

	public HomeViewPagerAdapter(FragmentManager fragmentManager, List<Location> locations) {
		super(fragmentManager);
		this.mLocations = locations;
	}

	@Override
	public Fragment getItem(int position) {
		Location location = mLocations.get(position);
		return LocationFragment.newInstance(location);
	}

	@Override
	public int getCount() {
		return mLocations.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		Location location = mLocations.get(position);
		return location.getName();
	}
}
