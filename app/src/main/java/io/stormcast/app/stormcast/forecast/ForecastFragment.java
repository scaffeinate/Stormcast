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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

    private RelativeLayout mOverviewLayout;

    private WeatherIconView mWeatherIconView;
    private StyledTextView mLocationName;
    private StyledTextView mSummaryTextView;
    private StyledTextView mTemperatureTextView;
    private StyledTextView mMinTemperatureTextView;
    private StyledTextView mMaxTemperatureTextView;

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
                LocalForecastDataSource.getInstance(getActivity().getApplicationContext()),
                RemoteForecastDataSource.getInstance()));
        backgroundColor = Color.parseColor(mLocationModel.getBackgroundColor());
        textColor = Color.parseColor(mLocationModel.getTextColor());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mOverviewLayout = (RelativeLayout) view.findViewById(R.id.overview_layout);

        mLocationName = (StyledTextView) view.findViewById(R.id.location_name_text_view);
        mSummaryTextView = (StyledTextView) view.findViewById(R.id.summary_text_view);
        mTemperatureTextView = (StyledTextView) view.findViewById(R.id.temperature_text_view);
        mMinTemperatureTextView = (StyledTextView) view.findViewById(R.id.min_temperature_text_view);
        mMaxTemperatureTextView = (StyledTextView) view.findViewById(R.id.max_temperature_text_view);

        mWeatherIconView = (WeatherIconView) view.findViewById(R.id.weather_icon_view);
        mLocationName.setText(mLocationModel.getName());

        view.setBackgroundColor(backgroundColor);
        setColors(mOverviewLayout);

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
        mSummaryTextView.setText(forecastModel.getSummary());

        String units = forecastModel.getUnits();
        String speedUnit = "kph";
        String temperatureUnit = "C";
        int temperature = (int) forecastModel.getTemperature();
        int minTemperature = (int) forecastModel.getMinTemperature();
        int maxTemperature = (int) forecastModel.getMaxTemperature();
        int windSpeed = (int) (forecastModel.getWindSpeed() * 3.6);
        int humidity = (int) (forecastModel.getHumidity() * 100);

        if (units.equals("us")) {
            speedUnit = "mph";
            temperatureUnit = "F";
            windSpeed *= 0.62;
        }

        mTemperatureTextView.setText(formatTemperature(temperature, temperatureUnit));
        mMinTemperatureTextView.setText(formatTemperature(minTemperature, temperatureUnit));
        mMaxTemperatureTextView.setText(formatTemperature(maxTemperature, temperatureUnit));

        mSwipeRefreshLayout.setRefreshing(false);
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

    private void setColors(ViewGroup viewGroup) {
        int numChildren = viewGroup.getChildCount();
        for (int i = 0; i < numChildren; i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof StyledTextView) {
                ((StyledTextView) view).setTextColor(textColor);
            } else if (view instanceof WeatherIconView) {
                ((WeatherIconView) view).setIconColor(textColor);
            } else if (view instanceof RelativeLayout || view instanceof LinearLayout) {
                setColors((ViewGroup) view);
            } else if (view instanceof ImageView) {
                ((ImageView) view).setColorFilter(textColor);
            }
        }
    }

    private String formatTemperature(int val, String unit) {
        return new StringBuilder().append(val).append("\u00b0").append(unit).toString();
    }
}
