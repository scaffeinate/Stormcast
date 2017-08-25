package io.stormcast.app.stormcast.data.forecast;

import io.stormcast.app.stormcast.common.models.Location;
import io.stormcast.app.stormcast.common.network.Forecast;

/**
 * Created by sudharti on 8/22/17.
 */

public class ForecastRepository implements ForecastDataSource {

    private static ForecastRepository sForecastRepository;
    private ForecastDataSource mLocalDataSource;
    private ForecastDataSource mRemoteDataSource;

    private ForecastRepository(ForecastDataSource locationsDataSource,
                               ForecastDataSource mRemoteDataSource) {
        this.mLocalDataSource = locationsDataSource;
        this.mRemoteDataSource = mRemoteDataSource;
    }

    public static ForecastRepository getInstance(ForecastDataSource mLocalDataSource,
                                                 ForecastDataSource mRemoteDataSource) {
        if (sForecastRepository == null) {
            sForecastRepository = new ForecastRepository(mLocalDataSource, mRemoteDataSource);
        }
        return sForecastRepository;
    }

    @Override
    public void loadForecast(final Location location, final LoadForecastCallback loadForecastCallback) {
        mLocalDataSource.loadForecast(location, new LoadForecastCallback() {
            @Override
            public void onForecastLoaded(Forecast forecast) {
                // If forecast last updated is older then call remoteDataSource
                getUpdateFromRemoteDataSource(location, loadForecastCallback);
                // Else
                loadForecastCallback.onForecastLoaded(forecast);
            }

            @Override
            public void onDataNotAvailable(String errorMessage) {
                getUpdateFromRemoteDataSource(location, loadForecastCallback);
            }
        });
    }

    @Override
    public void saveForecast(Forecast forecast) {
        mLocalDataSource.saveForecast(forecast);
    }

    private void getUpdateFromRemoteDataSource(Location location, final LoadForecastCallback loadForecastCallback) {
        mRemoteDataSource.loadForecast(location, new LoadForecastCallback() {
            @Override
            public void onForecastLoaded(Forecast forecast) {
                saveForecast(forecast);
                loadForecastCallback.onForecastLoaded(forecast);
            }

            @Override
            public void onDataNotAvailable(String errorMessage) {
                loadForecastCallback.onDataNotAvailable(errorMessage);
            }
        });
    }
}
