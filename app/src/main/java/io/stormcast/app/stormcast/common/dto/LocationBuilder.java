package io.stormcast.app.stormcast.common.dto;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by sudhar on 8/16/17.
 */
public class LocationBuilder {
    protected int id;
    protected String name;
    protected double latitude = Location.MINUS_ONE;
    protected double longitude = Location.MINUS_ONE;
    protected String backgroundColor = null, textColor = null;
    protected int unit = Location.MINUS_ONE;

    public void setId(int id) {
        this.id = id;
    }

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

    public LocationBuilder setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public LocationBuilder setTextColor(String textColor) {
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
