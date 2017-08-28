package io.stormcast.app.stormcast.common.models;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.Required;
import io.stormcast.app.stormcast.common.network.Hourly;

/**
 * Created by sudharti on 8/27/17.
 */

public class HourlyModel extends RealmObject implements Parcelable {
    @Required
    private Integer time;
    @Required
    private Double temperature;
    @Required
    private String icon;

    public HourlyModel() {}

    protected HourlyModel(HourlyModelBuilder builder) {
        setTime(builder.time);
        setTemperature(builder.temperature);
        setIcon(builder.icon);
    }

    protected HourlyModel(Parcel in) {
        setTime(in.readInt());
        setTemperature(in.readDouble());
        setIcon(in.readString());
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public static final Creator<HourlyModel> CREATOR = new Creator<HourlyModel>() {
        @Override
        public HourlyModel createFromParcel(Parcel in) {
            return new HourlyModel(in);
        }

        @Override
        public HourlyModel[] newArray(int size) {
            return new HourlyModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(time);
        parcel.writeDouble(temperature);
        parcel.writeString(icon);
    }
}
