package io.stormcast.app.stormcast.common.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by sudharti on 8/24/17.
 */

public class ForecastModel implements Parcelable {

    public static final Creator<ForecastModel> CREATOR = new Creator<ForecastModel>() {
        @Override
        public ForecastModel createFromParcel(Parcel in) {
            return new ForecastModel(in);
        }

        @Override
        public ForecastModel[] newArray(int size) {
            return new ForecastModel[size];
        }
    };
    private String timezone, summary, icon, units;
    private int currentTime;
    private double temperature, apparentTemperature;
    private Date updatedAt;
    private double humidity;
    private double windSpeed;
    private double pressure;
    private double visibility;

    protected ForecastModel(Parcel in) {
        setTimezone(in.readString());
        setCurrentTime(in.readInt());
        setSummary(in.readString());
        setIcon(in.readString());
        setTemperature(in.readDouble());
        setApparentTemperature(in.readDouble());
        setHumidity(in.readDouble());
        setWindSpeed(in.readDouble());
        setPressure(in.readDouble());
        setVisibility(in.readDouble());
        setUpdatedAt((Date) in.readValue(Date.class.getClassLoader()));
        setUnits(in.readString());
    }

    protected ForecastModel(ForecastModelBuilder builder) {
        setTimezone(builder.timezone);
        setCurrentTime(builder.currentTime);
        setSummary(builder.summary);
        setIcon(builder.icon);
        setTemperature(builder.temperature);
        setApparentTemperature(builder.apparentTemperature);
        setHumidity(builder.humidity);
        setWindSpeed(builder.windSpeed);
        setPressure(builder.pressure);
        setVisibility(builder.visibility);
        setUpdatedAt(builder.updatedAt);
        setUnits(builder.units);
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Integer currentTime) {
        this.currentTime = currentTime;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public double getApparentTemperature() {
        return apparentTemperature;
    }

    public void setApparentTemperature(Double apparentTemperature) {
        this.apparentTemperature = apparentTemperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getVisibility() {
        return visibility;
    }

    public void setVisibility(double visibility) {
        this.visibility = visibility;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.timezone);
        parcel.writeInt(this.currentTime);
        parcel.writeString(this.summary);
        parcel.writeString(this.icon);
        parcel.writeDouble(this.temperature);
        parcel.writeDouble(this.apparentTemperature);
        parcel.writeDouble(this.humidity);
        parcel.writeDouble(this.windSpeed);
        parcel.writeDouble(this.pressure);
        parcel.writeDouble(this.visibility);
        parcel.writeValue(this.updatedAt);
        parcel.writeString(this.units);
    }
}
