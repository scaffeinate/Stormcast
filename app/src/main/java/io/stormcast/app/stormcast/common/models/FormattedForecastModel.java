package io.stormcast.app.stormcast.common.models;

/**
 * Created by sudharti on 10/3/17.
 */

public class FormattedForecastModel {
    private String temperature, minTemperature, maxTemperature,
            windSpeed, humidity, pressure, summary, icon;

    protected FormattedForecastModel(FormattedForecastModelBuilder builder) {
        setTemperature(builder.temperature);
        setMinTemperature(builder.minTemperature);
        setMaxTemperature(builder.maxTemperature);
        setWindSpeed(builder.windSpeed);
        setHumidity(builder.humidity);
        setPressure(builder.pressure);
        setSummary(builder.summary);
        setIcon(builder.icon);
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(String minTemperature) {
        this.minTemperature = minTemperature;
    }

    public String getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(String maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
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
}
