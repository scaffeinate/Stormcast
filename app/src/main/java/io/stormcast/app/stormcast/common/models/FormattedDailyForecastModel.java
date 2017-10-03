package io.stormcast.app.stormcast.common.models;

/**
 * Created by sudharti on 10/3/17.
 */

public class FormattedDailyForecastModel {
    private String icon, temperature, time;

    protected FormattedDailyForecastModel(FormattedDailyForecastModelBuilder builder) {
        setIcon(builder.icon);
        setTemperature(builder.temperature);
        setTime(builder.time);
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
