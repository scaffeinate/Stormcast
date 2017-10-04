package io.stormcast.app.stormcast.home;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import io.stormcast.app.stormcast.AppConstants;
import io.stormcast.app.stormcast.R;
import io.stormcast.app.stormcast.common.models.LocationModel;
import io.stormcast.app.stormcast.data.locations.LocationsRepository;
import io.stormcast.app.stormcast.data.locations.local.LocalLocationsDataSource;
import io.stormcast.app.stormcast.navdrawer.NavDrawerCallbacks;

/**
 * Created by sudhar on 8/8/17.
 */

public class HomeFragment extends Fragment implements HomeContract.View, ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;
    private RelativeLayout mNoLocationsLayout;

    private HomeViewPagerAdapter mViewPagerAdapter;
    private HomePresenter mHomePresenter;
    private LocationsRepository mLocationsRepository;
    private List<LocationModel> mLocationModels;
    private ToolbarCallbacks mToolbarCallbacks;
    private NavDrawerCallbacks mNavDrawerCallbacks;

    public static HomeFragment newInstance() {
        HomeFragment mFragment = new HomeFragment();
        return mFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationsRepository = LocationsRepository.getInstance(LocalLocationsDataSource
                .getInstance(getActivity().getApplicationContext()));
        mHomePresenter = new HomePresenter(this, mLocationsRepository);
        mLocationModels = new ArrayList<>();
        mViewPagerAdapter = new HomeViewPagerAdapter(getChildFragmentManager(), mLocationModels.size());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_home, container, false);
        mViewPager = (ViewPager) mView.findViewById(R.id.view_pager);
        mNoLocationsLayout = (RelativeLayout) mView.findViewById(R.id.no_locations_layout);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.addOnPageChangeListener(this);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.mNavDrawerCallbacks = (NavDrawerCallbacks) getActivity();
        this.mToolbarCallbacks = (ToolbarCallbacks) getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
        mHomePresenter.loadLocations();
    }

    @Override
    public void onLocationsLoaded(List<LocationModel> locationModels) {
        int diff = Math.abs(this.mLocationModels.size() - locationModels.size());
        this.mLocationModels = locationModels;
        mViewPager.setVisibility(View.VISIBLE);
        mNoLocationsLayout.setVisibility(View.GONE);
        mViewPagerAdapter.setNumPages(locationModels.size());
        mViewPagerAdapter.shifIds(diff);
        mViewPagerAdapter.notifyDataSetChanged();
        customizeViews(locationModels.get(mViewPager.getCurrentItem()));
    }

    @Override
    public void onDataNotAvailable() {
        mViewPager.setVisibility(View.GONE);
        mNoLocationsLayout.setVisibility(View.VISIBLE);
        getView().setBackgroundColor(AppConstants.DEFAULT_BACKGROUND_COLOR);
        mToolbarCallbacks.setToolbarTitle(AppConstants.APP_NAME);
        mNavDrawerCallbacks.setNavDrawerHeaderBackgroundColor(AppConstants.DEFAULT_BACKGROUND_COLOR);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        customizeViews(mLocationModels.get(position));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.home_menu, menu);
    }

    public LocationModel getLocationModel(int position) {
        LocationModel locationModel = mLocationModels.get(position);
        return locationModel;
    }

    private void customizeViews(LocationModel locationModel) {
        int textColor = Color.parseColor(locationModel.getTextColor());
        int backgroundColor = Color.parseColor(locationModel.getBackgroundColor());
        mToolbarCallbacks.setToolbarTitle(locationModel.getName());
        mToolbarCallbacks.setToolbarTextColor(textColor);
        mNavDrawerCallbacks.setNavDrawerHeaderBackgroundColor(backgroundColor);
    }
}
