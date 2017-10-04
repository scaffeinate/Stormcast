package io.stormcast.app.stormcast.location.add;

import android.content.Context;

import com.google.android.gms.location.places.Place;

import io.stormcast.app.stormcast.common.models.LocationModel;

/**
 * Created by sudhar on 8/15/17.
 */

public interface AddLocationContract {
    interface View {
        void onValidLocation(LocationModel locationModel);

        void invalidLocation(String message);

        void onLocationSaved();

        void onLocationSaveFailed(String errorMessage);
    }

    interface Presenter {
        void validateLocation(LocationModel locationModel);

        void saveLocation(LocationModel locationModel);
    }
}
