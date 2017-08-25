package io.stormcast.app.stormcast.location.add;

import io.stormcast.app.stormcast.common.models.Location;
import io.stormcast.app.stormcast.data.locations.LocationsDataSource;
import io.stormcast.app.stormcast.data.locations.LocationsRepository;

/**
 * Created by sudhar on 8/15/17.
 */

public class AddLocationPresenter implements AddLocationContract.Presenter {

    private final AddLocationContract.View mView;
    private LocationsRepository mLocationsRepository;

    public AddLocationPresenter(AddLocationContract.View view, LocationsRepository locationsRepository) {
        this.mView = view;
        this.mLocationsRepository = locationsRepository;
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
        mLocationsRepository.saveLocation(location, new LocationsDataSource.SaveLocationCallback() {
            @Override
            public void onLocationSaved() {
                mView.onLocationSaved();
            }

            @Override
            public void onLocationSaveFailed(String errorMessage) {
                mView.onLocationSaveFailed(errorMessage);
            }
        });
    }
}
