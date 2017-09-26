package io.stormcast.app.stormcast.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.pwittchen.weathericonview.WeatherIconView;

import java.util.List;

import io.stormcast.app.stormcast.R;
import io.stormcast.app.stormcast.common.models.LocationModel;
import io.stormcast.app.stormcast.data.locations.LocationsRepository;
import io.stormcast.app.stormcast.data.locations.local.LocalLocationsDataSource;
import io.stormcast.app.stormcast.location.LocationsActivity;

/**
 * Created by sudhar on 8/8/17.
 */

public class HomeFragment extends Fragment implements HomeContract.View, View.OnClickListener {

    private Context mContext;

    private ViewPager mViewPager;
    private WeatherIconView mSplashIcon;
    private FloatingActionButton mButton;

    private HomeViewPagerAdapter mViewPagerAdapter;
    private HomePresenter mHomePresenter;
    private LocationsRepository mLocationsRepository;

    public static HomeFragment newInstance() {
        HomeFragment mFragment = new HomeFragment();
        return mFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        mLocationsRepository = LocationsRepository.getInstance(LocalLocationsDataSource.getInstance(mContext));
        mHomePresenter = new HomePresenter(this, mLocationsRepository);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_home, container, false);
        mViewPager = (ViewPager) mView.findViewById(R.id.view_pager);
        mSplashIcon = (WeatherIconView) mView.findViewById(R.id.splash_storm_icon);
        mButton = (FloatingActionButton) mView.findViewById(R.id.locations_button);
        mButton.setOnClickListener(this);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mHomePresenter.loadLocations();
    }

    @Override
    public void onLocationsLoaded(List<LocationModel> locationModels) {
        if (mViewPagerAdapter == null) {
            mViewPagerAdapter = new HomeViewPagerAdapter(getFragmentManager(), locationModels);
            mViewPager.setAdapter(mViewPagerAdapter);
        } else {
            mViewPagerAdapter.setLocations(locationModels);
            mViewPagerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDataNotAvailable() {
        mViewPager.setVisibility(View.GONE);
        mSplashIcon.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.locations_button:
                Intent mIntent = new Intent(getActivity(), LocationsActivity.class);
                startActivity(mIntent);
                break;
        }
    }
}
