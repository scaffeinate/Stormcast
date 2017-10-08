package io.stormcast.app.stormcast.forecast;

import io.stormcast.app.stormcast.common.models.LocationModel;

/**
 * Created by sudharti on 10/8/17.
 */

public interface GetLocationModelCallback {
    void getLocationModel(int position, OnGetLocationModel callback);

    interface OnGetLocationModel {
        void onLocationModelLoaded(LocationModel locationModel);
    }
}
