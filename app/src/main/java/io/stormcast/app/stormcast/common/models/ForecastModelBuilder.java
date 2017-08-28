package io.stormcast.app.stormcast.common.models;

import java.util.Date;
import java.util.List;

/**
 * Created by sudharti on 8/25/17.
 */

public class ForecastModelBuilder {
    protected String timezone;
    protected Integer currentTime;
    protected String summary;
    protected String icon;
    protected Double temperature, apparentTemperature;

    protected double humidity;
    protected double windSpeed;
    protected double pressure;
    protected double visibility;

    protected Date updatedAt;
    protected List<HourlyModel> hourlyModels;
    protected String units;

    public ForecastModelBuilder setTimezone(String timezone) {
        this.timezone = timezone;
        return this;
    }

    public ForecastModelBuilder setCurrentTime(Integer currentTime) {
        this.currentTime = currentTime;
        return this;
    }

    public ForecastModelBuilder setSummary(String summary) {
        this.summary = summary;
        return this;
    }

    public ForecastModelBuilder setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public ForecastModelBuilder setTemperature(Double temperature) {
        this.temperature = temperature;
        return this;
    }

    public ForecastModelBuilder setApparentTemperature(Double apparentTemperature) {
        this.apparentTemperature = apparentTemperature;
        return this;
    }

    public ForecastModelBuilder setHumidity(double humidity) {
        this.humidity = humidity;
        return this;
    }

    public ForecastModelBuilder setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
        return this;
    }

    public ForecastModelBuilder setPressure(double pressure) {
        this.pressure = pressure;
        return this;
    }

    public ForecastModelBuilder setVisibility(double visibility) {
        this.visibility = visibility;
        return this;
    }

    public ForecastModelBuilder setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public ForecastModelBuilder setHourlyModels(List<HourlyModel> hourlyModels) {
        this.hourlyModels = hourlyModels;
        return this;
    }

    public ForecastModelBuilder setUnits(String units) {
        this.units = units;
        return this;
    }

    public ForecastModel build() {
        return new ForecastModel(this);
    }
}
