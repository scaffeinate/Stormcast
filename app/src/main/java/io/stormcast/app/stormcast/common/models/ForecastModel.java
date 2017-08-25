package io.stormcast.app.stormcast.common.models;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by sudharti on 8/24/17.
 */

public class ForecastModel extends RealmObject implements Parcelable {

    private LocationModel locationModel;

    @Required
    private String timezone;
    @Required
    private Integer currentTime;
    @Required
    private String summary;
    @Required
    private String icon;
    @Required
    private Double temperature, apparentTemperature;

    private double humidity;
    private double windSpeed;
    private double pressure;
    private double visibility;

    public ForecastModel() {
    }

    protected ForecastModel(Parcel in) {
        setLocationModel((LocationModel) in.readValue(LocationModel.class.getClassLoader()));
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
    }

    protected ForecastModel(ForecastModelBuilder builder) {

    }

    public LocationModel getLocationModel() {
        return locationModel;
    }

    public void setLocationModel(LocationModel locationModel) {
        this.locationModel = locationModel;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Integer getCurrentTime() {
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

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getApparentTemperature() {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(this.locationModel);
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
    }
}
