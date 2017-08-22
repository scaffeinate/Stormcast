package io.stormcast.app.stormcast.forecast;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.stormcast.app.stormcast.R;
import io.stormcast.app.stormcast.common.dto.Location;

/**
 * Created by sudhar on 8/15/17.
 */

public class ForecastFragment extends Fragment {

    private static final String LOCATION = "LOCATION";
    private RecyclerView mRecyclerView;
    private ActionBar mActionBar;
    private Location mLocation;

    public static ForecastFragment newInstance(Location location) {
        ForecastFragment forecastFragment = new ForecastFragment();

        Bundle args = new Bundle();
        args.putParcelable(LOCATION, location);
        forecastFragment.setArguments(args);

        return forecastFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocation = getArguments().getParcelable(LOCATION);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.weather_info_recycler_view);
        view.setBackgroundColor(Color.parseColor(mLocation.getBackgroundColor()));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
