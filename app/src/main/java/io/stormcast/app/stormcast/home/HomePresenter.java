package io.stormcast.app.stormcast.home;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import io.stormcast.app.stormcast.common.Location;

/**
 * Created by sudhar on 8/15/17.
 */

public class HomePresenter implements HomeContract.Presenter {

    @NonNull
    private final HomeContract.View mView;

    public HomePresenter(HomeContract.View view) {
        this.mView = view;
    }

    @Override
    public void loadLocations() {
        List<Location> locations = new ArrayList<>();
        mView.onLocationsLoaded(locations);
    }
}
