package io.stormcast.app.stormcast.forecast;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.pwittchen.weathericonview.WeatherIconView;

import java.util.List;
import java.util.Map;

import io.stormcast.app.stormcast.R;
import io.stormcast.app.stormcast.common.models.DailyForecastModel;
import io.stormcast.app.stormcast.common.models.ForecastModel;
import io.stormcast.app.stormcast.common.models.LocationModel;
import io.stormcast.app.stormcast.data.forecast.ForecastRepository;
import io.stormcast.app.stormcast.data.forecast.local.LocalForecastDataSource;
import io.stormcast.app.stormcast.data.forecast.remote.RemoteForecastDataSource;
import io.stormcast.app.stormcast.home.HomeFragment;
import io.stormcast.app.stormcast.views.styled.StyledTextView;

/**
 * Created by sudhar on 8/15/17.
 */

public class ForecastFragment extends Fragment implements ForecastContract.View, SwipeRefreshLayout.OnRefreshListener {

    private static final String POSITION = "position";

    private Context mContext;
    private ForecastPresenter mPresenter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LocationModel mLocationModel;

    private LinearLayout mForecastLayout;

    private WeatherIconView mWeatherIconView;
    private StyledTextView mSummaryTextView;
    private StyledTextView mTemperatureTextView;
    private StyledTextView mMinTemperatureTextView;
    private StyledTextView mMaxTemperatureTextView;

    private int backgroundColor;
    private int textColor;

    public static ForecastFragment newInstance(int position) {
        ForecastFragment forecastFragment = new ForecastFragment();

        Bundle args = new Bundle();
        args.putInt(POSITION, position);
        forecastFragment.setArguments(args);

        return forecastFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        mPresenter = new ForecastPresenter(this, ForecastRepository.getInstance(
                LocalForecastDataSource.getInstance(getActivity().getApplicationContext()),
                RemoteForecastDataSource.getInstance()));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mForecastLayout = (LinearLayout) view.findViewById(R.id.forecast_layout);

        mSummaryTextView = (StyledTextView) view.findViewById(R.id.summary_text_view);
        mTemperatureTextView = (StyledTextView) view.findViewById(R.id.temperature_text_view);
        mMinTemperatureTextView = (StyledTextView) view.findViewById(R.id.min_temperature_text_view);
        mMaxTemperatureTextView = (StyledTextView) view.findViewById(R.id.max_temperature_text_view);
        mWeatherIconView = (WeatherIconView) view.findViewById(R.id.weather_icon_view);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        int position = getArguments().getInt(POSITION);
        mLocationModel = ((HomeFragment) getParentFragment()).getLocationModel(position);

        backgroundColor = Color.parseColor(mLocationModel.getBackgroundColor());
        textColor = Color.parseColor(mLocationModel.getTextColor());

        getView().setBackgroundColor(backgroundColor);
        mPresenter.setCustomTextColor(mForecastLayout, textColor);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.loadForecast(mLocationModel, false);
            }
        }, 250);
    }

    @Override
    public void onForecastLoaded(final ForecastModel forecastModel, final List<DailyForecastModel> dailyForecastModels) {
        if (!isAdded()) return;
        mSwipeRefreshLayout.setRefreshing(false);
        mPresenter.formatForecast(forecastModel, new ForecastFormatter.ForecastFormatterCallback() {
            @Override
            public void onFormatForecast(Map<String, String> formattedMap) {
                String temperature = formattedMap.get(ForecastPresenter.TEMPERATURE);
                String windSpeed = formattedMap.get(ForecastPresenter.WIND_SPEED);
                String minTemperature = formattedMap.get(ForecastPresenter.MIN_TEMPERATURE);
                String maxTemperature = formattedMap.get(ForecastPresenter.MAX_TEMPERATURE);
                String summary = formattedMap.get(ForecastPresenter.SUMMARY);
                String icon = formattedMap.get(ForecastPresenter.ICON);

                mTemperatureTextView.setText(temperature);
                mMinTemperatureTextView.setText(minTemperature);
                mMaxTemperatureTextView.setText(maxTemperature);
                mSummaryTextView.setText(summary);
                mWeatherIconView.setIconResource(getResources().getString(Integer.parseInt(icon)));
            }
        });
    }

    @Override
    public void onDataNotAvailable(String errorMessage) {
        mSwipeRefreshLayout.setRefreshing(false);
        Toast.makeText(mContext, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        mPresenter.loadForecast(mLocationModel, true);
    }
}
