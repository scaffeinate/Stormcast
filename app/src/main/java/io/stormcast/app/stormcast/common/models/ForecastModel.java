package io.stormcast.app.stormcast.common.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sudharti on 8/24/17.
 */

public class Forecast implements Parcelable {
    protected Forecast(Parcel in) {
    }

    public static final Creator<Forecast> CREATOR = new Creator<Forecast>() {
        @Override
        public Forecast createFromParcel(Parcel in) {
            return new Forecast(in);
        }

        @Override
        public Forecast[] newArray(int size) {
            return new Forecast[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
