package io.stormcast.app.stormcast.common.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Forecast implements Parcelable {

    public final static Parcelable.Creator<Forecast> CREATOR = new Creator<Forecast>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Forecast createFromParcel(Parcel in) {
            Forecast instance = new Forecast();
            instance.latitude = ((double) in.readValue((double.class.getClassLoader())));
            instance.longitude = ((double) in.readValue((double.class.getClassLoader())));
            instance.timezone = ((String) in.readValue((String.class.getClassLoader())));
            instance.offset = ((int) in.readValue((int.class.getClassLoader())));
            instance.currently = ((Currently) in.readValue((Currently.class.getClassLoader())));
            instance.minutely = ((Minutely) in.readValue((Minutely.class.getClassLoader())));
            instance.hourly = ((Hourly) in.readValue((Hourly.class.getClassLoader())));
            instance.daily = ((Daily) in.readValue((Daily.class.getClassLoader())));
            instance.flags = ((Flags) in.readValue((Flags.class.getClassLoader())));
            return instance;
        }

        public Forecast[] newArray(int size) {
            return (new Forecast[size]);
        }

    };
    @SerializedName("latitude")
    @Expose
    private double latitude;
    @SerializedName("longitude")
    @Expose
    private double longitude;
    @SerializedName("timezone")
    @Expose
    private String timezone;
    @SerializedName("offset")
    @Expose
    private int offset;
    @SerializedName("currently")
    @Expose
    private Currently currently;
    @SerializedName("minutely")
    @Expose
    private Minutely minutely;
    @SerializedName("hourly")
    @Expose
    private Hourly hourly;
    @SerializedName("daily")
    @Expose
    private Daily daily;
    @SerializedName("flags")
    @Expose
    private Flags flags;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public Currently getCurrently() {
        return currently;
    }

    public void setCurrently(Currently currently) {
        this.currently = currently;
    }

    public Minutely getMinutely() {
        return minutely;
    }

    public void setMinutely(Minutely minutely) {
        this.minutely = minutely;
    }

    public Hourly getHourly() {
        return hourly;
    }

    public void setHourly(Hourly hourly) {
        this.hourly = hourly;
    }

    public Daily getDaily() {
        return daily;
    }

    public void setDaily(Daily daily) {
        this.daily = daily;
    }

    public Flags getFlags() {
        return flags;
    }

    public void setFlags(Flags flags) {
        this.flags = flags;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(latitude);
        dest.writeValue(longitude);
        dest.writeValue(timezone);
        dest.writeValue(offset);
        dest.writeValue(currently);
        dest.writeValue(minutely);
        dest.writeValue(hourly);
        dest.writeValue(daily);
        dest.writeValue(flags);
    }

    public int describeContents() {
        return 0;
    }

}
