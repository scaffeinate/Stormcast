package io.stormcast.app.stormcast.data.locations;

import io.stormcast.app.stormcast.common.Location;

/**
 * Created by sudharti on 8/18/17.
 */

public class LocationsRepository implements LocationsDataSource {

    private static LocationsRepository sLocationsRepository;
    private LocationsDataSource mLocalDataSource;
    private LocationsDataSource mRemoteDataSource;

    public static LocationsRepository getInstance(LocationsDataSource mLocalDataSource,
                                                  LocationsDataSource mRemoteDataSource) {
        if (sLocationsRepository == null) {
            sLocationsRepository = new LocationsRepository(mLocalDataSource, mRemoteDataSource);
        }
        return sLocationsRepository;
    }

    private LocationsRepository(LocationsDataSource locationsDataSource,
                                LocationsDataSource mRemoteDataSource) {
        this.mLocalDataSource = locationsDataSource;
        this.mRemoteDataSource = mRemoteDataSource;
    }

    @Override
    public void saveLocation(Location location) {
        mLocalDataSource.saveLocation(location);
    }
}
