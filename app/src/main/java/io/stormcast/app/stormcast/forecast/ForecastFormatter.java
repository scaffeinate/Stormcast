package io.stormcast.app.stormcast.forecast;

import java.util.Map;

import io.stormcast.app.stormcast.common.models.ForecastModel;

/**
 * Created by sudharti on 10/1/17.
 */

public interface ForecastFormatter {

    String TEMPERATURE = "temperature";
    String WIND_SPEED = "wind_speed";
    String HUMIDITY = "humidity";
    String PRESSURE = "pressure";
    String OZONE = "ozone";
    String MIN_TEMPERATURE = "min_temperature";
    String MAX_TEMPERATURE = "max_temperature";
    String SUMMARY = "summary";
    String ICON = "icon";

    String KPH = "kph";
    String MPH = "mph";
    String CELCIUS = "C";
    String FARANHEIT = "F";
    String DEGREE = "\u00b0";
    String PERCENT = "%";
    String HECTOPASCALS = "hPa";

    String IMPERIAL = "us";

    void formatForecast(ForecastModel forecastModel, ForecastFormatterCallback forecastFormatterCallback);

    interface ForecastFormatterCallback {
        void onFormatForecast(Map<String, String> formattedMap);
    }
}
