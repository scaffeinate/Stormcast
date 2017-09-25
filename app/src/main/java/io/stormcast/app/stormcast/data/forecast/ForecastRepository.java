package io.stormcast.app.stormcast.data.forecast;

import io.stormcast.app.stormcast.common.models.ForecastModel;
import io.stormcast.app.stormcast.common.models.LocationModel;

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
    public void loadForecast(final LocationModel locationModel, final boolean forceRefresh, final LoadForecastCallback loadForecastCallback) {

    }

    @Override
    public void saveForecast(LocationModel locationModel, ForecastModel forecastModel, SaveForecastCallback saveForecastCallback) {
        mLocalDataSource.saveForecast(locationModel, forecastModel, saveForecastCallback);
    }

    private void getUpdateFromRemoteDataSource(final LocationModel locationModel, final boolean forceRefresh,
                                               final LoadForecastCallback loadForecastCallback) {
        mRemoteDataSource.loadForecast(locationModel, forceRefresh, new LoadForecastCallback() {
            @Override
            public void onForecastLoaded(final ForecastModel forecastModel) {
                saveForecast(locationModel, forecastModel, new SaveForecastCallback() {
                    @Override
                    public void onForecastSaved() {
                        loadForecastCallback.onForecastLoaded(forecastModel);
                    }

                    @Override
                    public void onLocationSaveFailed(String errorMessage) {
                        loadForecastCallback.onDataNotAvailable(errorMessage);
                    }
                });
            }

            @Override
            public void onDataNotAvailable(String errorMessage) {
                mLocalDataSource.loadForecast(locationModel, forceRefresh, loadForecastCallback);
            }
        });
    }
}
