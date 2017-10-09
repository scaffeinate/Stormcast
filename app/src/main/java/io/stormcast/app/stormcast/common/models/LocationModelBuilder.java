package io.stormcast.app.stormcast.common.models;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by sudhar on 8/16/17.
 */
public class LocationModelBuilder {
    protected int id;
    protected String name, address;
    protected double latitude = LocationModel.MINUS_ONE;
    protected double longitude = LocationModel.MINUS_ONE;
    protected String backgroundColor = null, textColor = null;
    protected int unit = LocationModel.MINUS_ONE, position = 0;

    public LocationModelBuilder() {
    }

    public LocationModelBuilder(LocationModel locationModel) {
        setId(locationModel.getId())
                .setName(locationModel.getName())
                .setAddress(locationModel.getAddress())
                .setLatitude(locationModel.getLatitude())
                .setLongitude(locationModel.getLongitude())
                .setBackgroundColor(locationModel.getBackgroundColor())
                .setTextColor(locationModel.getTextColor())
                .setUnit(locationModel.getPosition())
                .setPosition(locationModel.getPosition());
    }

    public LocationModelBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public LocationModelBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public LocationModelBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    public LocationModelBuilder setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public LocationModelBuilder setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    public LocationModelBuilder setLatLng(LatLng latLng) {
        this.latitude = latLng.latitude;
        this.longitude = latLng.longitude;
        return this;
    }

    public LocationModelBuilder setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public LocationModelBuilder setTextColor(String textColor) {
        this.textColor = textColor;
        return this;
    }

    public LocationModelBuilder setUnit(int unit) {
        this.unit = unit;
        return this;
    }

    public LocationModelBuilder setPosition(int position) {
        this.position = position;
        return this;
    }

    public LocationModel build() {
        return new LocationModel(this);
    }
}
