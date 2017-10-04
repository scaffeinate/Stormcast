package io.stormcast.app.stormcast.data.forecast.remote;

import java.util.List;

import io.stormcast.app.stormcast.common.mappers.DailyForecastMapper;
import io.stormcast.app.stormcast.common.mappers.ForecastMapper;
import io.stormcast.app.stormcast.common.models.DailyForecastModel;
import io.stormcast.app.stormcast.common.models.ForecastModel;
import io.stormcast.app.stormcast.common.models.LocationModel;
import io.stormcast.app.stormcast.common.network.Forecast;
import io.stormcast.app.stormcast.data.forecast.ForecastDataSource;

/**
 * Created by sudharti on 8/22/17.
 */

public class RemoteForecastDataSource implements ForecastDataSource {

    private static RemoteForecastDataSource mRemoteForecastDataSource;
    private DarkSkyApiClient mApiClient;

    private RemoteForecastDataSource() {
        mApiClient = DarkSkyApiClient.getInstance();
    }

    public static RemoteForecastDataSource getInstance() {
        if (mRemoteForecastDataSource == null) {
            mRemoteForecastDataSource = new RemoteForecastDataSource();
        }

        return mRemoteForecastDataSource;
    }

    @Override
    public void loadForecast(final LocationModel locationModel, boolean isManualRefresh, final LoadForecastCallback loadForecastCallback) {
        mApiClient.loadForecast(locationModel, new DarkSkyApiClient.ApiCallback() {
            @Override
            public void onLoadForecast(Forecast forecast) {
                ForecastModel forecastModel = ForecastMapper.map(forecast, locationModel.getId());
                List<DailyForecastModel> dailyForecastModels = DailyForecastMapper.map(forecast, locationModel.getId());
                loadForecastCallback.onForecastLoaded(forecastModel);
            }

            @Override
            public void onFailure(String errorMessage) {
                loadForecastCallback.onDataNotAvailable(errorMessage);
            }
        });
    }
}
