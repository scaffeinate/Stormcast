package io.stormcast.app.stormcast.forecast;

import io.stormcast.app.stormcast.common.dto.Forecast;
import io.stormcast.app.stormcast.common.dto.Location;

/**
 * Created by sudharti on 8/24/17.
 */

public interface ForecastContract {
    interface View {
        void onForecastLoaded(Forecast forecast);

        void onDataNotAvailable(String errorMessage);
    }

    interface Presenter {
        void loadForecast(Location location);
    }
}
