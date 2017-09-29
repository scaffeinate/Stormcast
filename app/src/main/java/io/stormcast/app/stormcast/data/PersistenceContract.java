package io.stormcast.app.stormcast.data;

import android.provider.BaseColumns;

/**
 * Created by sudharti on 9/24/17.
 */

public final class PersistenceContract {
    public final static String DATABASE_NAME = "Stormcast.db";
    public final static int DATABASE_VERSION = 1;

    public static abstract class LocationEntry implements BaseColumns {
        public final static String POSITION = "position";
        public final static String NAME = "name";
        public final static String LATITUDE = "latitude";
        public final static String LONGITUDE = "longitude";
        public final static String BG_COLOR = "bg_color";
        public final static String TEXT_COLOR = "text_color";
        public final static String UNIT = "unit";
        public final static String TABLE_NAME = "locations";
    }

    public static abstract class ForecastEntry implements BaseColumns {
        public final static String TIMEZONE = "timezone";
        public final static String SUMMARY = "summary";
        public final static String ICON = "icon";
        public final static String UNITS = "units";
        public final static String TEMPERATURE = "temperature";
        public final static String APPARENT_TEMPERATURE = "apparent_temperature";
        public final static String UPDATED_AT = "updated_at";
        public final static String HUMIDITY = "humidity";
        public final static String WIND_SPEED = "wind_speed";
        public final static String PRESSURE = "pressure";
        public final static String VISIBILITY = "visibility";
        public final static String LOCATION_ID = "location_id";
        public final static String TABLE_NAME = "forecast";
    }
}