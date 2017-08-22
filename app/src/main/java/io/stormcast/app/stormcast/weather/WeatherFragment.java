package io.stormcast.app.stormcast.weather;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import io.stormcast.app.stormcast.R;
import io.stormcast.app.stormcast.common.Location;

/**
 * Created by sudhar on 8/15/17.
 */

public class WeatherFragment extends Fragment {

    private static final String LOCATION = "LOCATION";
    private RecyclerView mRecyclerView;
    private ActionBar mActionBar;
    private Location mLocation;

    public static WeatherFragment newInstance(Location location) {
        WeatherFragment weatherFragment = new WeatherFragment();

        Bundle args = new Bundle();
        args.putParcelable(LOCATION, location);
        weatherFragment.setArguments(args);

        return weatherFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocation = getArguments().getParcelable(LOCATION);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.weather_info_recycler_view);
        view.setBackgroundColor(Color.parseColor(mLocation.getBackgroundColor()));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
