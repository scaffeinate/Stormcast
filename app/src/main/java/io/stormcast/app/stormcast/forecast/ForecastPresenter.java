package io.stormcast.app.stormcast.forecast;

import android.support.annotation.NonNull;

import io.stormcast.app.stormcast.common.models.Forecast;
import io.stormcast.app.stormcast.common.models.Location;
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
    public void loadForecast(Location location) {
        mRepository.loadForecast(location, new ForecastDataSource.LoadForecastCallback() {
            @Override
            public void onForecastLoaded(Forecast forecast) {
                mView.onForecastLoaded(forecast);
            }

            @Override
            public void onDataNotAvailable(String errorMessage) {
                mView.onDataNotAvailable(errorMessage);
            }
        });
    }
}
