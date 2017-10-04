package io.stormcast.app.stormcast.forecast;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.github.pwittchen.weathericonview.WeatherIconView;

import java.util.List;

import io.stormcast.app.stormcast.common.models.DailyForecastModel;
import io.stormcast.app.stormcast.common.models.ForecastModel;
import io.stormcast.app.stormcast.common.models.FormattedDailyForecastModel;
import io.stormcast.app.stormcast.common.models.LocationModel;
import io.stormcast.app.stormcast.data.forecast.ForecastDataSource;
import io.stormcast.app.stormcast.data.forecast.ForecastRepository;
import io.stormcast.app.stormcast.views.styled.StyledTextView;

/**
 * Created by sudharti on 8/24/17.
 */

public class ForecastPresenter implements ForecastContract.Presenter {

    @NonNull
    private final ForecastContract.View mView;

    @NonNull
    private final ForecastRepository mRepository;

    public ForecastPresenter(ForecastContract.View view, ForecastRepository repository) {
        this.mView = view;
        this.mRepository = repository;
    }

    @Override
    public void loadForecast(final LocationModel locationModel, boolean isManualRefresh) {
        mRepository.loadForecast(locationModel, isManualRefresh, new ForecastDataSource.LoadForecastCallback() {
            @Override
            public void onForecastLoaded(ForecastModel forecastModel) {
                mView.onForecastLoaded(forecastModel);
            }

            @Override
            public void onDataNotAvailable(String errorMessage) {
                mView.onDataNotAvailable(errorMessage);
            }
        });
    }

    @Override
    public void setCustomTextColor(ViewGroup parentView, int textColor) {
        int numChildren = parentView.getChildCount();
        for (int i = 0; i < numChildren; i++) {
            View view = parentView.getChildAt(i);
            if (view instanceof StyledTextView) {
                ((StyledTextView) view).setTextColor(textColor);
            } else if (view instanceof WeatherIconView) {
                ((WeatherIconView) view).setIconColor(textColor);
            } else if (view instanceof ImageView) {
                ((ImageView) view).setColorFilter(textColor);
            } else if (view instanceof RelativeLayout || view instanceof LinearLayout) {
                setCustomTextColor((ViewGroup) view, textColor);
            }
        }
    }
}
