package io.stormcast.app.stormcast.data.forecast;

import java.util.Date;
import java.util.List;

import io.stormcast.app.stormcast.common.models.DailyForecastModel;
import io.stormcast.app.stormcast.common.models.ForecastModel;
import io.stormcast.app.stormcast.common.models.LocationModel;
import io.stormcast.app.stormcast.data.forecast.local.LocalForecastDataSource;

/**
 * Created by sudharti on 8/22/17.
 */

public class ForecastRepository implements ForecastDataSource {

    private static final int UPDATE_THRESHOLD = 30;

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
    public void loadForecast(final LocationModel locationModel, final boolean isManualRefresh, final LoadForecastCallback loadForecastCallback) {
        if (isManualRefresh) {
            getUpdateFromRemoteDataSource(locationModel, isManualRefresh, loadForecastCallback);
        } else {
            mLocalDataSource.loadForecast(locationModel, isManualRefresh, new LoadForecastCallback() {
                @Override
                public void onForecastLoaded(ForecastModel forecastModel, List<DailyForecastModel> dailyForecastModels) {
                    long lastUpdatedAt = forecastModel.getUpdatedAt();
                    long now = new Date().getTime();
                    int diffInMinutes = (int) (now - lastUpdatedAt) / 60000;
                    if (diffInMinutes <= UPDATE_THRESHOLD) {
                        loadForecastCallback.onForecastLoaded(forecastModel, dailyForecastModels);
                    } else {
                        getUpdateFromRemoteDataSource(locationModel, isManualRefresh, loadForecastCallback);
                    }
                }

                @Override
                public void onDataNotAvailable(String errorMessage) {
                    getUpdateFromRemoteDataSource(locationModel, isManualRefresh, loadForecastCallback);
                }
            });
        }
    }

    private void getUpdateFromRemoteDataSource(final LocationModel locationModel, final boolean isManualRefresh, final LoadForecastCallback loadForecastCallback) {
        mRemoteDataSource.loadForecast(locationModel, isManualRefresh, new LoadForecastCallback() {
            @Override
            public void onForecastLoaded(final ForecastModel forecastModel, final List<DailyForecastModel> dailyForecastModels) {
                loadForecastCallback.onForecastLoaded(forecastModel, dailyForecastModels);
                LocalForecastDataSource localForecastDataSource = (LocalForecastDataSource) mLocalDataSource;
                localForecastDataSource.saveForecast(forecastModel, locationModel.getId());
                localForecastDataSource.saveDailyForecasts(dailyForecastModels, locationModel.getId());
            }

            @Override
            public void onDataNotAvailable(String errorMessage) {
                mLocalDataSource.loadForecast(locationModel, isManualRefresh, loadForecastCallback);
            }
        });
    }
}
