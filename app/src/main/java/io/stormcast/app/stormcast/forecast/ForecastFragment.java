package io.stormcast.app.stormcast.forecast;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.pwittchen.weathericonview.WeatherIconView;

import java.util.List;

import io.stormcast.app.stormcast.R;
import io.stormcast.app.stormcast.common.models.DailyForecastModel;
import io.stormcast.app.stormcast.common.models.ForecastModel;
import io.stormcast.app.stormcast.common.models.FormattedDailyForecastModel;
import io.stormcast.app.stormcast.common.models.FormattedForecastModel;
import io.stormcast.app.stormcast.common.models.LocationModel;
import io.stormcast.app.stormcast.data.forecast.ForecastRepository;
import io.stormcast.app.stormcast.data.forecast.local.LocalForecastDataSource;
import io.stormcast.app.stormcast.data.forecast.remote.RemoteForecastDataSource;
import io.stormcast.app.stormcast.home.GetLocationModelCallback;
import io.stormcast.app.stormcast.util.ForecastFormatter;
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

    private LinearLayout mDailyForecastLayout;
    private RelativeLayout mTodayForecastLayout;
    private RelativeLayout mTomoForecastLayout;
    private RelativeLayout mDayAfterForecastLayout;
    private RelativeLayout mTwoDaysFromNowForecastLayout;

    private RecyclerView mAdditionalInfoRecyclerView;

    private ProgressBar mProgressBar;

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

        mDailyForecastLayout = (LinearLayout) view.findViewById(R.id.daily_forecast_layout);
        mTodayForecastLayout = (RelativeLayout) view.findViewById(R.id.today_forecast);
        mTomoForecastLayout = (RelativeLayout) view.findViewById(R.id.tomo_forecast);
        mDayAfterForecastLayout = (RelativeLayout) view.findViewById(R.id.day_after_tomo_forecast);
        mTwoDaysFromNowForecastLayout = (RelativeLayout) view.findViewById(R.id.two_days_from_now_forecast);

        mAdditionalInfoRecyclerView = (RecyclerView) view.findViewById(R.id.additional_info_recycler_view);

        mProgressBar = (ProgressBar) view.findViewById(R.id.forecast_progress_bar);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchLocationModel();
    }

    @Override
    public void onForecastLoaded(final ForecastModel forecastModel) {
        if (!isAdded()) return;
        mSwipeRefreshLayout.setRefreshing(false);
        mForecastLayout.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);

        FormattedForecastModel formattedForecastModel = ForecastFormatter.formatForecast(forecastModel);
        mTemperatureTextView.setText(formattedForecastModel.getTemperature());
        mMinTemperatureTextView.setText(formattedForecastModel.getMinTemperature());
        mMaxTemperatureTextView.setText(formattedForecastModel.getMaxTemperature());
        mSummaryTextView.setText(formattedForecastModel.getSummary());
        mWeatherIconView.setIconResource(getResources().getString(Integer.parseInt(formattedForecastModel.getIcon())));

        List<DailyForecastModel> dailyForecastModels = forecastModel.getDailyForecastModels();

        if (dailyForecastModels != null && dailyForecastModels.size() == 4) {
            populateDailyForecastView(mTodayForecastLayout, ForecastFormatter.formatDailyForecast(dailyForecastModels.get(0)));
            populateDailyForecastView(mTomoForecastLayout, ForecastFormatter.formatDailyForecast(dailyForecastModels.get(1)));
            populateDailyForecastView(mDayAfterForecastLayout, ForecastFormatter.formatDailyForecast(dailyForecastModels.get(2)));
            populateDailyForecastView(mTwoDaysFromNowForecastLayout, ForecastFormatter.formatDailyForecast(dailyForecastModels.get(3)));
        }

    }

    @Override
    public void onDataNotAvailable(String errorMessage) {
        mSwipeRefreshLayout.setRefreshing(false);
        mProgressBar.setVisibility(View.GONE);
        Toast.makeText(mContext, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        if (mLocationModel != null) {
            mSwipeRefreshLayout.setRefreshing(true);
            mPresenter.loadForecast(mLocationModel, true);
        }
    }

    public void animateViews() {
        final Animation fadeInAnimation = AnimationUtils.loadAnimation(mContext, R.anim.fade_in);
        final Animation bounceAnimation = AnimationUtils.loadAnimation(mContext, R.anim.scale_in);
        mSummaryTextView.startAnimation(fadeInAnimation);
        mWeatherIconView.startAnimation(bounceAnimation);
        mTemperatureTextView.startAnimation(fadeInAnimation);
        mMinTemperatureTextView.startAnimation(fadeInAnimation);
        mMaxTemperatureTextView.startAnimation(fadeInAnimation);
        mDailyForecastLayout.startAnimation(fadeInAnimation);
    }

    private void fetchLocationModel() {
        int position = getArguments().getInt(POSITION);
        GetLocationModelCallback callback = ((GetLocationModelCallback) getParentFragment());
        callback.getLocationModel(position, new GetLocationModelCallback.OnGetLocationModel() {
            @Override
            public void onLocationModelLoaded(LocationModel locationModel) {
                mLocationModel = locationModel;
                backgroundColor = Color.parseColor(mLocationModel.getBackgroundColor());
                textColor = Color.parseColor(mLocationModel.getTextColor());
                getView().setBackgroundColor(backgroundColor);
                mPresenter.setCustomTextColor(mForecastLayout, textColor);
                mProgressBar.getIndeterminateDrawable().mutate().setColorFilter(textColor, PorterDuff.Mode.SRC_IN);

                mPresenter.loadForecast(mLocationModel, false);
                animateViews();
            }
        });
    }

    private void populateDailyForecastView(RelativeLayout layout, FormattedDailyForecastModel model) {
        StyledTextView timeTextView = (StyledTextView) layout.findViewById(R.id.time_text_view);
        WeatherIconView dailyWeatherIcon = (WeatherIconView) layout.findViewById(R.id.daily_weather_icon_view);
        StyledTextView temperatureTextView = (StyledTextView) layout.findViewById(R.id.temperature_text_view);

        timeTextView.setText(model.getTime());
        dailyWeatherIcon.setIconResource(getResources().getString(Integer.parseInt(model.getIcon())));
        temperatureTextView.setText(model.getTemperature());
    }
}
