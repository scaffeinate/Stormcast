package io.stormcast.app.stormcast.common;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sudhar on 8/15/17.
 */

public class Location implements Parcelable {

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
    private String name;
    private Double latitude = 0d, longitude = 0d;

    public Location(Parcel parcel) {
        this.name = parcel.readString();
        this.latitude = parcel.readDouble();
        this.longitude = parcel.readDouble();
    }

    public Location(String name, Double latitude, Double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.name);
        parcel.writeDouble(this.latitude);
        parcel.writeDouble(this.longitude);
    }
}
