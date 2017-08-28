package io.stormcast.app.stormcast.common.mappers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.stormcast.app.stormcast.common.models.ForecastModel;
import io.stormcast.app.stormcast.common.models.ForecastModelBuilder;
import io.stormcast.app.stormcast.common.models.HourlyModel;
import io.stormcast.app.stormcast.common.models.HourlyModelBuilder;
import io.stormcast.app.stormcast.common.network.Currently;
import io.stormcast.app.stormcast.common.network.Datum_;
import io.stormcast.app.stormcast.common.network.Forecast;
import io.stormcast.app.stormcast.common.network.Hourly;

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
                        .setUpdatedAt(new Date());
            }

            if (forecast.getFlags() != null) {
                builder.setUnits(forecast.getFlags().getUnits());
            }

            model = builder.build();
        }
        return model;
    }

    public static List<HourlyModel> mapHourlyData(Forecast forecast) {
        List<HourlyModel> models = new ArrayList<>();
        if (forecast != null) {
            Hourly hourly = forecast.getHourly();
            if (hourly != null) {
                List<Datum_> data = hourly.getData();
                for (int i = 0; i < data.size(); i++) {
                    Datum_ datum_ = data.get(i);
                    HourlyModel model = new HourlyModelBuilder()
                            .setTime(datum_.getTime())
                            .setIcon(datum_.getIcon())
                            .setTemperature(datum_.getTemperature())
                            .build();
                    models.add(model);
                }
            }
        }
        return models;
    }
}
