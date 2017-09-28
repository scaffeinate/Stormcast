package io.stormcast.app.stormcast.location.list;

import java.util.List;

import io.stormcast.app.stormcast.common.models.LocationModel;

/**
 * Created by sudhar on 8/15/17.
 */

public interface LocationsListContract {
    interface Presenter {
        void getLocations();

        void deleteLocation(LocationModel locationModel);

        void reorder(List<LocationModel> locationModels);
    }

    interface View {
        void onLocationsLoaded(List<LocationModel> locationModelList);

        void onDataNotAvailable();

        void onLocationDeleted();

        void onLocationDeleteFailed(String errorMessage);
    }
}
