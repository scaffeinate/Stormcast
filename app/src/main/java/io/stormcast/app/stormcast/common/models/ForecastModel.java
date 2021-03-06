package io.stormcast.app.stormcast.common.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

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
    private int currentTime, locationId, uvIndex;
    private double temperature, apparentTemperature, minTemperature, maxTemperature;
    private double humidity, ozone, windSpeed, visibility, pressure;
    private long updatedAt;
    private List<DailyForecastModel> dailyForecastModels;

    protected ForecastModel(Parcel in) {
        setTimezone(in.readString());
        setCurrentTime(in.readInt());
        setSummary(in.readString());
        setIcon(in.readString());
        setTemperature(in.readDouble());
        setApparentTemperature(in.readDouble());
        setMinTemperature(in.readDouble());
        setMaxTemperature(in.readDouble());
        setHumidity(in.readDouble());
        setWindSpeed(in.readDouble());
        setPressure(in.readDouble());
        setVisibility(in.readDouble());
        setOzone(in.readDouble());
        setUvIndex(in.readInt());
        setUpdatedAt(in.readLong());
        setUnits(in.readString());
        setLocationId(in.readInt());
        in.readTypedList(this.dailyForecastModels, DailyForecastModel.CREATOR);
    }

    protected ForecastModel(ForecastModelBuilder builder) {
        setTimezone(builder.timezone);
        setCurrentTime(builder.currentTime);
        setSummary(builder.summary);
        setIcon(builder.icon);
        setTemperature(builder.temperature);
        setApparentTemperature(builder.apparentTemperature);
        setMinTemperature(builder.minTemperature);
        setMaxTemperature(builder.maxTemperature);
        setHumidity(builder.humidity);
        setWindSpeed(builder.windSpeed);
        setPressure(builder.pressure);
        setVisibility(builder.visibility);
        setOzone(builder.ozone);
        setUvIndex(builder.uvIndex);
        setUpdatedAt(builder.updatedAt);
        setUnits(builder.units);
        setLocationId(builder.locationId);
        setDailyForecastModels(builder.dailyForecastModels);
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

    public int getUvIndex() {
        return uvIndex;
    }

    public void setUvIndex(int uvIndex) {
        this.uvIndex = uvIndex;
    }

    public double getOzone() {
        return ozone;
    }

    public void setOzone(double ozone) {
        this.ozone = ozone;
    }

    public double getVisibility() {
        return visibility;
    }

    public void setVisibility(double visibility) {
        this.visibility = visibility;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }


    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }


    public double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public List<DailyForecastModel> getDailyForecastModels() {
        return dailyForecastModels;
    }

    public void setDailyForecastModels(List<DailyForecastModel> dailyForecastModels) {
        this.dailyForecastModels = dailyForecastModels;
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
        parcel.writeDouble(this.minTemperature);
        parcel.writeDouble(this.maxTemperature);
        parcel.writeDouble(this.humidity);
        parcel.writeDouble(this.windSpeed);
        parcel.writeDouble(this.pressure);
        parcel.writeDouble(this.visibility);
        parcel.writeDouble(this.ozone);
        parcel.writeInt(this.uvIndex);
        parcel.writeLong(this.updatedAt);
        parcel.writeString(this.units);
        parcel.writeInt(this.locationId);
        parcel.writeTypedList(this.dailyForecastModels);
    }
}
