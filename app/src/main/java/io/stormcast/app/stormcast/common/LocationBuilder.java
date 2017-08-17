package io.stormcast.app.stormcast.common;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by sudhar on 8/16/17.
 */

public class LocationBuilder {
    protected final String name;
    protected final double latitude;
    protected final double longitude;
    protected int backgroundColor = 0, textColor = 0, unit = Location.UNIT_AUTO;

    public LocationBuilder(String name, LatLng latLng) {
        this.name = name;
        this.latitude = latLng.latitude;
        this.longitude = latLng.longitude;
    }

    public LocationBuilder setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public LocationBuilder setTextColor(int textColor) {
        this.textColor = textColor;
        return this;
    }

    public LocationBuilder setUnit(int unit) {
        this.unit = unit;
        return this;
    }

    public Location build() {
        return new Location(this);
    }

}
