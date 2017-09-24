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
            ContentValues cv = new ContentValues();
            cv.put(PersistenceContract.LocationEntry.NAME, locationModel.getName());
            cv.put(PersistenceContract.LocationEntry.LATITUDE, locationModel.getLatitude());
            cv.put(PersistenceContract.LocationEntry.LONGITUDE, locationModel.getLongitude());
            cv.put(PersistenceContract.LocationEntry.BG_COLOR, locationModel.getBackgroundColor());
            cv.put(PersistenceContract.LocationEntry.TEXT_COLOR, locationModel.getTextColor());
            cv.put(PersistenceContract.LocationEntry.UNIT, locationModel.getUnit());
            cv.put(PersistenceContract.LocationEntry.POSITION, locationModel.getPosition());

            database.insertOrThrow(PersistenceContract.LocationEntry.TABLE_NAME, null, cv);
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
                PersistenceContract.LocationEntry.POSITION,
                PersistenceContract.LocationEntry.UNIT
        };

        Cursor c = database.query(PersistenceContract.LocationEntry.TABLE_NAME, projection, null, null, null, null, PersistenceContract.LocationEntry.POSITION);
        if (c != null) {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                LocationModel locationModel = new LocationModelBuilder()
                        .setId(c.getInt(c.getColumnIndex(PersistenceContract.LocationEntry.ID)))
                        .setName(c.getString(c.getColumnIndex(PersistenceContract.LocationEntry.NAME)))
                        .setBackgroundColor(c.getString(c.getColumnIndex(PersistenceContract.LocationEntry.BG_COLOR)))
                        .setTextColor(c.getString(c.getColumnIndex(PersistenceContract.LocationEntry.TEXT_COLOR)))
                        .setUnit(c.getInt(c.getColumnIndex(PersistenceContract.LocationEntry.UNIT)))
                        .setLatitude(c.getDouble(c.getColumnIndex(PersistenceContract.LocationEntry.LATITUDE)))
                        .setLongitude(c.getDouble(c.getColumnIndex(PersistenceContract.LocationEntry.LONGITUDE)))
                        .setPosition(c.getInt(c.getColumnIndex(PersistenceContract.LocationEntry.POSITION)))
                        .build();

                locationList.add(locationModel);
                c.moveToNext();
            }
        }

        if (c != null) c.close();
        database.close();

        if (locationList.isEmpty()) {
            getLocationsCallback.onDataNotAvailable();
        } else {
            getLocationsCallback.onLocationsLoaded(locationList);
        }
    }
}
