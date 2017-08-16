package io.stormcast.app.stormcast.locations.location_detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.stormcast.app.stormcast.R;
import io.stormcast.app.stormcast.common.Location;

/**
 * Created by sudhar on 8/15/17.
 */

public class LocationFragment extends Fragment {

	private static final String LOCATION = "LOCATION";
	private RecyclerView mRecyclerView;

	public static LocationFragment newInstance(Location location) {
		LocationFragment locationFragment = new LocationFragment();

		Bundle args = new Bundle();
		args.putParcelable(LOCATION, location);
		locationFragment.setArguments(args);

		return locationFragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_location, container, false);
		return mRecyclerView;
	}
}
