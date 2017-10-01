package io.stormcast.app.stormcast.forecast;

import android.view.ViewGroup;

import java.util.Map;

import io.stormcast.app.stormcast.common.models.ForecastModel;
import io.stormcast.app.stormcast.common.models.LocationModel;

/**
 * Created by sudharti on 8/24/17.
 */

public interface ForecastContract {
    interface View {
        void onForecastLoaded(ForecastModel forecastModel);

        void onDataNotAvailable(String errorMessage);

    }

    interface Presenter {

        void loadForecast(LocationModel locationModel, boolean isManualRefresh);

        void setCustomTextColor(ViewGroup parentView, int textColor);

    }
}
