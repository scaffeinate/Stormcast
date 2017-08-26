package io.stormcast.app.stormcast.data.forecast.remote;

import io.stormcast.app.stormcast.common.mappers.ForecastMapper;
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

    public static RemoteForecastDataSource getInstance() {
        if (mRemoteForecastDataSource == null) {
            mRemoteForecastDataSource = new RemoteForecastDataSource();
        }

        return mRemoteForecastDataSource;
    }

    private RemoteForecastDataSource() {
        mApiClient = DarkSkyApiClient.getInstance();
    }

    @Override
    public void loadForecast(final LocationModel locationModel, boolean manualRefresh, final LoadForecastCallback loadForecastCallback) {
        mApiClient.loadForecast(locationModel, new DarkSkyApiClient.ApiCallback() {
            @Override
            public void onLoadForecast(Forecast forecast) {
                loadForecastCallback.onForecastLoaded(ForecastMapper.map(forecast));
            }

            @Override
            public void onFailure(String errorMessage) {
                loadForecastCallback.onDataNotAvailable(errorMessage);
            }
        });
    }

    @Override
    public void saveForecast(LocationModel locationModel, ForecastModel forecastModel, SaveForecastCallback saveForecastCallback) {
        //Do Nothing
    }
}
