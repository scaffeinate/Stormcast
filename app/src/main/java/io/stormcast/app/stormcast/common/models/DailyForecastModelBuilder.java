package io.stormcast.app.stormcast.common.models;

/**
 * Created by sudharti on 10/2/17.
 */

public class DailyForecastModelBuilder {
    protected String icon, units;
    protected int time, updatedAt, locationId;
    protected double temperature;

    public DailyForecastModelBuilder setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public DailyForecastModelBuilder setUnits(String units) {
        this.units = units;
        return this;
    }

    public DailyForecastModelBuilder setTime(int time) {
        this.time = time;
        return this;
    }

    public DailyForecastModelBuilder setUpdatedAt(int updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public DailyForecastModelBuilder setTemperature(double temperature) {
        this.temperature = temperature;
        return this;
    }

    public DailyForecastModelBuilder setLocationId(int locationId) {
        this.locationId = locationId;
        return this;
    }

    public DailyForecastModel build() {
        return new DailyForecastModel(this);
    }
}
