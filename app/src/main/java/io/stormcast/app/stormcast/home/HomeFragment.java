package io.stormcast.app.stormcast.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.stormcast.app.stormcast.R;
import io.stormcast.app.stormcast.common.Location;
import io.stormcast.app.stormcast.location.LocationsActivity;
import io.stormcast.app.stormcast.location.list.LocationsListFragment;

/**
 * Created by sudhar on 8/8/17.
 */

public class HomeFragment extends Fragment implements HomeContract.View, View.OnClickListener {

	private ViewPager mViewPager;
	private TabLayout mTabLayout;
	private FloatingActionButton mButton;

	private HomeViewPagerAdapter mViewPagerAdapter;
	private HomePresenter mHomePresenter;

	public static HomeFragment newInstance() {
		HomeFragment mFragment = new HomeFragment();
		return mFragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mHomePresenter = new HomePresenter(this);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View mView = inflater.inflate(R.layout.fragment_home, container, false);
		mViewPager = (ViewPager) mView.findViewById(R.id.view_pager);
		mTabLayout = (TabLayout) mView.findViewById(R.id.tab_layout);
		mButton = (FloatingActionButton) mView.findViewById(R.id.locations_button);

		mTabLayout.setupWithViewPager(mViewPager);
		mButton.setOnClickListener(this);

		return mView;
	}

	@Override
	public void onResume() {
		super.onResume();
		mHomePresenter.loadLocations();
	}

	@Override
	public void onLocationsLoaded(List<Location> locations) {
		mViewPagerAdapter = new HomeViewPagerAdapter(getFragmentManager(), locations);
		mViewPager.setAdapter(mViewPagerAdapter);
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
