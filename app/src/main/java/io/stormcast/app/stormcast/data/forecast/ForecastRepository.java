package io.stormcast.app.stormcast.data.forecast;

import io.stormcast.app.stormcast.common.models.LocationModel;
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
    public void loadForecast(final LocationModel locationModel, final LoadForecastCallback loadForecastCallback) {
        mLocalDataSource.loadForecast(locationModel, new LoadForecastCallback() {
            @Override
            public void onForecastLoaded(Forecast forecast) {
                // If forecast last updated is older then call remoteDataSource
                getUpdateFromRemoteDataSource(locationModel, loadForecastCallback);
                // Else
                //loadForecastCallback.onForecastLoaded(forecast);
            }

            @Override
            public void onDataNotAvailable(String errorMessage) {
                getUpdateFromRemoteDataSource(locationModel, loadForecastCallback);
            }
        });
    }

    @Override
    public void saveForecast(Forecast forecast) {
        mLocalDataSource.saveForecast(forecast);
    }

    private void getUpdateFromRemoteDataSource(LocationModel locationModel, final LoadForecastCallback loadForecastCallback) {
        mRemoteDataSource.loadForecast(locationModel, new LoadForecastCallback() {
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
