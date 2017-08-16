package io.stormcast.app.stormcast.location.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import io.stormcast.app.stormcast.common.Location;

/**
 * Created by sudhar on 8/15/17.
 */

public class LocationsListAdapter extends ArrayAdapter<Location> {
    public LocationsListAdapter(@NonNull Context context) {
        super(context, 0);
    }
}
