package io.stormcast.app.stormcast.home;

import android.support.annotation.NonNull;

import java.util.List;

import io.stormcast.app.stormcast.common.models.LocationModel;
import io.stormcast.app.stormcast.data.locations.LocationsDataSource;
import io.stormcast.app.stormcast.data.locations.LocationsRepository;

/**
 * Created by sudhar on 8/15/17.
 */

public class HomePresenter implements HomeContract.Presenter {

    @NonNull
    private final HomeContract.View mView;

    @NonNull
    private final LocationsRepository mLocationsRepository;

    public HomePresenter(HomeContract.View view, LocationsRepository locationsRepository) {
        this.mView = view;
        this.mLocationsRepository = locationsRepository;
    }

    @Override
    public void loadLocations() {
        mLocationsRepository.getLocations(new LocationsDataSource.GetLocationsCallback() {
            @Override
            public void onLocationsLoaded(List<LocationModel> locationModelList) {
                mView.onLocationsLoaded(locationModelList);
            }

            @Override
            public void onDataNotAvailable() {
                mView.onDataNotAvailable();
            }
        });
    }
}
