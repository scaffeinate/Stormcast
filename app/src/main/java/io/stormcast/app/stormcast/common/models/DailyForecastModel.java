package io.stormcast.app.stormcast.common.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sudharti on 10/1/17.
 */

public class DailyForecastModel implements Parcelable {

    public static final Creator<DailyForecastModel> CREATOR = new Creator<DailyForecastModel>() {
        @Override
        public DailyForecastModel createFromParcel(Parcel in) {
            return new DailyForecastModel(in);
        }

        @Override
        public DailyForecastModel[] newArray(int size) {
            return new DailyForecastModel[size];
        }
    };

    private String icon, units;
    private int time, updatedAt, locationId;
    private double temperature;

    protected DailyForecastModel(Parcel in) {
        setIcon(in.readString());
        setUnits(in.readString());
        setTime(in.readInt());
        setUpdatedAt(in.readInt());
        setTemperature(in.readDouble());
        setLocationId(in.readInt());
    }

    protected DailyForecastModel(DailyForecastModelBuilder builder) {
        setIcon(builder.icon);
        setUnits(builder.units);
        setTime(builder.time);
        setUpdatedAt(builder.updatedAt);
        setTemperature(builder.temperature);
        setLocationId(builder.locationId);
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public int getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(int updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.icon);
        parcel.writeString(this.units);
        parcel.writeInt(this.time);
        parcel.writeInt(this.updatedAt);
        parcel.writeDouble(this.temperature);
        parcel.writeInt(this.locationId);
    }
}
