package io.stormcast.app.stormcast.data.locations.local;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.stormcast.app.stormcast.common.models.LocationModel;
import io.stormcast.app.stormcast.data.locations.LocationsDataSource;

/**
 * Created by sudharti on 8/18/17.
 */

public class LocalLocationsDataSource implements LocationsDataSource {

    private static LocalLocationsDataSource mLocalLocationsDataSource;
    private Realm realm;

    private LocalLocationsDataSource() {
        realm = Realm.getDefaultInstance();
    }

    public static LocalLocationsDataSource getInstance() {
        if (mLocalLocationsDataSource == null) {
            mLocalLocationsDataSource = new LocalLocationsDataSource();
        }

        return mLocalLocationsDataSource;
    }

    @Override
    public void saveLocation(final LocationModel locationModel, final SaveLocationCallback saveLocationCallback) {

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(locationModel);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                saveLocationCallback.onLocationSaved();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                saveLocationCallback.onLocationSaveFailed(error.getMessage());
            }
        });
    }

    @Override
    public void getLocations(GetLocationsCallback getLocationsCallback) {
        RealmQuery<LocationModel> query = realm.where(LocationModel.class);
        RealmResults<LocationModel> results = query.findAll();
        if (results.size() == 0) {
            getLocationsCallback.onDataNotAvailable();
        } else {
            getLocationsCallback.onLocationsLoaded(results);
        }
    }
}
