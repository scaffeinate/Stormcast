package io.stormcast.app.stormcast.forecast;

import android.support.annotation.NonNull;
import android.widget.Toast;

import io.stormcast.app.stormcast.common.mappers.ForecastMapper;
import io.stormcast.app.stormcast.common.models.ForecastModel;
import io.stormcast.app.stormcast.common.models.LocationModel;
import io.stormcast.app.stormcast.common.network.Forecast;
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
    public void loadForecast(final LocationModel locationModel, boolean manualRefresh) {
        mRepository.loadForecast(locationModel, manualRefresh, new ForecastDataSource.LoadForecastCallback() {
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
