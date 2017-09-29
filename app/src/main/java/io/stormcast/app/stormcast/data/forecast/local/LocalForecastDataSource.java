package io.stormcast.app.stormcast.data.forecast.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import io.stormcast.app.stormcast.common.models.ForecastModel;
import io.stormcast.app.stormcast.common.models.ForecastModelBuilder;
import io.stormcast.app.stormcast.common.models.LocationModel;
import io.stormcast.app.stormcast.data.PersistenceContract;
import io.stormcast.app.stormcast.data.forecast.ForecastDataSource;

/**
 * Created by sudharti on 8/22/17.
 */

public class LocalForecastDataSource implements ForecastDataSource {

    private static LocalForecastDataSource mLocalForecastDataSource;
    private ForecastDbHelper mForecastDbHelper;

    private LocalForecastDataSource(Context context) {
        mForecastDbHelper = new ForecastDbHelper(context);
    }

    public static LocalForecastDataSource getInstance(Context context) {
        if (mLocalForecastDataSource == null) {
            mLocalForecastDataSource = new LocalForecastDataSource(context);
        }
        return mLocalForecastDataSource;
    }

    @Override
    public void loadForecast(final LocationModel locationModel, boolean isManualRefresh, LoadForecastCallback loadForecastCallback) {
        ForecastModel forecastModel = null;
        SQLiteDatabase database = mForecastDbHelper.getReadableDatabase();
        String[] projection = new String[]{
                PersistenceContract.ForecastEntry.ID,
                PersistenceContract.ForecastEntry.TEMPERATURE,
                PersistenceContract.ForecastEntry.APPARENT_TEMPERATURE,
                PersistenceContract.ForecastEntry.HUMIDITY,
                PersistenceContract.ForecastEntry.ICON,
                PersistenceContract.ForecastEntry.PRESSURE,
                PersistenceContract.ForecastEntry.SUMMARY,
                PersistenceContract.ForecastEntry.WIND_SPEED,
                PersistenceContract.ForecastEntry.VISIBILITY,
                PersistenceContract.ForecastEntry.UNITS,
                PersistenceContract.ForecastEntry.UPDATED_AT,
                PersistenceContract.ForecastEntry.TIMEZONE
        };

        Cursor c = database.query(PersistenceContract.ForecastEntry.TABLE_NAME, projection, PersistenceContract.ForecastEntry.LOCATION_ID + " = ?", new String[]{String.valueOf(locationModel.getId())}, null, null, null);
        if (c != null) {
            c.moveToFirst();
            forecastModel = new ForecastModelBuilder()
                    .setTemperature(c.getDouble(c.getColumnIndex(PersistenceContract.ForecastEntry.TEMPERATURE)))
                    .setApparentTemperature(c.getDouble(c.getColumnIndex(PersistenceContract.ForecastEntry.APPARENT_TEMPERATURE)))
                    .setHumidity(c.getDouble(c.getColumnIndex(PersistenceContract.ForecastEntry.HUMIDITY)))
                    .setIcon(c.getString(c.getColumnIndex(PersistenceContract.ForecastEntry.ICON)))
                    .setPressure(c.getDouble(c.getColumnIndex(PersistenceContract.ForecastEntry.PRESSURE)))
                    .setSummary(c.getString(c.getColumnIndex(PersistenceContract.ForecastEntry.SUMMARY)))
                    .setWindSpeed(c.getDouble(c.getColumnIndex(PersistenceContract.ForecastEntry.WIND_SPEED)))
                    .setVisibility(c.getDouble(c.getColumnIndex(PersistenceContract.ForecastEntry.VISIBILITY)))
                    .setUnits(c.getString(c.getColumnIndex(PersistenceContract.ForecastEntry.UNITS)))
                    .setUpdatedAt(c.getString(c.getColumnIndex(PersistenceContract.ForecastEntry.UPDATED_AT)))
                    .setTimezone(c.getString(c.getColumnIndex(PersistenceContract.ForecastEntry.TIMEZONE)))
                    .build();
            c.close();
        }
        database.close();

        if (forecastModel == null) {
            loadForecastCallback.onDataNotAvailable("Unable to retrieve Forecast");
        } else {
            loadForecastCallback.onForecastLoaded(forecastModel);
        }
    }

    public void saveForecast(final ForecastModel forecastModel) {
        SQLiteDatabase database = mForecastDbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        populate(cv, forecastModel);
        try {
            //Insert or Update here
        } catch (SQLiteException e) {
            //Log e.getMessage();
        } finally {
            database.close();
        }
    }

    private void populate(ContentValues cv, ForecastModel forecastModel) {
        cv.put(PersistenceContract.ForecastEntry.TEMPERATURE, forecastModel.getTemperature());
        cv.put(PersistenceContract.ForecastEntry.APPARENT_TEMPERATURE, forecastModel.getApparentTemperature());
        cv.put(PersistenceContract.ForecastEntry.HUMIDITY, forecastModel.getHumidity());
        cv.put(PersistenceContract.ForecastEntry.ICON, forecastModel.getIcon());
        cv.put(PersistenceContract.ForecastEntry.PRESSURE, forecastModel.getPressure());
        cv.put(PersistenceContract.ForecastEntry.SUMMARY, forecastModel.getSummary());
        cv.put(PersistenceContract.ForecastEntry.WIND_SPEED, forecastModel.getWindSpeed());
        cv.put(PersistenceContract.ForecastEntry.VISIBILITY, forecastModel.getVisibility());
        cv.put(PersistenceContract.ForecastEntry.UNITS, forecastModel.getUnits());
        cv.put(PersistenceContract.ForecastEntry.UPDATED_AT, forecastModel.getUpdatedAt());
        cv.put(PersistenceContract.ForecastEntry.TIMEZONE, forecastModel.getTimezone());
    }
}
