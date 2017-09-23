package io.stormcast.app.stormcast.forecast;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.pwittchen.weathericonview.WeatherIconView;

import io.stormcast.app.stormcast.R;
import io.stormcast.app.stormcast.common.models.ForecastModel;
import io.stormcast.app.stormcast.common.models.LocationModel;
import io.stormcast.app.stormcast.data.forecast.ForecastRepository;
import io.stormcast.app.stormcast.data.forecast.local.LocalForecastDataSource;
import io.stormcast.app.stormcast.data.forecast.remote.RemoteForecastDataSource;
import io.stormcast.app.stormcast.views.styled.StyledTextView;

/**
 * Created by sudhar on 8/15/17.
 */

public class ForecastFragment extends Fragment implements ForecastContract.View, SwipeRefreshLayout.OnRefreshListener {

    private static final String LOCATION = "LOCATION";

    private Context mContext;
    private ForecastPresenter mPresenter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LocationModel mLocationModel;

    private HourlyForecastAdapter mHourlyAdapter;

    private WeatherIconView mWeatherIconView;
    private StyledTextView mLocationName;
    private StyledTextView mSummary;
    private TextView mLastUpdatedAt;
    private StyledTextView mTemperatureTextView;
    //private RecyclerView mHourlyRecyclerView;

    private int backgroundColor;
    private int textColor;

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
        backgroundColor = Color.parseColor(mLocationModel.getBackgroundColor());
        textColor = Color.parseColor(mLocationModel.getTextColor());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);

        mLocationName = (StyledTextView) view.findViewById(R.id.location_name_text_view);
        mSummary = (StyledTextView) view.findViewById(R.id.summary_text_view);
        mWeatherIconView = (WeatherIconView) view.findViewById(R.id.weather_icon_view);
        mTemperatureTextView = (StyledTextView) view.findViewById(R.id.temperature_text_view);
        //mHourlyRecyclerView = (RecyclerView) view.findViewById(R.id.hourly_forecast_recycler_view);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
       // mHourlyRecyclerView.setLayoutManager(layoutManager);

        view.setBackgroundColor(backgroundColor);
        mLocationName.setTextColor(textColor);
        mSummary.setTextColor(textColor);
        mWeatherIconView.setIconColor(textColor);
        mTemperatureTextView.setTextColor(textColor);

        mSwipeRefreshLayout.setOnRefreshListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                mPresenter.loadForecast(mLocationModel, false);
            }
        }, 250);
    }

    @Override
    public void onForecastLoaded(ForecastModel forecastModel) {
        mLocationName.setText(mLocationModel.getName());
        mSummary.setText(forecastModel.getSummary());
        android.text.format.DateFormat dateFormat = new android.text.format.DateFormat();
        String lastUpdateAt = dateFormat.format("MM-dd-yyyy HH:mm:ss", forecastModel.getUpdatedAt()).toString();
        //mLastUpdatedAt.setText(new StringBuilder().append("Last Updated At: ").append(lastUpdateAt).toString());
        mTemperatureTextView.setText(String.valueOf(forecastModel.getTemperature().intValue()));
        mSwipeRefreshLayout.setRefreshing(false);

        mHourlyAdapter = new HourlyForecastAdapter(forecastModel.getHourlyModels(), textColor);
        //mHourlyRecyclerView.setAdapter(mHourlyAdapter);
    }

    @Override
    public void onDataNotAvailable(String errorMessage) {
        Toast.makeText(mContext, errorMessage, Toast.LENGTH_SHORT).show();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        mPresenter.loadForecast(mLocationModel, true);
    }
}
