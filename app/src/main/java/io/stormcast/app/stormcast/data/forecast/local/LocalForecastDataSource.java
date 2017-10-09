package io.stormcast.app.stormcast.data.forecast.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import io.stormcast.app.stormcast.common.models.DailyForecastModel;
import io.stormcast.app.stormcast.common.models.DailyForecastModelBuilder;
import io.stormcast.app.stormcast.common.models.ForecastModel;
import io.stormcast.app.stormcast.common.models.ForecastModelBuilder;
import io.stormcast.app.stormcast.common.models.LocationModel;
import io.stormcast.app.stormcast.common.network.Forecast;
import io.stormcast.app.stormcast.data.DbHelper;
import io.stormcast.app.stormcast.data.PersistenceContract;
import io.stormcast.app.stormcast.data.forecast.ForecastDataSource;

/**
 * Created by sudharti on 8/22/17.
 */

public class LocalForecastDataSource implements ForecastDataSource {

    private static LocalForecastDataSource mLocalForecastDataSource;
    private DbHelper mDbHelper;
    private final Gson gson = new Gson();

    private LocalForecastDataSource(Context context) {
        mDbHelper = DbHelper.getInstance(context);
    }

    public static LocalForecastDataSource getInstance(Context context) {
        if (mLocalForecastDataSource == null) {
            mLocalForecastDataSource = new LocalForecastDataSource(context);
        }
        return mLocalForecastDataSource;
    }

    @Override
    public void loadForecast(final LocationModel locationModel, boolean isManualRefresh, LoadForecastCallback loadForecastCallback) {
        new FetchForecastTask(locationModel, loadForecastCallback).execute();
    }

