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
    private int unit = UNIT_AUTO;

    protected Location(LocationBuilder locationBuilder) {
        this(locationBuilder.name, locationBuilder.latitude, locationBuilder.longitude,
                locationBuilder.backgroundColor, locationBuilder.textColor, locationBuilder.unit);
    }

    private Location(String name, double latitude, double longitude, int backgroundColor, int textColor, int unit) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
        this.unit = unit;
    }

    private Location(Parcel parcel) {
        this(parcel.readString(),
                parcel.readDouble(),
                parcel.readDouble(),
                parcel.readInt(),
                parcel.readInt(),
                parcel.readInt());
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
