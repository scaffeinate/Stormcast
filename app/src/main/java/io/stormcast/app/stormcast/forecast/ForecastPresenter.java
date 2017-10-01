package io.stormcast.app.stormcast.forecast;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.github.pwittchen.weathericonview.WeatherIconView;

import java.util.HashMap;
import java.util.Map;

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
            public void onForecastLoaded(ForecastModel forecastModel) {
                mView.onForecastLoaded(forecastModel);
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
    public void formatForecast(ForecastModel forecastModel, ForecastFormatterCallback forecastFormatterCallback) {
        Map<String, String> formattedMap = new HashMap<>();
        String units = forecastModel.getUnits();

        String speedUnit = KPH;
        String tempUnit = CELCIUS;

        int temp = (int) forecastModel.getTemperature();
        int minTemp = (int) forecastModel.getMinTemperature();
        int maxTemp = (int) forecastModel.getMaxTemperature();
        int windSpeed = (int) (forecastModel.getWindSpeed() * 3.6);
        int humidity = (int) (forecastModel.getHumidity() * 100);
        int pressure = (int) forecastModel.getPressure();

        if (units.equals(IMPERIAL)) {
            speedUnit = MPH;
            tempUnit = FARANHEIT;
            windSpeed *= 0.62;
        }

        formattedMap.put(TEMPERATURE, formatTemperature(temp, tempUnit));
        formattedMap.put(MIN_TEMPERATURE, formatTemperature(minTemp, tempUnit));
        formattedMap.put(MAX_TEMPERATURE, formatTemperature(maxTemp, tempUnit));
        formattedMap.put(WIND_SPEED, formatUnit(windSpeed, speedUnit));
        formattedMap.put(HUMIDITY, formatUnit(humidity, PERCENT));
        formattedMap.put(PRESSURE, formatUnit(pressure, HECTOPASCALS));
        formattedMap.put(SUMMARY, forecastModel.getSummary());
        formattedMap.put(ICON, forecastModel.getIcon());
        forecastFormatterCallback.onFormatForecast(formattedMap);
    }

    private String formatTemperature(int temp, String unit) {
        return new StringBuilder().append(temp).append(DEGREE).append(unit).toString();
    }

    private String formatUnit(int speed, String unit) {
        return new StringBuilder().append(speed).append(unit).toString();
    }
}
