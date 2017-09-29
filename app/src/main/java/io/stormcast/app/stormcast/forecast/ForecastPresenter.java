package io.stormcast.app.stormcast.forecast;

import android.support.annotation.NonNull;

import io.stormcast.app.stormcast.common.models.ForecastModel;
import io.stormcast.app.stormcast.common.models.LocationModel;
import io.stormcast.app.stormcast.data.forecast.ForecastDataSource;
import io.stormcast.app.stormcast.data.forecast.ForecastRepository;

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
}
