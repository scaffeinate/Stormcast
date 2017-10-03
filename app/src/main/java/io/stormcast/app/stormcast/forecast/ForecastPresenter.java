package io.stormcast.app.stormcast.forecast;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.github.pwittchen.weathericonview.WeatherIconView;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.stormcast.app.stormcast.R;
import io.stormcast.app.stormcast.common.models.DailyForecastModel;
import io.stormcast.app.stormcast.common.models.ForecastModel;
import io.stormcast.app.stormcast.common.models.LocationModel;
import io.stormcast.app.stormcast.data.forecast.ForecastDataSource;
import io.stormcast.app.stormcast.data.forecast.ForecastRepository;
import io.stormcast.app.stormcast.views.styled.StyledTextView;

/**
 * Created by sudharti on 8/24/17.
 */

public class ForecastPresenter implements ForecastContract.Presenter, ForecastFormatter {

    @NonNull
    private final ForecastContract.View mView;

    @NonNull
    private final ForecastRepository mRepository;

    public ForecastPresenter(ForecastContract.View view, ForecastRepository repository) {
        this.mView = view;
        this.mRepository = repository;
    }

    @Override
    public void loadForecast(final LocationModel locationModel, boolean isManualRefresh) {
        mRepository.loadForecast(locationModel, isManualRefresh, new ForecastDataSource.LoadForecastCallback() {
            @Override
            public void onForecastLoaded(ForecastModel forecastModel, List<DailyForecastModel> dailyForecastModels) {
                mView.onForecastLoaded(forecastModel, dailyForecastModels);
            }

            @Override
            public void onDataNotAvailable(String errorMessage) {
                mView.onDataNotAvailable(errorMessage);
            }
        });
    }

    @Override
    public void setCustomTextColor(ViewGroup parentView, int textColor) {
        int numChildren = parentView.getChildCount();
        for (int i = 0; i < numChildren; i++) {
            View view = parentView.getChildAt(i);
            if (view instanceof StyledTextView) {
                ((StyledTextView) view).setTextColor(textColor);
            } else if (view instanceof WeatherIconView) {
                ((WeatherIconView) view).setIconColor(textColor);
            } else if (view instanceof ImageView) {
                ((ImageView) view).setColorFilter(textColor);
            } else if (view instanceof RelativeLayout || view instanceof LinearLayout) {
                setCustomTextColor((ViewGroup) view, textColor);
            }
        }
    }

    @Override
    public int getIconResource(String icon, boolean isDay) {
        switch (icon) {
            case "clear-day":
                return R.string.wi_day_sunny;
            case "clear-night":
                return R.string.wi_night_clear;
            case "rain":
                return isDay ? R.string.wi_rain : R.string.wi_night_alt_rain;
            case "snow":
                return isDay ? R.string.wi_snow : R.string.wi_night_alt_snow;
            case "sleet":
                return isDay ? R.string.wi_sleet : R.string.wi_night_sleet;
            case "wind":
                return isDay ? R.string.wi_windy : R.string.wi_night_alt_cloudy_windy;
            case "fog":
                return isDay ? R.string.wi_fog : R.string.wi_night_fog;
            case "cloudy":
                return isDay ? R.string.wi_cloudy : R.string.wi_night_cloudy;
            case "partly-cloudy-day":
                return R.string.wi_day_cloudy;
            case "partly-cloudy-night":
                return R.string.wi_night_partly_cloudy;
            case "hail":
                return isDay ? R.string.wi_hail : R.string.wi_night_alt_hail;
            case "thunderstorm":
                return isDay ? R.string.wi_thunderstorm : R.string.wi_night_alt_thunderstorm;
            case "tornado":
                return R.string.wi_tornado;
            case "tsunami":
                return R.string.wi_tsunami;
            case "sandstorm":
                return R.string.wi_sandstorm;
            case "hurricane":
                return R.string.wi_hurricane;
            case "earthquake":
                return R.string.wi_earthquake;
            case "flood":
                return R.string.wi_flood;
            default:
                return R.string.wi_cloud;
        }
    }

    @Override
    public void formatForecast(ForecastModel forecastModel, ForecastFormatterCallback forecastFormatterCallback) {
        Map<String, String> formattedMap = new HashMap<>();
        String unitType = forecastModel.getUnits();
        Unit unit = new Unit(unitType);

        int temp = (int) forecastModel.getTemperature();
        int minTemp = (int) forecastModel.getMinTemperature();
        int maxTemp = (int) forecastModel.getMaxTemperature();
        int windSpeed = (int) ((forecastModel.getWindSpeed() * 3.6) * (unitType.equals(IMPERIAL) ? (0.62) : 1));
        int humidity = (int) (forecastModel.getHumidity() * 100);
        int pressure = (int) forecastModel.getPressure();

        Calendar currentTime = Calendar.getInstance();
        currentTime.setTimeInMillis(forecastModel.getCurrentTime());
        int hourOfDay = currentTime.get(Calendar.HOUR_OF_DAY);
        boolean isDay = (hourOfDay > 7 && hourOfDay < 20) ? true : false;

        formattedMap.put(TEMPERATURE, formatTemperature(temp, unit.tempUnit));
        formattedMap.put(MIN_TEMPERATURE, formatTemperature(minTemp, unit.tempUnit));
        formattedMap.put(MAX_TEMPERATURE, formatTemperature(maxTemp, unit.tempUnit));
        formattedMap.put(WIND_SPEED, formatUnit(windSpeed, unit.speedUnit));
        formattedMap.put(HUMIDITY, formatUnit(humidity, PERCENT));
        formattedMap.put(PRESSURE, formatUnit(pressure, HECTOPASCALS));
        formattedMap.put(SUMMARY, forecastModel.getSummary());
        formattedMap.put(ICON, String.valueOf(getIconResource(forecastModel.getIcon(), isDay)));
        forecastFormatterCallback.onFormatForecast(formattedMap);
    }

    @Override
    public void formatDailyForecast(DailyForecastModel dailyForecastModel, ForecastFormatterCallback forecastFormatterCallback) {
        Map<String, String> formattedMap = new HashMap<>();
        Unit unit = new Unit(dailyForecastModel.getUnits());
        formattedMap.put(TEMPERATURE, formatTemperature((int) dailyForecastModel.getTemperature(), unit.tempUnit));
        formattedMap.put(ICON, String.valueOf(getIconResource(dailyForecastModel.getIcon(), true)));
        forecastFormatterCallback.onFormatForecast(formattedMap);
    }

    private String formatTemperature(int temp, String unit) {
        return new StringBuilder().append(temp).append(DEGREE).append(unit).toString();
    }

    private String formatUnit(int speed, String unit) {
        return new StringBuilder().append(speed).append(unit).toString();
    }

    static class Unit {
        String speedUnit = KPH, tempUnit = CELCIUS;

        Unit(String unitType) {
            if (unitType.equals(IMPERIAL)) {
                this.speedUnit = MPH;
                this.tempUnit = FARANHEIT;
            }
        }
    }
}
