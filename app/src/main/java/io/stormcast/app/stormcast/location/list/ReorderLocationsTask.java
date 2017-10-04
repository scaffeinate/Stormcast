package io.stormcast.app.stormcast.location.list;

import android.os.AsyncTask;

import java.util.List;

import io.stormcast.app.stormcast.common.models.LocationModel;

/**
 * Created by sudharti on 9/28/17.
 */

public class ReorderLocationsTask extends AsyncTask<Void, Integer, Void> {

    private List<LocationModel> mLocationModelList;
    private int fromPosition = 0, toPosition = 0;
    private LocationsListContract.Presenter mPresenter;

    public ReorderLocationsTask(LocationsListContract.Presenter presenter, List<LocationModel> locationModelList,
                                int fromPosition, int toPosition) {
        this.mPresenter = presenter;
        this.mLocationModelList = locationModelList;
        this.fromPosition = fromPosition;
        this.toPosition = toPosition;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i <= toPosition; i++) {
                LocationModel locationModel = mLocationModelList.get(i);
                locationModel.setPosition(i + 1);
            }
        } else {
            for (int i = toPosition; i <= fromPosition; i++) {
                LocationModel locationModel = mLocationModelList.get(i);
                locationModel.setPosition(i + 1);
            }
        }
        mPresenter.reorder(mLocationModelList);
        return null;
    }
}
