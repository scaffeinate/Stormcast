package io.stormcast.app.stormcast.location.list;

import java.util.List;

import io.stormcast.app.stormcast.common.models.Location;

/**
 * Created by sudhar on 8/15/17.
 */

public interface LocationsListContract {
    interface Presenter {
        void getLocations();
    }

    interface View {
        void onLocationsLoaded(List<Location> locationList);

        void onDataNotAvailable();
    }
}
