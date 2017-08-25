package io.stormcast.app.stormcast.forecast;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.stormcast.app.stormcast.R;
import io.stormcast.app.stormcast.common.models.ForecastModel;
import io.stormcast.app.stormcast.common.models.LocationModel;
import io.stormcast.app.stormcast.data.forecast.ForecastRepository;
import io.stormcast.app.stormcast.data.forecast.local.LocalForecastDataSource;
import io.stormcast.app.stormcast.data.forecast.remote.RemoteForecastDataSource;

/**
 * Created by sudhar on 8/15/17.
 */

public class ForecastFragment extends Fragment implements ForecastContract.View {

    private static final String LOCATION = "LOCATION";

    private Context mContext;
    private ForecastPresenter mPresenter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ActionBar mActionBar;
    private LocationModel mLocationModel;

    public static ForecastFragment newInstance(LocationModel locationModel) {
        ForecastFragment forecastFragment = new ForecastFragment();

        Bundle args = new Bundle();
        args.putParcelable(LOCATION, locationModel);
        forecastFragment.setArguments(args);

        return forecastFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationModel = getArguments().getParcelable(LOCATION);
        mContext = getContext();
        mPresenter = new ForecastPresenter(this, ForecastRepository.getInstance(
                LocalForecastDataSource.getInstance(mContext),
                RemoteForecastDataSource.getInstance()));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        view.setBackgroundColor(Color.parseColor(mLocationModel.getBackgroundColor()));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mSwipeRefreshLayout.setRefreshing(true);
        mPresenter.loadForecast(mLocationModel);
    }

    @Override
    public void onForecastLoaded(ForecastModel forecastModel) {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onDataNotAvailable(String errorMessage) {
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
