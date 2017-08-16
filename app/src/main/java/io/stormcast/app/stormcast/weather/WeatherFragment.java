package io.stormcast.app.stormcast.weather;

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

public class WeatherFragment extends Fragment {

    private static final String LOCATION = "LOCATION";
    private RecyclerView mRecyclerView;

    public static WeatherFragment newInstance(Location location) {
        WeatherFragment weatherFragment = new WeatherFragment();

        Bundle args = new Bundle();
        args.putParcelable(LOCATION, location);
        weatherFragment.setArguments(args);

        return weatherFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_location_detail, container, false);
        return mRecyclerView;
    }
}
