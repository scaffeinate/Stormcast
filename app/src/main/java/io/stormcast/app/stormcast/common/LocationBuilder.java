package io.stormcast.app.stormcast.common;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by sudhar on 8/16/17.
 */
public class LocationBuilder {
    protected String name;
    protected double latitude;
    protected double longitude;
    protected int backgroundColor = 0, textColor = 0, unit = Location.UNIT_AUTO;

    public LocationBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public LocationBuilder setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public LocationBuilder setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    public LocationBuilder setLatLng(LatLng latLng) {
        this.latitude = latLng.latitude;
        this.longitude = latLng.longitude;
        return this;
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
