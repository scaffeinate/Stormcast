package io.stormcast.app.stormcast.data.locations.remote;

import io.stormcast.app.stormcast.common.Location;
import io.stormcast.app.stormcast.data.locations.LocationsDataSource;

/**
 * Created by sudharti on 8/18/17.
 */

public class RemoteLocationsDataSource implements LocationsDataSource {

    private static RemoteLocationsDataSource mRemoteLocationsDataSource;

    public static RemoteLocationsDataSource getInstance() {
        if (mRemoteLocationsDataSource == null) {
            mRemoteLocationsDataSource = new RemoteLocationsDataSource();
        }
        return mRemoteLocationsDataSource;
    }

    @Override
    public void saveLocation(Location location, SaveLocationCallback callback) {

    }
}
