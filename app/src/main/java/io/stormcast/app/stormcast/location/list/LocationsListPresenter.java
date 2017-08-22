package io.stormcast.app.stormcast.location.list;

import java.util.List;

import io.stormcast.app.stormcast.common.dto.Location;
import io.stormcast.app.stormcast.data.locations.LocationsDataSource;
import io.stormcast.app.stormcast.data.locations.LocationsRepository;

/**
 * Created by sudhar on 8/15/17.
 */

public class LocationsListPresenter implements LocationsListContract.Presenter {

    private LocationsListContract.View mView;
    private LocationsRepository mLocationsRepository;

    public LocationsListPresenter(LocationsListContract.View view, LocationsRepository locationsRepository) {
        this.mView = view;
        this.mLocationsRepository = locationsRepository;
    }

    @Override
    public void getLocations() {
        mLocationsRepository.getLocations(new LocationsDataSource.GetLocationsCallback() {
            @Override
            public void onLocationsLoaded(List<Location> locationList) {
                mView.onLocationsLoaded(locationList);
            }

            @Override
            public void onDataNotAvailable() {
                mView.onDataNotAvailable();
            }
        });
    }
}
