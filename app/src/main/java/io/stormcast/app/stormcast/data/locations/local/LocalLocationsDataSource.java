package io.stormcast.app.stormcast.data.locations.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import io.stormcast.app.stormcast.common.models.LocationModel;
import io.stormcast.app.stormcast.common.models.LocationModelBuilder;
import io.stormcast.app.stormcast.data.PersistenceContract;
import io.stormcast.app.stormcast.data.locations.LocationsDataSource;

/**
 * Created by sudharti on 8/18/17.
 */

public class LocalLocationsDataSource implements LocationsDataSource {

    private static LocalLocationsDataSource mLocalLocationsDataSource;
    private LocationsDbHelper mLocationsDbHelper;

    private LocalLocationsDataSource(Context context) {
        mLocationsDbHelper = new LocationsDbHelper(context);
    }

    public static LocalLocationsDataSource getInstance(Context context) {
        if (mLocalLocationsDataSource == null) {
            mLocalLocationsDataSource = new LocalLocationsDataSource(context);
        }

        return mLocalLocationsDataSource;
    }

    @Override
    public void saveLocation(final LocationModel locationModel, final SaveLocationCallback saveLocationCallback) {
        try {
            SQLiteDatabase database = mLocationsDbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(PersistenceContract.LocationEntry.NAME, locationModel.getName());
            contentValues.put(PersistenceContract.LocationEntry.LATITUDE, locationModel.getLatitude());
            contentValues.put(PersistenceContract.LocationEntry.LONGITUDE, locationModel.getLongitude());
            contentValues.put(PersistenceContract.LocationEntry.BG_COLOR, locationModel.getBackgroundColor());
            contentValues.put(PersistenceContract.LocationEntry.TEXT_COLOR, locationModel.getTextColor());
            contentValues.put(PersistenceContract.LocationEntry.UNIT, locationModel.getUnit());

            database.insertOrThrow(PersistenceContract.LocationEntry.TABLE_NAME, null, contentValues);
            database.close();
            saveLocationCallback.onLocationSaved();
        } catch (SQLiteConstraintException e) {
            saveLocationCallback.onLocationSaveFailed(e.getMessage());
        }
    }

    @Override
    public void getLocations(GetLocationsCallback getLocationsCallback) {
        List<LocationModel> locationList = new ArrayList<>();
        SQLiteDatabase database = mLocationsDbHelper.getReadableDatabase();
        String[] projection = new String[]{
                PersistenceContract.LocationEntry.ID,
                PersistenceContract.LocationEntry.NAME,
                PersistenceContract.LocationEntry.LATITUDE,
                PersistenceContract.LocationEntry.LONGITUDE,
                PersistenceContract.LocationEntry.BG_COLOR,
                PersistenceContract.LocationEntry.TEXT_COLOR,
                PersistenceContract.LocationEntry.UNIT
        };

        Cursor cursor = database.query(PersistenceContract.LocationEntry.TABLE_NAME, projection, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                LocationModelBuilder locationBuilder = new LocationModelBuilder();
                locationBuilder.setId(cursor.getInt(cursor.getColumnIndexOrThrow(PersistenceContract.LocationEntry.ID)));
                locationBuilder.setName(cursor.getString(cursor.getColumnIndexOrThrow(PersistenceContract.LocationEntry.NAME)));
                locationBuilder.setBackgroundColor(cursor.getString(cursor.getColumnIndexOrThrow(PersistenceContract.LocationEntry.BG_COLOR)));
                locationBuilder.setTextColor(cursor.getString(cursor.getColumnIndexOrThrow(PersistenceContract.LocationEntry.TEXT_COLOR)));
                locationBuilder.setUnit(cursor.getInt(cursor.getColumnIndexOrThrow(PersistenceContract.LocationEntry.UNIT)));
                locationBuilder.setLatitude(cursor.getDouble(cursor.getColumnIndexOrThrow(PersistenceContract.LocationEntry.LATITUDE)));
                locationBuilder.setLongitude(cursor.getDouble(cursor.getColumnIndexOrThrow(PersistenceContract.LocationEntry.LONGITUDE)));

                locationList.add(locationBuilder.build());
                cursor.moveToNext();
            }
        }

        if (cursor != null) {
            cursor.close();
        }
        database.close();

        if (locationList.isEmpty()) {
            getLocationsCallback.onDataNotAvailable();
        } else {
            getLocationsCallback.onLocationsLoaded(locationList);
        }
    }
}
