package io.stormcast.app.stormcast.common;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sudhar on 8/15/17.
 */

public class Location implements Parcelable {

    public static final int UNIT_IMPERIAL = 0;
    public static final int UNIT_METRIC = 1;
    public static final int UNIT_AUTO = 2;

    private String name;
    private double latitude = 0d, longitude = 0d;
    private int backgroundColor = 0, textColor = 0;
    private int unit = -1;

    protected Location(LocationBuilder locationBuilder) {
        this.name = locationBuilder.name;
        this.latitude = locationBuilder.latitude;
        this.longitude = locationBuilder.longitude;
        this.backgroundColor = locationBuilder.backgroundColor;
        this.textColor = locationBuilder.textColor;
        this.unit = locationBuilder.unit;
    }

    public Location(Parcel parcel) {
        this.name = parcel.readString();
        this.latitude = parcel.readDouble();
        this.longitude = parcel.readDouble();
        this.backgroundColor = parcel.readInt();
        this.textColor = parcel.readInt();
        this.unit = parcel.readInt();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }


    public int getBackgroundColor() {
        return backgroundColor;
    }

    public int getTextColor() {
        return textColor;
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
        parcel.writeString(this.name);
        parcel.writeDouble(this.latitude);
        parcel.writeDouble(this.longitude);
        parcel.writeInt(this.backgroundColor);
        parcel.writeInt(this.textColor);
        parcel.writeInt(this.unit);
    }
}
