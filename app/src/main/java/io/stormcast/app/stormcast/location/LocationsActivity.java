package io.stormcast.app.stormcast.location;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import io.stormcast.app.stormcast.R;
import io.stormcast.app.stormcast.location.list.LocationsListFragment;

/**
 * Created by sudhar on 8/15/17.
 */

public class LocationsActivity extends AppCompatActivity {

	private FragmentManager mFragmentManager;
	private ActionBar mActionBar;
	private LocationsListFragment mLocationsListFragment;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_locations);

		mFragmentManager = getSupportFragmentManager();
		mActionBar = getSupportActionBar();

		if (mFragmentManager != null) {

			if (mLocationsListFragment == null) {
				mLocationsListFragment = LocationsListFragment.newInstance();
			}

			mFragmentManager
					.beginTransaction()
					.replace(R.id.locations_content, mLocationsListFragment)
					.commit();
		}

		if (mActionBar != null) mActionBar.setTitle("Locations");
	}
}
