package io.stormcast.app.stormcast.data.locations;

import java.util.List;

import io.stormcast.app.stormcast.common.models.LocationModel;

/**
 * Created by sudharti on 8/18/17.
 */

public interface LocationsDataSource {

    void saveLocation(LocationModel locationModel, SaveLocationCallback saveLocationCallback);

    void getLocations(GetLocationsCallback getLocationsCallback);

    void deleteLocation(LocationModel locationModel, DeleteLocationCallback deleteLocationCallback);

    interface SaveLocationCallback {
        void onLocationSaved();

        void onLocationSaveFailed(String errorMessage);
    }

    interface GetLocationsCallback {
        void onLocationsLoaded(List<LocationModel> locationModelList);

        void onDataNotAvailable();
    }

    interface DeleteLocationCallback {
        void onLocationDeleted();

        void onLocationDeleteFailed(String errorMessage);
    }
}
