package io.stormcast.app.stormcast.common.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sudhar on 8/15/17.
 */

public class LocationModel implements Parcelable {

    public static final int UNIT_IMPERIAL = 0;
    public static final int UNIT_METRIC = 1;
    public static final int UNIT_AUTO = 2;
    public static final String DEFAULT_BACKGROUND_COLOR = "#019c70";
    public static final String DEFAULT_TEXT_COLOR = "#FFFFFF";
    protected final static int MINUS_ONE = -1;
    private static final double DEFAULT_LATITUDE = 0;
    private static final double DEFAULT_LONGITUDE = 0;

    private String name, backgroundColor = DEFAULT_BACKGROUND_COLOR, textColor = DEFAULT_TEXT_COLOR;
    private double latitude = DEFAULT_LATITUDE, longitude = DEFAULT_LONGITUDE;
    private int unit = UNIT_AUTO, position = 0;

    private ForecastModel forecastModel;

    public LocationModel() {
    }

    protected LocationModel(LocationModelBuilder locationModelBuilder) {
        setName(locationModelBuilder.name);
        setLatitude(locationModelBuilder.latitude);
        setLongitude(locationModelBuilder.longitude);
        setBackgroundColor(locationModelBuilder.backgroundColor);
        setTextColor(locationModelBuilder.textColor);
        setUnit(locationModelBuilder.unit);
        setPosition(locationModelBuilder.position);
    }

    private LocationModel(Parcel parcel) {
        setName(parcel.readString());
        setLatitude(parcel.readDouble());
        setLongitude(parcel.readDouble());
        setBackgroundColor(parcel.readString());
        setTextColor(parcel.readString());
        setUnit(parcel.readInt());
        setPosition(parcel.readInt());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        if (longitude != MINUS_ONE) {
            this.latitude = latitude;
        }
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        if (longitude != MINUS_ONE) {
            this.longitude = longitude;
        }
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        if (unit != MINUS_ONE) this.unit = unit;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(this.name);
        parcel.writeDouble(this.latitude);
        parcel.writeDouble(this.longitude);
        parcel.writeString(this.backgroundColor);
        parcel.writeString(this.textColor);
        parcel.writeInt(this.unit);
        parcel.writeInt(this.position);
    }

    public static final Parcelable.Creator CREATOR = new Creator() {
        @Override
        public Object createFromParcel(Parcel parcel) {
            return new LocationModel(parcel);
        }

        @Override
        public Object[] newArray(int size) {
            return new LocationModel[size];
        }
    };
}