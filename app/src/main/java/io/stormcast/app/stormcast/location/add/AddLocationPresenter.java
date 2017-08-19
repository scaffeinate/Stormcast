package io.stormcast.app.stormcast.location.add;

import io.stormcast.app.stormcast.common.Location;

/**
 * Created by sudhar on 8/15/17.
 */

public class AddLocationPresenter implements AddLocationContract.Presenter {

    private final AddLocationContract.View mView;

    public AddLocationPresenter(AddLocationContract.View view) {
        this.mView = view;
    }

    @Override
    public void validateLocation(Location location) {
        if (location.getName() == null || location.getName().trim().isEmpty() ||
                location.getLatitude() == -1 || location.getLongitude() == -1) {
            mView.invalidLocation("Location is invalid");
            return;
        }
        mView.onValidLocation(location);
    }

    @Override
    public void saveLocation(Location location) {
        mView.onLocationSaved();
    }
}
