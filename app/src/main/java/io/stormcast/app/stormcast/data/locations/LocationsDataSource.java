package io.stormcast.app.stormcast.data.locations;

import io.stormcast.app.stormcast.common.Location;

/**
 * Created by sudharti on 8/18/17.
 */

public interface LocationsDataSource {

    interface SaveLocationCallback {
        void onLocationSaved();
        void onLocationSaveFailed(String errorMessage);
    }

    void saveLocation(Location location, SaveLocationCallback saveLocationCallback);
}
