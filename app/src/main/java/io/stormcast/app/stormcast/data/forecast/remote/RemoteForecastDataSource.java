package io.stormcast.app.stormcast.data.forecast.remote;

import android.util.Log;

import io.stormcast.app.stormcast.common.dto.Location;
import io.stormcast.app.stormcast.common.models.Forecast;
import io.stormcast.app.stormcast.data.forecast.ForecastDataSource;

/**
 * Created by sudharti on 8/22/17.
 */

public class RemoteForecastDataSource implements ForecastDataSource {

    private static RemoteForecastDataSource mRemoteForecastDataSource;
    private DarkSkyApiClient mApiClient;

    public static RemoteForecastDataSource getInstace() {
        if (mRemoteForecastDataSource == null) {
            mRemoteForecastDataSource = new RemoteForecastDataSource();
        }

        return mRemoteForecastDataSource;
    }

    private RemoteForecastDataSource() {
        mApiClient = DarkSkyApiClient.getInstance();
    }

    @Override
    public void loadForecast(Location location, final LoadForecastCallback loadForecastCallback) {
        mApiClient.loadForecast(location, new DarkSkyApiClient.ApiCallback() {
            @Override
            public void onLoadForecast(Forecast forecast) {
                Log.i("Forecast", forecast.getTimezone());
                //loadForecastCallback.onForecastLoaded(forecast);
            }

            @Override
            public void onFailure(String errorMessage) {
                loadForecastCallback.onDataNotAvailable(errorMessage);
            }
        });
    }
}
