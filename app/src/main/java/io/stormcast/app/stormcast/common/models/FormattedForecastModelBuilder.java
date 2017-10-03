package io.stormcast.app.stormcast.common.models;

/**
 * Created by sudharti on 10/3/17.
 */

public class FormattedForecastModelBuilder {
    protected String temperature, minTemperature, maxTemperature,
            windSpeed, humidity, pressure, summary, icon;

    public FormattedForecastModelBuilder setTemperature(String temperature) {
        this.temperature = temperature;
        return this;
    }

    public FormattedForecastModelBuilder setMinTemperature(String minTemperature) {
        this.minTemperature = minTemperature;
        return this;
    }

    public FormattedForecastModelBuilder setMaxTemperature(String maxTemperature) {
        this.maxTemperature = maxTemperature;
        return this;
    }

    public FormattedForecastModelBuilder setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
        return this;
    }

    public FormattedForecastModelBuilder setHumidity(String humidity) {
        this.humidity = humidity;
        return this;
    }

    public FormattedForecastModelBuilder setPressure(String pressure) {
        this.pressure = pressure;
        return this;
    }

    public FormattedForecastModelBuilder setSummary(String summary) {
        this.summary = summary;
        return this;
    }

    public FormattedForecastModelBuilder setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public FormattedForecastModel build() {
        return new FormattedForecastModel(this);
    }
}
