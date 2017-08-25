package io.stormcast.app.stormcast.location.add;

import io.stormcast.app.stormcast.common.models.LocationModel;
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
    public void validateLocation(LocationModel locationModel) {
        if (locationModel.getName() == null || locationModel.getName().trim().isEmpty() ||
                locationModel.getLatitude() == -1 || locationModel.getLongitude() == -1) {
            mView.invalidLocation("LocationModel is invalid");
            return;
        }
        mView.onValidLocation(locationModel);
    }

    @Override
    public void saveLocation(LocationModel locationModel) {
        mLocationsRepository.saveLocation(locationModel, new LocationsDataSource.SaveLocationCallback() {
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
