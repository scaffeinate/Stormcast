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
    public static ForecastModel map(Forecast forecast) {
        ForecastModel model = null;
        if (forecast != null) {
            Currently currently = forecast.getCurrently();
            Daily daily = forecast.getDaily();
            List<Datum__> dailyData = daily.getData();
            List<DailyForecastModel> dailyForecastModelList = new ArrayList<>();
            String units = "us";

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

                if (!dailyData.isEmpty()) {
                    builder.setMinTemperature(dailyData.get(0).getTemperatureMin())
                            .setMaxTemperature(dailyData.get(0).getTemperatureMax());
                } else {
                    builder.setMinTemperature(currently.getTemperature())
                            .setMaxTemperature(currently.getTemperature());
                }
            }

            if (forecast.getFlags() != null) {
                units = forecast.getFlags().getUnits();
            }

            for (int i = 1; i < 5; i++) {
                Datum__ datum__ = dailyData.get(i);
                DailyForecastModel dailyForecastModel = new DailyForecastModelBuilder()
                        .setIcon(datum__.getIcon())
                        .setTime(datum__.getTime())
                        .setTemperature((datum__.getTemperatureMin() + datum__.getTemperatureMax()) / 2)
                        .setUnits(units)
                        .build();
                dailyForecastModelList.add(dailyForecastModel);
            }

            builder.setDailyForecastModels(dailyForecastModelList).setUnits(units);
            model = builder.build();
        }
        return model;
    }
}
