package io.stormcast.app.stormcast.common.models;

/**
 * Created by sudharti on 10/3/17.
 */

public class FormattedDailyForecastModelBuilder {
    protected String icon, temperature, time;

    public FormattedDailyForecastModelBuilder setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public FormattedDailyForecastModelBuilder setTemperature(String temperature) {
        this.temperature = temperature;
        return this;
    }

    public FormattedDailyForecastModelBuilder setTime(String time) {
        this.time = time;
        return this;
    }

    public FormattedDailyForecastModel build() {
        return new FormattedDailyForecastModel(this);
    }
}
