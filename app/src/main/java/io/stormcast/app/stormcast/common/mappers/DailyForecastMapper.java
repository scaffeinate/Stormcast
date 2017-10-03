package io.stormcast.app.stormcast.common.mappers;

import java.util.ArrayList;
import java.util.List;

import io.stormcast.app.stormcast.common.models.DailyForecastModel;
import io.stormcast.app.stormcast.common.models.DailyForecastModelBuilder;
import io.stormcast.app.stormcast.common.network.Datum__;
import io.stormcast.app.stormcast.common.network.Forecast;

/**
 * Created by sudharti on 10/2/17.
 */

public class DailyForecastMapper {
    public static List<DailyForecastModel> map(final Forecast forecast, final int locationId) {
        List<Datum__> dailyData = forecast.getDaily().getData();
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
        return dailyForecastModels;
    }
}
