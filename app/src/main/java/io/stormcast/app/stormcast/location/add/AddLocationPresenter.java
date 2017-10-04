package io.stormcast.app.stormcast.location.add;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

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
            mView.invalidLocation("Invalid Location");
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
