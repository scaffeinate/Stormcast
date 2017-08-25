package io.stormcast.app.stormcast.data.locations;

import java.util.List;

import io.stormcast.app.stormcast.common.models.Location;

/**
 * Created by sudharti on 8/18/17.
 */

public interface LocationsDataSource {

    void saveLocation(Location location, SaveLocationCallback saveLocationCallback);

    void getLocations(GetLocationsCallback getLocationsCallback);

    interface SaveLocationCallback {
        void onLocationSaved();

        void onLocationSaveFailed(String errorMessage);
    }

    interface GetLocationsCallback {
        void onLocationsLoaded(List<Location> locationList);

        void onDataNotAvailable();
    }
}
