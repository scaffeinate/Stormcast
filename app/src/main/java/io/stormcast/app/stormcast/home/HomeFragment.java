package io.stormcast.app.stormcast.home;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.stormcast.app.stormcast.R;
import io.stormcast.app.stormcast.common.models.LocationModel;
import io.stormcast.app.stormcast.data.locations.LocationsRepository;
import io.stormcast.app.stormcast.data.locations.local.LocalLocationsDataSource;
import io.stormcast.app.stormcast.views.styled.StyledTextView;

/**
 * Created by sudhar on 8/8/17.
 */

public class HomeFragment extends Fragment implements HomeContract.View, ViewPager.OnPageChangeListener {

    private Context mContext;

    private ViewPager mViewPager;

    private HomeViewPagerAdapter mViewPagerAdapter;
    private HomePresenter mHomePresenter;
    private LocationsRepository mLocationsRepository;
    private List<LocationModel> mLocationModels;

    private Toolbar mToolbar;
    private StyledTextView mToolbarTitle;

    public static HomeFragment newInstance() {
        HomeFragment mFragment = new HomeFragment();
        return mFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        mLocationsRepository = LocationsRepository.getInstance(LocalLocationsDataSource
                .getInstance(getActivity().getApplicationContext()));
        mHomePresenter = new HomePresenter(this, mLocationsRepository);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_home, container, false);
        mViewPager = (ViewPager) mView.findViewById(R.id.view_pager);
        mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        mToolbarTitle = (StyledTextView) getActivity().findViewById(R.id.toolbar_title);
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
        this.mLocationModels = locationModels;
        setTitle(locationModels.get(0));
        mViewPager.addOnPageChangeListener(this);
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
        setTitle(mLocationModels.get(position));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void setTitle(LocationModel locationModel) {
        mToolbarTitle.setText(locationModel.getName());
    }
}
