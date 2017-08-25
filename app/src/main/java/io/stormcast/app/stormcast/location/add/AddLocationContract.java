package io.stormcast.app.stormcast.location.add;

import io.stormcast.app.stormcast.common.models.Location;

/**
 * Created by sudhar on 8/15/17.
 */

public interface AddLocationContract {
    interface View {
        void onValidLocation(Location location);

        void invalidLocation(String message);

        void onLocationSaved();

        void onLocationSaveFailed(String errorMessage);
    }

    interface Presenter {
        void validateLocation(Location location);

        void saveLocation(Location location);
    }
}
