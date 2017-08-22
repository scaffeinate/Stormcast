
package io.stormcast.app.stormcast.common.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum__ implements Parcelable
{

    @SerializedName("time")
    @Expose
    private int time;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("sunriseTime")
    @Expose
    private int sunriseTime;
    @SerializedName("sunsetTime")
    @Expose
    private int sunsetTime;
    @SerializedName("moonPhase")
    @Expose
    private double moonPhase;
    @SerializedName("precipIntensity")
    @Expose
    private double precipIntensity;
    @SerializedName("precipIntensityMax")
    @Expose
    private double precipIntensityMax;
    @SerializedName("precipIntensityMaxTime")
    @Expose
    private int precipIntensityMaxTime;
    @SerializedName("precipProbability")
    @Expose
    private double precipProbability;
    @SerializedName("precipType")
    @Expose
    private String precipType;
    @SerializedName("temperatureMin")
    @Expose
    private double temperatureMin;
    @SerializedName("temperatureMinTime")
    @Expose
    private int temperatureMinTime;
    @SerializedName("temperatureMax")
    @Expose
    private double temperatureMax;
    @SerializedName("temperatureMaxTime")
    @Expose
    private int temperatureMaxTime;
    @SerializedName("apparentTemperatureMin")
    @Expose
    private double apparentTemperatureMin;
    @SerializedName("apparentTemperatureMinTime")
    @Expose
    private int apparentTemperatureMinTime;
    @SerializedName("apparentTemperatureMax")
    @Expose
    private double apparentTemperatureMax;
    @SerializedName("apparentTemperatureMaxTime")
    @Expose
    private int apparentTemperatureMaxTime;
    @SerializedName("dewPoint")
    @Expose
    private double dewPoint;
    @SerializedName("humidity")
    @Expose
    private double humidity;
    @SerializedName("windSpeed")
    @Expose
    private double windSpeed;
    @SerializedName("windGust")
    @Expose
    private double windGust;
    @SerializedName("windGustTime")
    @Expose
    private int windGustTime;
    @SerializedName("windBearing")
    @Expose
    private int windBearing;
    @SerializedName("visibility")
    @Expose
    private int visibility;
    @SerializedName("cloudCover")
    @Expose
    private double cloudCover;
    @SerializedName("pressure")
    @Expose
    private double pressure;
    @SerializedName("ozone")
    @Expose
    private double ozone;
    @SerializedName("uvIndex")
    @Expose
    private int uvIndex;
    @SerializedName("uvIndexTime")
    @Expose
    private int uvIndexTime;
    public final static Parcelable.Creator<Datum__> CREATOR = new Creator<Datum__>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Datum__ createFromParcel(Parcel in) {
            Datum__ instance = new Datum__();
            instance.time = ((int) in.readValue((int.class.getClassLoader())));
            instance.summary = ((String) in.readValue((String.class.getClassLoader())));
            instance.icon = ((String) in.readValue((String.class.getClassLoader())));
            instance.sunriseTime = ((int) in.readValue((int.class.getClassLoader())));
            instance.sunsetTime = ((int) in.readValue((int.class.getClassLoader())));
            instance.moonPhase = ((double) in.readValue((double.class.getClassLoader())));
            instance.precipIntensity = ((double) in.readValue((double.class.getClassLoader())));
            instance.precipIntensityMax = ((double) in.readValue((double.class.getClassLoader())));
            instance.precipIntensityMaxTime = ((int) in.readValue((int.class.getClassLoader())));
            instance.precipProbability = ((double) in.readValue((double.class.getClassLoader())));
            instance.precipType = ((String) in.readValue((String.class.getClassLoader())));
            instance.temperatureMin = ((double) in.readValue((double.class.getClassLoader())));
            instance.temperatureMinTime = ((int) in.readValue((int.class.getClassLoader())));
            instance.temperatureMax = ((double) in.readValue((double.class.getClassLoader())));
            instance.temperatureMaxTime = ((int) in.readValue((int.class.getClassLoader())));
            instance.apparentTemperatureMin = ((double) in.readValue((double.class.getClassLoader())));
            instance.apparentTemperatureMinTime = ((int) in.readValue((int.class.getClassLoader())));
            instance.apparentTemperatureMax = ((double) in.readValue((double.class.getClassLoader())));
            instance.apparentTemperatureMaxTime = ((int) in.readValue((int.class.getClassLoader())));
            instance.dewPoint = ((double) in.readValue((double.class.getClassLoader())));
            instance.humidity = ((double) in.readValue((double.class.getClassLoader())));
            instance.windSpeed = ((double) in.readValue((double.class.getClassLoader())));
            instance.windGust = ((double) in.readValue((double.class.getClassLoader())));
            instance.windGustTime = ((int) in.readValue((int.class.getClassLoader())));
            instance.windBearing = ((int) in.readValue((int.class.getClassLoader())));
            instance.visibility = ((int) in.readValue((int.class.getClassLoader())));
            instance.cloudCover = ((double) in.readValue((double.class.getClassLoader())));
            instance.pressure = ((double) in.readValue((double.class.getClassLoader())));
            instance.ozone = ((double) in.readValue((double.class.getClassLoader())));
            instance.uvIndex = ((int) in.readValue((int.class.getClassLoader())));
            instance.uvIndexTime = ((int) in.readValue((int.class.getClassLoader())));
            return instance;
        }

        public Datum__[] newArray(int size) {
            return (new Datum__[size]);
        }

    }
    ;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
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

    public int getSunriseTime() {
        return sunriseTime;
    }

    public void setSunriseTime(int sunriseTime) {
        this.sunriseTime = sunriseTime;
    }

    public int getSunsetTime() {
        return sunsetTime;
    }

    public void setSunsetTime(int sunsetTime) {
        this.sunsetTime = sunsetTime;
    }

    public double getMoonPhase() {
        return moonPhase;
    }

    public void setMoonPhase(double moonPhase) {
        this.moonPhase = moonPhase;
    }

    public double getPrecipIntensity() {
        return precipIntensity;
    }

    public void setPrecipIntensity(double precipIntensity) {
        this.precipIntensity = precipIntensity;
    }

    public double getPrecipIntensityMax() {
        return precipIntensityMax;
    }

    public void setPrecipIntensityMax(double precipIntensityMax) {
        this.precipIntensityMax = precipIntensityMax;
    }

    public int getPrecipIntensityMaxTime() {
        return precipIntensityMaxTime;
    }

    public void setPrecipIntensityMaxTime(int precipIntensityMaxTime) {
        this.precipIntensityMaxTime = precipIntensityMaxTime;
    }

    public double getPrecipProbability() {
        return precipProbability;
    }

    public void setPrecipProbability(double precipProbability) {
        this.precipProbability = precipProbability;
    }

    public String getPrecipType() {
        return precipType;
    }

    public void setPrecipType(String precipType) {
        this.precipType = precipType;
    }

    public double getTemperatureMin() {
        return temperatureMin;
    }

    public void setTemperatureMin(double temperatureMin) {
        this.temperatureMin = temperatureMin;
    }

    public int getTemperatureMinTime() {
        return temperatureMinTime;
    }

    public void setTemperatureMinTime(int temperatureMinTime) {
        this.temperatureMinTime = temperatureMinTime;
    }

    public double getTemperatureMax() {
        return temperatureMax;
    }

    public void setTemperatureMax(double temperatureMax) {
        this.temperatureMax = temperatureMax;
    }

    public int getTemperatureMaxTime() {
        return temperatureMaxTime;
    }

    public void setTemperatureMaxTime(int temperatureMaxTime) {
        this.temperatureMaxTime = temperatureMaxTime;
    }

    public double getApparentTemperatureMin() {
        return apparentTemperatureMin;
    }

    public void setApparentTemperatureMin(double apparentTemperatureMin) {
        this.apparentTemperatureMin = apparentTemperatureMin;
    }

    public int getApparentTemperatureMinTime() {
        return apparentTemperatureMinTime;
    }

    public void setApparentTemperatureMinTime(int apparentTemperatureMinTime) {
        this.apparentTemperatureMinTime = apparentTemperatureMinTime;
    }

    public double getApparentTemperatureMax() {
        return apparentTemperatureMax;
    }

    public void setApparentTemperatureMax(double apparentTemperatureMax) {
        this.apparentTemperatureMax = apparentTemperatureMax;
    }

    public int getApparentTemperatureMaxTime() {
        return apparentTemperatureMaxTime;
    }

    public void setApparentTemperatureMaxTime(int apparentTemperatureMaxTime) {
        this.apparentTemperatureMaxTime = apparentTemperatureMaxTime;
    }

    public double getDewPoint() {
        return dewPoint;
    }

    public void setDewPoint(double dewPoint) {
        this.dewPoint = dewPoint;
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

    public double getWindGust() {
        return windGust;
    }

    public void setWindGust(double windGust) {
        this.windGust = windGust;
    }

    public int getWindGustTime() {
        return windGustTime;
    }

    public void setWindGustTime(int windGustTime) {
        this.windGustTime = windGustTime;
    }

    public int getWindBearing() {
        return windBearing;
    }

    public void setWindBearing(int windBearing) {
        this.windBearing = windBearing;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public double getCloudCover() {
        return cloudCover;
    }

    public void setCloudCover(double cloudCover) {
        this.cloudCover = cloudCover;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getOzone() {
        return ozone;
    }

    public void setOzone(double ozone) {
        this.ozone = ozone;
    }

    public int getUvIndex() {
        return uvIndex;
    }

    public void setUvIndex(int uvIndex) {
        this.uvIndex = uvIndex;
    }

    public int getUvIndexTime() {
        return uvIndexTime;
    }

    public void setUvIndexTime(int uvIndexTime) {
        this.uvIndexTime = uvIndexTime;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(time);
        dest.writeValue(summary);
        dest.writeValue(icon);
        dest.writeValue(sunriseTime);
        dest.writeValue(sunsetTime);
        dest.writeValue(moonPhase);
        dest.writeValue(precipIntensity);
        dest.writeValue(precipIntensityMax);
        dest.writeValue(precipIntensityMaxTime);
        dest.writeValue(precipProbability);
        dest.writeValue(precipType);
        dest.writeValue(temperatureMin);
        dest.writeValue(temperatureMinTime);
        dest.writeValue(temperatureMax);
        dest.writeValue(temperatureMaxTime);
        dest.writeValue(apparentTemperatureMin);
        dest.writeValue(apparentTemperatureMinTime);
        dest.writeValue(apparentTemperatureMax);
        dest.writeValue(apparentTemperatureMaxTime);
        dest.writeValue(dewPoint);
        dest.writeValue(humidity);
        dest.writeValue(windSpeed);
        dest.writeValue(windGust);
        dest.writeValue(windGustTime);
        dest.writeValue(windBearing);
        dest.writeValue(visibility);
        dest.writeValue(cloudCover);
        dest.writeValue(pressure);
        dest.writeValue(ozone);
        dest.writeValue(uvIndex);
        dest.writeValue(uvIndexTime);
    }

    public int describeContents() {
        return  0;
    }

}
