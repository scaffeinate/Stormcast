package io.stormcast.app.stormcast.util;

import java.util.Calendar;

import io.stormcast.app.stormcast.common.models.DailyForecastModel;
import io.stormcast.app.stormcast.common.models.ForecastModel;
import io.stormcast.app.stormcast.common.models.FormattedDailyForecastModel;
import io.stormcast.app.stormcast.common.models.FormattedDailyForecastModelBuilder;
import io.stormcast.app.stormcast.common.models.FormattedForecastModel;
import io.stormcast.app.stormcast.common.models.FormattedForecastModelBuilder;

/**
 * Created by sudharti on 10/1/17.
 */

public class ForecastFormatter {

    private final static String KPH = "kph";
    private final static String MPH = "mph";
    private final static String CELCIUS = "C";
    private final static String FARANHEIT = "F";
    private final static String DEGREE = "\u00b0";
    private final static String PERCENT = "%";
    private final static String HECTOPASCALS = "hPa";
    private final static String MILLIBAR = "mb";
    private final static String MILE = "mi";
    private final static String DU = "DU";

    private final static String IMPERIAL = "us";
    private final static String[] daysOfWeek = new String[]{
            "SUN", "MON", "TUE", "WED", "THUR", "FRI", "SAT"
    };

    public static FormattedForecastModel formatForecast(ForecastModel forecastModel) {
        String unitType = forecastModel.getUnits();
        Unit unit = new Unit(unitType);
        FormattedForecastModelBuilder builder = new FormattedForecastModelBuilder();

        int temp = (int) forecastModel.getTemperature();
        int apparentTemp = (int) forecastModel.getApparentTemperature();
        int minTemp = (int) forecastModel.getMinTemperature();
        int maxTemp = (int) forecastModel.getMaxTemperature();
        int windSpeed = (int) ((forecastModel.getWindSpeed() * 3.6) * (unitType.equals(IMPERIAL) ? (0.62) : 1));
        int humidity = (int) (forecastModel.getHumidity() * 100);
        int pressure = (int) forecastModel.getPressure();
        int visibility = (int) forecastModel.getVisibility();
        int uvIndex = (int) forecastModel.getUvIndex();
        int ozone = (int) forecastModel.getOzone();

        Calendar currentTime = Calendar.getInstance();
        currentTime.setTimeInMillis((long) forecastModel.getCurrentTime() * 1000);
        int hourOfDay = currentTime.get(Calendar.HOUR_OF_DAY);
        boolean isDay = (hourOfDay > 7 && hourOfDay < 20) ? true : false;
        String icon = forecastModel.getIcon();

        return builder.setTemperature(formatTemperature(temp, unit.tempUnit))
                .setApparentTemperature(formatTemperature(apparentTemp, unit.tempUnit))
                .setMinTemperature(formatTemperature(minTemp, unit.tempUnit))
                .setMaxTemperature(formatTemperature(maxTemp, unit.tempUnit))
                .setWindSpeed(formatUnit(windSpeed, unit.speedUnit))
                .setHumidity(formatUnit(humidity, PERCENT))
                .setPressure(formatUnit(pressure, unit.pressureUnit))
                .setOzone(formatUnit(ozone, DU))
                .setUVIndex(String.valueOf(uvIndex))
                .setVisibility(formatUnit(visibility, MILE))
                .setSummary(forecastModel.getSummary())
                .setIcon(String.valueOf(IconResource.getIconResource(icon, isDay)))
                .build();
    }

    public static FormattedDailyForecastModel formatDailyForecast(DailyForecastModel dailyForecastModel) {
        String unitType = dailyForecastModel.getUnits();
        Unit unit = new Unit(unitType);
        FormattedDailyForecastModelBuilder builder = new FormattedDailyForecastModelBuilder();

        int temp = (int) dailyForecastModel.getTemperature();
        String icon = dailyForecastModel.getIcon();
        Calendar currentTime = Calendar.getInstance();
        currentTime.setTimeInMillis((long) dailyForecastModel.getTime() * 1000);
        int dayOfWeek = currentTime.get(Calendar.DAY_OF_WEEK);

        return builder.setTemperature(formatTemperature(temp, unit.tempUnit))
                .setIcon(String.valueOf(IconResource.getIconResource(icon, true)))
                .setTime(daysOfWeek[dayOfWeek - 1])
                .build();
    }

    private static String formatTemperature(int temp, String unit) {
        return new StringBuilder().append(temp).append(DEGREE).append(unit).toString();
    }

    private static String formatUnit(int value, String unit) {
        return new StringBuilder().append(value).append((unit == PERCENT) ? "" : " ").append(unit).toString();
    }

    static class Unit {
        String speedUnit = KPH, tempUnit = CELCIUS, pressureUnit = HECTOPASCALS;

        Unit(String unitType) {
            if (unitType.equals(IMPERIAL)) {
                this.speedUnit = MPH;
                this.tempUnit = FARANHEIT;
                this.pressureUnit = MILLIBAR;
            }
        }
    }
}
