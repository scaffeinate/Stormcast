package io.stormcast.app.stormcast.data.locations.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import com.google.android.gms.maps.model.LatLng;

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
            contentValues.put(LocationsDbHelper.LAT_LNG, new StringBuilder()
                    .append(location.getLatitude())
                    .append(",")
                    .append(location.getLongitude())
                    .toString());
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
                LocationsDbHelper.LAT_LNG,
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
                locationBuilder.setBackgroundColor(cursor.getInt(cursor.getColumnIndexOrThrow(LocationsDbHelper.BG_COLOR)));
                locationBuilder.setTextColor(cursor.getInt(cursor.getColumnIndexOrThrow(LocationsDbHelper.TEXT_COLOR)));
                locationBuilder.setUnit(cursor.getInt(cursor.getColumnIndexOrThrow(LocationsDbHelper.UNIT)));

                String[] latLng = cursor.getString(cursor.getColumnIndexOrThrow(LocationsDbHelper.LAT_LNG)).split(",");
                locationBuilder.setLatLng(new LatLng(Double.parseDouble(latLng[0]), Double.parseDouble(latLng[1])));

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
