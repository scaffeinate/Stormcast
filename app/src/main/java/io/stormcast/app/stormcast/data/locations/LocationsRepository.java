package io.stormcast.app.stormcast.data.locations;

import io.stormcast.app.stormcast.common.models.LocationModel;

/**
 * Created by sudharti on 8/18/17.
 */

public class LocationsRepository implements LocationsDataSource {

    private static LocationsRepository sLocationsRepository;
    private LocationsDataSource mLocalDataSource;

    private LocationsRepository(LocationsDataSource locationsDataSource) {
        this.mLocalDataSource = locationsDataSource;
    }

    public static LocationsRepository getInstance(LocationsDataSource mLocalDataSource) {
        if (sLocationsRepository == null) {
            sLocationsRepository = new LocationsRepository(mLocalDataSource);
        }
        return sLocationsRepository;
    }

    @Override
    public void saveLocation(LocationModel locationModel, SaveLocationCallback callback) {
        mLocalDataSource.saveLocation(locationModel, callback);
    }

    @Override
    public void getLocations(GetLocationsCallback getLocationsCallback) {
        mLocalDataSource.getLocations(getLocationsCallback);
    }

    @Override
    public void deleteLocation(LocationModel locationModel, DeleteLocationCallback deleteLocationCallback) {
        mLocalDataSource.deleteLocation(locationModel, deleteLocationCallback);
    }
}
