package io.stormcast.app.stormcast.common.models;

/**
 * Created by sudharti on 8/27/17.
 */

public class HourlyModelBuilder {
    protected int time;
    protected double temperature;
    protected String icon;

    public HourlyModelBuilder setTime(Integer time) {
        this.time = time;
        return this;
    }

    public HourlyModelBuilder setTemperature(Double temperature) {
        this.temperature = temperature;
        return this;
    }

    public HourlyModelBuilder setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public HourlyModel build() {
        return new HourlyModel(this);
    }
}
