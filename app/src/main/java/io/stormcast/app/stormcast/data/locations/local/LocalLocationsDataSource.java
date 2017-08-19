package io.stormcast.app.stormcast.data.locations.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import io.stormcast.app.stormcast.common.Location;
import io.stormcast.app.stormcast.common.LocationBuilder;
import io.stormcast.app.stormcast.data.locations.LocationsDataSource;

/**
 * Created by sudharti on 8/18/17.
 */

public class LocalLocationsDataSource implements LocationsDataSource {

    private static LocalLocationsDataSource mLocalLocationsDataSource;
    private LocationsDbHelper mLocationsDbHelper;

    public static LocalLocationsDataSource getInstance(Context context) {
        if (mLocalLocationsDataSource == null) {
            mLocalLocationsDataSource = new LocalLocationsDataSource(context);
        }

        return mLocalLocationsDataSource;
    }

    private LocalLocationsDataSource(Context context) {
        mLocationsDbHelper = new LocationsDbHelper(context);
    }

    @Override
    public void saveLocation(Location location, SaveLocationCallback saveLocationCallback) {
        try {
            SQLiteDatabase database = mLocationsDbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(LocationsDbHelper.NAME, location.getName());
            contentValues.put(LocationsDbHelper.LATITUDE, location.getLatitude());
            contentValues.put(LocationsDbHelper.LONGITUDE, location.getLongitude());
            contentValues.put(LocationsDbHelper.BG_COLOR, location.getBackgroundColor());
            contentValues.put(LocationsDbHelper.TEXT_COLOR, location.getTextColor());
            contentValues.put(LocationsDbHelper.UNIT, location.getUnit());

            database.insertOrThrow(LocationsDbHelper.TABLE_NAME, null, contentValues);
            database.close();
            saveLocationCallback.onLocationSaved();
        } catch (SQLiteConstraintException e) {
            saveLocationCallback.onLocationSaveFailed(e.getMessage());
        }
    }

    @Override
    public void getLocations(GetLocationsCallback getLocationsCallback) {
        List<Location> locationList = new ArrayList<>();
        SQLiteDatabase database = mLocationsDbHelper.getReadableDatabase();
        String[] projection = new String[]{
                LocationsDbHelper.ID,
                LocationsDbHelper.NAME,
                LocationsDbHelper.LATITUDE,
                LocationsDbHelper.LONGITUDE,
                LocationsDbHelper.BG_COLOR,
                LocationsDbHelper.TEXT_COLOR,
                LocationsDbHelper.UNIT
        };

        Cursor cursor = database.query(LocationsDbHelper.TABLE_NAME, projection, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                LocationBuilder locationBuilder = new LocationBuilder();
                locationBuilder.setId(cursor.getInt(cursor.getColumnIndexOrThrow(LocationsDbHelper.ID)));
                locationBuilder.setName(cursor.getString(cursor.getColumnIndexOrThrow(LocationsDbHelper.NAME)));
                locationBuilder.setBackgroundColor(cursor.getString(cursor.getColumnIndexOrThrow(LocationsDbHelper.BG_COLOR)));
                locationBuilder.setTextColor(cursor.getString(cursor.getColumnIndexOrThrow(LocationsDbHelper.TEXT_COLOR)));
                locationBuilder.setUnit(cursor.getInt(cursor.getColumnIndexOrThrow(LocationsDbHelper.UNIT)));
                locationBuilder.setLatitude(cursor.getDouble(cursor.getColumnIndexOrThrow(LocationsDbHelper.LATITUDE)));
                locationBuilder.setLongitude(cursor.getDouble(cursor.getColumnIndexOrThrow(LocationsDbHelper.LONGITUDE)));

                locationList.add(locationBuilder.build());
                cursor.moveToNext();
            }
        }

        if (cursor != null) cursor.close();
        database.close();

        if (locationList.size() == 0) {
            getLocationsCallback.onDataNotAvailable();
        } else {
            getLocationsCallback.onLocationsLoaded(locationList);
        }
    }
}
