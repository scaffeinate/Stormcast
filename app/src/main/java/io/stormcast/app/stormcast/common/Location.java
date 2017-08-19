package io.stormcast.app.stormcast.common;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sudhar on 8/15/17.
 */

public class Location implements Parcelable {

    protected final static int MINUS_ONE = -1;

    public static final int UNIT_IMPERIAL = 0;
    public static final int UNIT_METRIC = 1;
    public static final int UNIT_AUTO = 2;

    public static final String DEFAULT_BACKGROUND_COLOR = "#3F51B5";
    public static final String DEFAULT_TEXT_COLOR = "#FFFFFF";

    private static final double DEFAULT_LATITUDE = 0;
    private static final double DEFAULT_LONGITUDE = 0;

    private int id = 0;
    private String name;
    private double latitude = DEFAULT_LATITUDE, longitude = DEFAULT_LONGITUDE;
    private String backgroundColor = DEFAULT_BACKGROUND_COLOR, textColor = DEFAULT_TEXT_COLOR;
    private int unit = UNIT_AUTO;

    protected Location(LocationBuilder locationBuilder) {
        setId(locationBuilder.id);
        setName(locationBuilder.name);
        setLatitude(locationBuilder.latitude);
        setLongitude(locationBuilder.longitude);
        setBackgroundColor(locationBuilder.backgroundColor);
        setTextColor(locationBuilder.textColor);
        setUnit(locationBuilder.unit);
    }

    private Location(Parcel parcel) {
        setId(parcel.readInt());
        setName(parcel.readString());
        setLatitude(parcel.readDouble());
        setLongitude(parcel.readDouble());
        setBackgroundColor(parcel.readString());
        setTextColor(parcel.readString());
        setUnit(parcel.readInt());
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
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
        if (longitude != MINUS_ONE) this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        if (longitude != MINUS_ONE) this.longitude = longitude;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        if (backgroundColor != null && !backgroundColor.trim().isEmpty()) {
            this.backgroundColor = backgroundColor;
        }
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        if (textColor != null && !textColor.trim().isEmpty()) {
            this.textColor = textColor;
        }
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        if (unit != MINUS_ONE) this.unit = unit;
    }

    public static final Parcelable.Creator CREATOR = new Creator() {
        @Override
        public Object createFromParcel(Parcel parcel) {
            return new Location(parcel);
        }

        @Override
        public Object[] newArray(int size) {
            return new Location[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.name);
        parcel.writeDouble(this.latitude);
        parcel.writeDouble(this.longitude);
        parcel.writeString(this.backgroundColor);
        parcel.writeString(this.textColor);
        parcel.writeInt(this.unit);
    }
}