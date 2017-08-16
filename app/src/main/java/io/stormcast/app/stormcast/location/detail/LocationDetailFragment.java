package io.stormcast.app.stormcast.location.detail;

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

public class LocationDetailFragment extends Fragment {

	private static final String LOCATION = "LOCATION";
	private RecyclerView mRecyclerView;

	public static LocationDetailFragment newInstance(Location location) {
		LocationDetailFragment locationDetailFragment = new LocationDetailFragment();

		Bundle args = new Bundle();
		args.putParcelable(LOCATION, location);
		locationDetailFragment.setArguments(args);

		return locationDetailFragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_location_detail, container, false);
		return mRecyclerView;
	}
}
