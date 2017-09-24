package io.stormcast.app.stormcast.data.forecast.local;

import android.content.Context;

import io.stormcast.app.stormcast.common.models.ForecastModel;
import io.stormcast.app.stormcast.common.models.LocationModel;
import io.stormcast.app.stormcast.data.forecast.ForecastDataSource;

/**
 * Created by sudharti on 8/22/17.
 */

public class LocalForecastDataSource implements ForecastDataSource {

    private static LocalForecastDataSource mLocalForecastDataSource;
    private ForecastDbHelper mForecastDbHelper;

    private LocalForecastDataSource(Context context) {
        mForecastDbHelper = new ForecastDbHelper(context);
    }

    public static LocalForecastDataSource getInstance(Context context) {
        if (mLocalForecastDataSource == null) {
            mLocalForecastDataSource = new LocalForecastDataSource(context);
        }
        return mLocalForecastDataSource;
    }

    @Override
    public void loadForecast(LocationModel locationModel, boolean forceRefresh, LoadForecastCallback loadForecastCallback) {

    }

    @Override
    public void saveForecast(final LocationModel locationModel, final ForecastModel forecastModel, final SaveForecastCallback saveForecastCallback) {

    }
}
