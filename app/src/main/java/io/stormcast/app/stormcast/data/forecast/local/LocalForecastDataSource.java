package io.stormcast.app.stormcast.data.forecast.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import io.stormcast.app.stormcast.common.models.DailyForecastModel;
import io.stormcast.app.stormcast.common.models.DailyForecastModelBuilder;
import io.stormcast.app.stormcast.common.models.ForecastModel;
import io.stormcast.app.stormcast.common.models.ForecastModelBuilder;
import io.stormcast.app.stormcast.common.models.LocationModel;
import io.stormcast.app.stormcast.data.DbHelper;
import io.stormcast.app.stormcast.data.PersistenceContract;
import io.stormcast.app.stormcast.data.forecast.ForecastDataSource;

/**
 * Created by sudharti on 8/22/17.
 */

public class LocalForecastDataSource implements ForecastDataSource {

    private static LocalForecastDataSource mLocalForecastDataSource;
    private DbHelper mDbHelper;

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
        new FetchForecastTask(locationModel.getId(), loadForecastCallback).execute();
    }

    public void saveForecast(final ForecastModel forecastModel, int locationId) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        populate(cv, forecastModel);
        String where = PersistenceContract.ForecastEntry.LOCATION_ID + " = ? ";
        String[] whereArgs = new String[]{String.valueOf(locationId)};
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

    public void saveDailyForecasts(final List<DailyForecastModel> dailyForecastModels, int locationId) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        String where = PersistenceContract.ForecastEntry.LOCATION_ID + " = ? ";
        String[] whereArgs = new String[]{String.valueOf(locationId)};
        database.delete(PersistenceContract.DailyForecastEntry.TABLE_NAME, where, whereArgs);
        try {
            for (DailyForecastModel dailyForecastModel : dailyForecastModels) {
                ContentValues cv = new ContentValues();
                populate(cv, dailyForecastModel);
                database.insert(PersistenceContract.DailyForecastEntry.TABLE_NAME, null, cv);
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
        cv.put(PersistenceContract.ForecastEntry.SUMMARY, forecastModel.getSummary());
        cv.put(PersistenceContract.ForecastEntry.WIND_SPEED, forecastModel.getWindSpeed());
        cv.put(PersistenceContract.ForecastEntry.VISIBILITY, forecastModel.getVisibility());
        cv.put(PersistenceContract.ForecastEntry.UNITS, forecastModel.getUnits());
        cv.put(PersistenceContract.ForecastEntry.UPDATED_AT, forecastModel.getUpdatedAt());
        cv.put(PersistenceContract.ForecastEntry.TIMEZONE, forecastModel.getTimezone());
        cv.put(PersistenceContract.ForecastEntry.LOCATION_ID, forecastModel.getLocationId());
        cv.put(PersistenceContract.ForecastEntry.CURRENT_TIME, forecastModel.getCurrentTime());
    }

    private void populate(ContentValues cv, DailyForecastModel dailyForecastModel) {
        cv.put(PersistenceContract.DailyForecastEntry.ICON, dailyForecastModel.getIcon());
        cv.put(PersistenceContract.DailyForecastEntry.TEMPERATURE, dailyForecastModel.getTemperature());
        cv.put(PersistenceContract.DailyForecastEntry.TIME, dailyForecastModel.getTime());
        cv.put(PersistenceContract.DailyForecastEntry.UNITS, dailyForecastModel.getUnits());
        cv.put(PersistenceContract.DailyForecastEntry.UPDATED_AT, dailyForecastModel.getUpdatedAt());
        cv.put(PersistenceContract.DailyForecastEntry.LOCATION_ID, dailyForecastModel.getLocationId());
    }

    class FetchForecastTask extends AsyncTask<Void, Integer, Void> {

        private int locationId;
        private ForecastModel forecastModel;
        private List<DailyForecastModel> dailyForecastModels;
        private LoadForecastCallback loadForecastCallback;

        FetchForecastTask(int locationId, LoadForecastCallback loadForecastCallback) {
            this.dailyForecastModels = new ArrayList<>();
            this.locationId = locationId;
            this.loadForecastCallback = loadForecastCallback;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            SQLiteDatabase database = mDbHelper.getReadableDatabase();
            String whereArgs = PersistenceContract.ForecastEntry.LOCATION_ID + " = ? ";
            String[] whereArgsValues = new String[]{String.valueOf(locationId)};
            String[] projection = new String[]{
                    PersistenceContract.ForecastEntry.TEMPERATURE,
                    PersistenceContract.ForecastEntry.APPARENT_TEMPERATURE,
                    PersistenceContract.ForecastEntry.MIN_TEMPERATURE,
                    PersistenceContract.ForecastEntry.MAX_TEMPERATURE,
                    PersistenceContract.ForecastEntry.HUMIDITY,
                    PersistenceContract.ForecastEntry.ICON,
                    PersistenceContract.ForecastEntry.PRESSURE,
                    PersistenceContract.ForecastEntry.SUMMARY,
                    PersistenceContract.ForecastEntry.WIND_SPEED,
                    PersistenceContract.ForecastEntry.VISIBILITY,
                    PersistenceContract.ForecastEntry.UNITS,
                    PersistenceContract.ForecastEntry.UPDATED_AT,
                    PersistenceContract.ForecastEntry.TIMEZONE,
                    PersistenceContract.ForecastEntry.LOCATION_ID,
                    PersistenceContract.ForecastEntry.CURRENT_TIME
            };

            Cursor c = database.query(PersistenceContract.ForecastEntry.TABLE_NAME, projection, whereArgs, whereArgsValues, null, null, null);
            if (c != null) {
                c.moveToFirst();
                if (!c.isAfterLast()) {
                    forecastModel = new ForecastModelBuilder()
                            .setTemperature(c.getDouble(c.getColumnIndex(PersistenceContract.ForecastEntry.TEMPERATURE)))
                            .setApparentTemperature(c.getDouble(c.getColumnIndex(PersistenceContract.ForecastEntry.APPARENT_TEMPERATURE)))
                            .setMinTemperature(c.getDouble(c.getColumnIndex(PersistenceContract.ForecastEntry.MIN_TEMPERATURE)))
                            .setMaxTemperature(c.getDouble(c.getColumnIndex(PersistenceContract.ForecastEntry.MAX_TEMPERATURE)))
                            .setHumidity(c.getDouble(c.getColumnIndex(PersistenceContract.ForecastEntry.HUMIDITY)))
                            .setIcon(c.getString(c.getColumnIndex(PersistenceContract.ForecastEntry.ICON)))
                            .setPressure(c.getDouble(c.getColumnIndex(PersistenceContract.ForecastEntry.PRESSURE)))
                            .setSummary(c.getString(c.getColumnIndex(PersistenceContract.ForecastEntry.SUMMARY)))
                            .setWindSpeed(c.getDouble(c.getColumnIndex(PersistenceContract.ForecastEntry.WIND_SPEED)))
                            .setVisibility(c.getDouble(c.getColumnIndex(PersistenceContract.ForecastEntry.VISIBILITY)))
                            .setUnits(c.getString(c.getColumnIndex(PersistenceContract.ForecastEntry.UNITS)))
                            .setUpdatedAt(c.getLong(c.getColumnIndex(PersistenceContract.ForecastEntry.UPDATED_AT)))
                            .setTimezone(c.getString(c.getColumnIndex(PersistenceContract.ForecastEntry.TIMEZONE)))
                            .setLocationId(c.getInt(c.getColumnIndex(PersistenceContract.ForecastEntry.LOCATION_ID)))
                            .setCurrentTime(c.getInt(c.getColumnIndex(PersistenceContract.ForecastEntry.CURRENT_TIME)))
                            .build();
                }
                c.close();
            }

            projection = new String[]{};
            c = database.query(PersistenceContract.DailyForecastEntry.TABLE_NAME, projection, whereArgs, whereArgsValues, null, null, null);
            if (c != null) {
                c.moveToFirst();
                while (!c.isAfterLast()) {
                    DailyForecastModel dailyForecastModel = new DailyForecastModelBuilder()
                            .setIcon(c.getString(c.getColumnIndex(PersistenceContract.DailyForecastEntry.ICON)))
                            .setTemperature(c.getDouble(c.getColumnIndex(PersistenceContract.DailyForecastEntry.TEMPERATURE)))
                            .setLocationId(c.getInt(c.getColumnIndex(PersistenceContract.DailyForecastEntry.LOCATION_ID)))
                            .setTime(c.getInt(c.getColumnIndex(PersistenceContract.DailyForecastEntry.TIME)))
                            .setUpdatedAt(c.getInt(c.getColumnIndex(PersistenceContract.DailyForecastEntry.UPDATED_AT)))
                            .setUnits(c.getString(c.getColumnIndex(PersistenceContract.DailyForecastEntry.UNITS)))
                            .build();
                    dailyForecastModels.add(dailyForecastModel);
                    c.moveToNext();
                }
                c.close();
            }

            database.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (forecastModel == null) {
                loadForecastCallback.onDataNotAvailable("Unable to retrieve Forecast");
            } else {
                loadForecastCallback.onForecastLoaded(forecastModel, dailyForecastModels);
            }
        }
    }
}
