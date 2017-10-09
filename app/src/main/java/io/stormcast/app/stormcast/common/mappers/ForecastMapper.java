package io.stormcast.app.stormcast.common.mappers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.stormcast.app.stormcast.common.models.DailyForecastModel;
import io.stormcast.app.stormcast.common.models.DailyForecastModelBuilder;
import io.stormcast.app.stormcast.common.models.ForecastModel;
import io.stormcast.app.stormcast.common.models.ForecastModelBuilder;
import io.stormcast.app.stormcast.common.network.Currently;
import io.stormcast.app.stormcast.common.network.Daily;
import io.stormcast.app.stormcast.common.network.Datum__;
import io.stormcast.app.stormcast.common.network.Forecast;

/**
 * Created by sudharti on 8/25/17.
 */

public final class ForecastMapper {
    public static ForecastModel map(final Forecast forecast, final int locationId) {
        ForecastModel model = null;
        if (forecast != null) {
            Currently currently = forecast.getCurrently();
            Daily daily = forecast.getDaily();
            List<Datum__> dailyData = daily.getData();

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
                        .setOzone(currently.getOzone())
                        .setUvIndex(currently.getUvIndex())
                        .setVisibility(currently.getVisibility())
                        .setWindSpeed(currently.getWindSpeed())
                        .setUpdatedAt(new Date().getTime())
                        .setUnits(forecast.getFlags().getUnits())
                        .setLocationId(locationId);

                if (!dailyData.isEmpty()) {
                    builder.setMinTemperature(dailyData.get(0).getTemperatureMin())
                            .setMaxTemperature(dailyData.get(0).getTemperatureMax());
                } else {
                    builder.setMinTemperature(currently.getTemperature())
                            .setMaxTemperature(currently.getTemperature());
                }
            }

            List<DailyForecastModel> dailyForecastModels = new ArrayList<>();
            for (int i = 1; i < 5; i++) {
                Datum__ datum__ = dailyData.get(i);
                DailyForecastModel dailyForecastModel = new DailyForecastModelBuilder()
                        .setIcon(datum__.getIcon())
                        .setTime(datum__.getTime())
                        .setTemperature((datum__.getTemperatureMin() + datum__.getTemperatureMax()) / 2)
                        .setUnits(forecast.getFlags().getUnits())
                        .setLocationId(locationId)
                        .build();
                dailyForecastModels.add(dailyForecastModel);
            }

            builder.setDailyForecastModels(dailyForecastModels);
            model = builder.build();
        }
        return model;
    }
}
