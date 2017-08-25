package io.stormcast.app.stormcast.data.forecast.local;

import android.content.Context;

import io.realm.Realm;
import io.stormcast.app.stormcast.common.models.Location;
import io.stormcast.app.stormcast.common.network.Forecast;
import io.stormcast.app.stormcast.data.forecast.ForecastDataSource;

/**
 * Created by sudharti on 8/22/17.
 */

public class LocalForecastDataSource implements ForecastDataSource {

    private static LocalForecastDataSource mLocalForecastDataSource;
    private Realm realm;

    private LocalForecastDataSource(Context context) {
        realm = Realm.getDefaultInstance();
    }

    public static LocalForecastDataSource getInstance(Context context) {
        if (mLocalForecastDataSource == null) {
            mLocalForecastDataSource = new LocalForecastDataSource(context);
        }
        return mLocalForecastDataSource;
    }

    @Override
    public void loadForecast(Location location, LoadForecastCallback loadForecastCallback) {
        loadForecastCallback.onDataNotAvailable("Test");
    }

    @Override
    public void saveForecast(Forecast forecast) {

    }
}