    public void saveForecast(final ForecastModel forecastModel) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        populate(cv, forecastModel);
        String where = PersistenceContract.ForecastEntry.LOCATION_ID + " = ? ";
        String[] whereArgs = new String[]{String.valueOf(forecastModel.getLocationId())};
        try {
            int numRowsAffected = database.update(PersistenceContract.ForecastEntry.TABLE_NAME, cv, where, whereArgs);

            if (numRowsAffected == 0) {
                database.insert(PersistenceContract.ForecastEntry.TABLE_NAME, null, cv);
            }
        } catch (SQLiteException e) {

        } finally {
            database.close();
        }
    }

    private void populate(ContentValues cv, ForecastModel forecastModel) {
        cv.put(PersistenceContract.ForecastEntry.TEMPERATURE, forecastModel.getTemperature());
        cv.put(PersistenceContract.ForecastEntry.APPARENT_TEMPERATURE, forecastModel.getApparentTemperature());
        cv.put(PersistenceContract.ForecastEntry.MIN_TEMPERATURE, forecastModel.getMinTemperature());
        cv.put(PersistenceContract.ForecastEntry.MAX_TEMPERATURE, forecastModel.getMaxTemperature());
        cv.put(PersistenceContract.ForecastEntry.HUMIDITY, forecastModel.getHumidity());
        cv.put(PersistenceContract.ForecastEntry.ICON, forecastModel.getIcon());
        cv.put(PersistenceContract.ForecastEntry.PRESSURE, forecastModel.getPressure());
        cv.put(PersistenceContract.ForecastEntry.OZONE, forecastModel.getOzone());
        cv.put(PersistenceContract.ForecastEntry.UV_INDEX, forecastModel.getUvIndex());
        cv.put(PersistenceContract.ForecastEntry.SUMMARY, forecastModel.getSummary());
        cv.put(PersistenceContract.ForecastEntry.WIND_SPEED, forecastModel.getWindSpeed());
        cv.put(PersistenceContract.ForecastEntry.VISIBILITY, forecastModel.getVisibility());
        cv.put(PersistenceContract.ForecastEntry.UNITS, forecastModel.getUnits());
        cv.put(PersistenceContract.ForecastEntry.UPDATED_AT, forecastModel.getUpdatedAt());
        cv.put(PersistenceContract.ForecastEntry.TIMEZONE, forecastModel.getTimezone());
        cv.put(PersistenceContract.ForecastEntry.LOCATION_ID, forecastModel.getLocationId());
        cv.put(PersistenceContract.ForecastEntry.CURRENT_TIME, forecastModel.getCurrentTime());
        cv.put(PersistenceContract.ForecastEntry.DAILY_FORECASTS, gson.toJson(forecastModel.getDailyForecastModels()));
    }

    class FetchForecastTask extends AsyncTask<Void, Integer, ForecastModel> {

        private LocationModel locationModel;
        private LoadForecastCallback loadForecastCallback;

        FetchForecastTask(LocationModel locationModel, LoadForecastCallback loadForecastCallback) {
            this.locationModel = locationModel;
            this.loadForecastCallback = loadForecastCallback;
        }

        @Override
        protected ForecastModel doInBackground(Void... voids) {
            ForecastModel forecastModel = null;
            SQLiteDatabase database = mDbHelper.getReadableDatabase();
            String whereArgs = PersistenceContract.ForecastEntry.LOCATION_ID + " = ? ";
            String[] whereArgsValues = new String[]{String.valueOf(locationModel.getId())};
            String[] projection = new String[]{
                    PersistenceContract.ForecastEntry.TEMPERATURE,
                    PersistenceContract.ForecastEntry.APPARENT_TEMPERATURE,
                    PersistenceContract.ForecastEntry.MIN_TEMPERATURE,
                    PersistenceContract.ForecastEntry.MAX_TEMPERATURE,
                    PersistenceContract.ForecastEntry.HUMIDITY,
                    PersistenceContract.ForecastEntry.ICON,
                    PersistenceContract.ForecastEntry.PRESSURE,
                    PersistenceContract.ForecastEntry.OZONE,
                    PersistenceContract.ForecastEntry.UV_INDEX,
                    PersistenceContract.ForecastEntry.SUMMARY,
                    PersistenceContract.ForecastEntry.WIND_SPEED,
                    PersistenceContract.ForecastEntry.VISIBILITY,
                    PersistenceContract.ForecastEntry.UNITS,
                    PersistenceContract.ForecastEntry.UPDATED_AT,
                    PersistenceContract.ForecastEntry.TIMEZONE,
                    PersistenceContract.ForecastEntry.LOCATION_ID,
                    PersistenceContract.ForecastEntry.CURRENT_TIME,
                    PersistenceContract.ForecastEntry.DAILY_FORECASTS
            };

            Cursor c = database.query(PersistenceContract.ForecastEntry.TABLE_NAME, projection, whereArgs, whereArgsValues, null, null, null);
            if (c != null) {
                c.moveToFirst();
                if (!c.isAfterLast()) {
                    String dailyForecastModelsJson = c.getString(c.getColumnIndex(PersistenceContract.ForecastEntry.DAILY_FORECASTS));
                    List<DailyForecastModel> dailyForecastModels = gson.fromJson(dailyForecastModelsJson,
                            new TypeToken<List<DailyForecastModel>>() {
                            }.getType());
                    forecastModel = new ForecastModelBuilder()
                            .setTemperature(c.getDouble(c.getColumnIndex(PersistenceContract.ForecastEntry.TEMPERATURE)))
                            .setApparentTemperature(c.getDouble(c.getColumnIndex(PersistenceContract.ForecastEntry.APPARENT_TEMPERATURE)))
                            .setMinTemperature(c.getDouble(c.getColumnIndex(PersistenceContract.ForecastEntry.MIN_TEMPERATURE)))
                            .setMaxTemperature(c.getDouble(c.getColumnIndex(PersistenceContract.ForecastEntry.MAX_TEMPERATURE)))
                            .setHumidity(c.getDouble(c.getColumnIndex(PersistenceContract.ForecastEntry.HUMIDITY)))
                            .setIcon(c.getString(c.getColumnIndex(PersistenceContract.ForecastEntry.ICON)))
                            .setPressure(c.getDouble(c.getColumnIndex(PersistenceContract.ForecastEntry.PRESSURE)))
                            .setOzone(c.getDouble(c.getColumnIndex(PersistenceContract.ForecastEntry.OZONE)))
                            .setUvIndex(c.getInt(c.getColumnIndex(PersistenceContract.ForecastEntry.UV_INDEX)))
                            .setSummary(c.getString(c.getColumnIndex(PersistenceContract.ForecastEntry.SUMMARY)))
                            .setWindSpeed(c.getDouble(c.getColumnIndex(PersistenceContract.ForecastEntry.WIND_SPEED)))
                            .setVisibility(c.getDouble(c.getColumnIndex(PersistenceContract.ForecastEntry.VISIBILITY)))
                            .setUnits(c.getString(c.getColumnIndex(PersistenceContract.ForecastEntry.UNITS)))
                            .setUpdatedAt(c.getLong(c.getColumnIndex(PersistenceContract.ForecastEntry.UPDATED_AT)))
                            .setTimezone(c.getString(c.getColumnIndex(PersistenceContract.ForecastEntry.TIMEZONE)))
                            .setLocationId(c.getInt(c.getColumnIndex(PersistenceContract.ForecastEntry.LOCATION_ID)))
                            .setCurrentTime(c.getInt(c.getColumnIndex(PersistenceContract.ForecastEntry.CURRENT_TIME)))
                            .setDailyForecastModels(dailyForecastModels)
                            .build();
                }
                c.close();
            }

            database.close();
            return forecastModel;
        }

        @Override
        protected void onPostExecute(ForecastModel forecastModel) {
            super.onPostExecute(forecastModel);
            if (forecastModel == null) {
                loadForecastCallback.onDataNotAvailable("Unable to retrieve Forecast");
            } else {
                loadForecastCallback.onForecastLoaded(forecastModel);
            }
        }
    }
}
