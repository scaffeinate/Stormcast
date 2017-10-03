package io.stormcast.app.stormcast.home;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.stormcast.app.stormcast.R;
import io.stormcast.app.stormcast.common.models.LocationModel;
import io.stormcast.app.stormcast.data.locations.LocationsRepository;
import io.stormcast.app.stormcast.data.locations.local.LocalLocationsDataSource;
import io.stormcast.app.stormcast.location.add.AddLocationFragment;
import io.stormcast.app.stormcast.navdrawer.NavDrawerFragment;

/**
 * Created by sudhar on 8/8/17.
 */

public class HomeFragment extends Fragment implements HomeContract.View, ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;

    private HomeViewPagerAdapter mViewPagerAdapter;
    private HomePresenter mHomePresenter;
    private LocationsRepository mLocationsRepository;
    private List<LocationModel> mLocationModels;
    private CustomizeCallbacks mCustomizeCallbacks;

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
        mViewPagerAdapter = new HomeViewPagerAdapter(getChildFragmentManager(), mLocationModels);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_home, container, false);
        mViewPager = (ViewPager) mView.findViewById(R.id.view_pager);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.addOnPageChangeListener(this);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.mCustomizeCallbacks = (CustomizeCallbacks) getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
        mCustomizeCallbacks.setToolbarBackgroundColor(Color.TRANSPARENT);
        mCustomizeCallbacks.setNavDrawerSelected(NavDrawerFragment.POSITION_FORECAST);
        mHomePresenter.loadLocations();
    }

    @Override
    public void onLocationsLoaded(List<LocationModel> locationModels) {
        this.mLocationModels = locationModels;
        mViewPagerAdapter.setLocations(locationModels);
        mViewPagerAdapter.notifyDataSetChanged();
        customizeViews(locationModels.get(mViewPager.getCurrentItem()));
    }

    @Override
    public void onDataNotAvailable() {
        mViewPager.setVisibility(View.GONE);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_location_menu_item:
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_left_enter, R.anim.slide_left_exit, R.anim.slide_right_enter, R.anim.slide_right_exit)
                        .replace(R.id.main_content, AddLocationFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public LocationModel getLocationModel(int position) {
        LocationModel locationModel = mLocationModels.get(position);
        return locationModel;
    }

    private void customizeViews(LocationModel locationModel) {
        int textColor = Color.parseColor(locationModel.getTextColor());
        int backgroundColor = Color.parseColor(locationModel.getBackgroundColor());
        mCustomizeCallbacks.setToolbarTitle(locationModel.getName());
        mCustomizeCallbacks.setToolbarTextColor(textColor);
        mCustomizeCallbacks.setNavDrawerHeaderBackgroundColor(backgroundColor);
    }
}
