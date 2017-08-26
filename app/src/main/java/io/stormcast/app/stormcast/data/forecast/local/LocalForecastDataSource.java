package io.stormcast.app.stormcast.data.forecast.local;

import android.content.Context;

import io.realm.Realm;
import io.realm.Realm.Transaction;
import io.stormcast.app.stormcast.common.models.ForecastModel;
import io.stormcast.app.stormcast.common.models.LocationModel;
import io.stormcast.app.stormcast.data.forecast.ForecastDataSource;

/**
 * Created by sudharti on 8/22/17.
 */

public class LocalForecastDataSource implements ForecastDataSource {

    private static LocalForecastDataSource mLocalForecastDataSource;

    private LocalForecastDataSource(Context context) {

    }

    public static LocalForecastDataSource getInstance(Context context) {
        if (mLocalForecastDataSource == null) {
            mLocalForecastDataSource = new LocalForecastDataSource(context);
        }
        return mLocalForecastDataSource;
    }

    @Override
    public void loadForecast(LocationModel locationModel, LoadForecastCallback loadForecastCallback) {
        ForecastModel forecastModel = locationModel.getForecastModel();
        if (forecastModel == null) {
            loadForecastCallback.onDataNotAvailable("No forecast available");
        } else {
            loadForecastCallback.onForecastLoaded(forecastModel);
        }
    }

    @Override
    public void saveForecast(final LocationModel locationModel, final ForecastModel forecastModel, final SaveForecastCallback saveForecastCallback) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Transaction() {
            @Override
            public void execute(Realm realm) {
                locationModel.setForecastModel(realm.copyToRealm(forecastModel));
                realm.insertOrUpdate(locationModel);
                saveForecastCallback.onForecastSaved();
            }
        });
        realm.close();
    }
}
