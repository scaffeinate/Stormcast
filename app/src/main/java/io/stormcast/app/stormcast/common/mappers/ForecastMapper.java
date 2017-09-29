package io.stormcast.app.stormcast.common.mappers;

import java.util.Date;

import io.stormcast.app.stormcast.common.models.ForecastModel;
import io.stormcast.app.stormcast.common.models.ForecastModelBuilder;
import io.stormcast.app.stormcast.common.network.Currently;
import io.stormcast.app.stormcast.common.network.Forecast;

/**
 * Created by sudharti on 8/25/17.
 */

public final class ForecastMapper {
    public static ForecastModel map(Forecast forecast) {
        ForecastModel model = null;
        if (forecast != null) {
            Currently currently = forecast.getCurrently();
            ForecastModelBuilder builder = new ForecastModelBuilder();
            if (currently != null) {
                builder.setTimezone(forecast.getTimezone())
                        .setCurrentTime(currently.getTime())
                        .setTemperature(currently.getTemperature())
                        .setApparentTemperature(currently.getApparentTemperature())
                        .setSummary(currently.getSummary())
                        .setIcon(currently.getIcon())
                        .setHumidity(currently.getHumidity())
                        .setPressure(currently.getPressure())
                        .setVisibility(currently.getVisibility())
                        .setWindSpeed(currently.getWindSpeed())
                        .setUpdatedAt(new Date().getTime());
            }

            if (forecast.getFlags() != null) {
                builder.setUnits(forecast.getFlags().getUnits());
            }

            model = builder.build();
        }
        return model;
    }
}
