package io.stormcast.app.stormcast.common.models;

/**
 * Created by sudharti on 10/3/17.
 */

public class FormattedForecastModel {
    private String temperature, apparentTemperature, minTemperature, maxTemperature,
            windSpeed, humidity, pressure, ozone, uvIndex, visibility, summary, icon;

    protected FormattedForecastModel(FormattedForecastModelBuilder builder) {
        setTemperature(builder.temperature);
        setApparentTemperature(builder.apparentTemperature);
        setMinTemperature(builder.minTemperature);
        setMaxTemperature(builder.maxTemperature);
        setWindSpeed(builder.windSpeed);
        setHumidity(builder.humidity);
        setPressure(builder.pressure);
        setOzone(builder.ozone);
        setUvIndex(builder.uvIndex);
        setVisibility(builder.visibility);
        setSummary(builder.summary);
        setIcon(builder.icon);
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getApparentTemperature() {
        return apparentTemperature;
    }

    public void setApparentTemperature(String apparentTemperature) {
        this.apparentTemperature = apparentTemperature;
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

    public String getOzone() {
        return ozone;
    }

    public void setOzone(String ozone) {
        this.ozone = ozone;
    }

    public String getUvIndex() {
        return uvIndex;
    }

    public void setUvIndex(String uvIndex) {
        this.uvIndex = uvIndex;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
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
