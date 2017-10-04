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
import io.stormcast.app.stormcast.data.DbHelper;
import io.stormcast.app.stormcast.data.PersistenceContract;
import io.stormcast.app.stormcast.data.locations.LocationsDataSource;

/**
 * Created by sudharti on 8/18/17.
 */

public class LocalLocationsDataSource implements LocationsDataSource {

    private static LocalLocationsDataSource sLocalLocationsDataSource;
    private DbHelper mDbHelper;

    private LocalLocationsDataSource(Context context) {
        mDbHelper = DbHelper.getInstance(context);
    }

    public static LocalLocationsDataSource getInstance(Context context) {
        if (sLocalLocationsDataSource == null) {
            sLocalLocationsDataSource = new LocalLocationsDataSource(context);
        }

        return sLocalLocationsDataSource;
    }

    @Override
    public void saveLocation(final LocationModel locationModel, final SaveLocationCallback saveLocationCallback) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            locationModel.setPosition(getInsertPosition(database));
            populateContentValues(cv, locationModel);

            database.insertOrThrow(PersistenceContract.LocationEntry.TABLE_NAME, null, cv);
            saveLocationCallback.onLocationSaved();
        } catch (SQLiteConstraintException e) {
            saveLocationCallback.onLocationSaveFailed(e.getMessage());
        } finally {
            database.close();
        }
    }

    @Override
    public void getLocations(GetLocationsCallback getLocationsCallback) {
        List<LocationModel> locationList = new ArrayList<>();
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        String[] projection = new String[]{
                PersistenceContract.LocationEntry._ID,
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
                        .setId(c.getInt(c.getColumnIndex(PersistenceContract.LocationEntry._ID)))
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

    @Override
    public void deleteLocation(LocationModel locationModel, DeleteLocationCallback deleteLocationCallback) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int numRows = database.delete(PersistenceContract.LocationEntry.TABLE_NAME, PersistenceContract.LocationEntry._ID + " = ? ",
                new String[]{String.valueOf(locationModel.getId())});
        database.close();
        if (numRows == 0) {
            deleteLocationCallback.onLocationDeleteFailed("Failed to delete location.");
        } else {
            deleteLocationCallback.onLocationDeleted();
        }
    }

    @Override
    public void reorder(List<LocationModel> locationModels) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        for (LocationModel locationModel : locationModels) {
            ContentValues cv = new ContentValues();
            populateContentValues(cv, locationModel);
            database.update(PersistenceContract.LocationEntry.TABLE_NAME, cv, PersistenceContract.LocationEntry._ID + " = ? ",
                    new String[]{String.valueOf(locationModel.getId())});
        }
    }

    private void populateContentValues(ContentValues cv, LocationModel locationModel) {
        cv.put(PersistenceContract.LocationEntry.NAME, locationModel.getName());
        cv.put(PersistenceContract.LocationEntry.LATITUDE, locationModel.getLatitude());
        cv.put(PersistenceContract.LocationEntry.LONGITUDE, locationModel.getLongitude());
        cv.put(PersistenceContract.LocationEntry.BG_COLOR, locationModel.getBackgroundColor());
        cv.put(PersistenceContract.LocationEntry.TEXT_COLOR, locationModel.getTextColor());
        cv.put(PersistenceContract.LocationEntry.UNIT, locationModel.getUnit());
        cv.put(PersistenceContract.LocationEntry.POSITION, locationModel.getPosition());
    }

    private int getInsertPosition(SQLiteDatabase database) {
        String query = "SELECT MAX ( " + PersistenceContract.LocationEntry.POSITION + ") FROM "
                + PersistenceContract.LocationEntry.TABLE_NAME;
        Cursor c = database.rawQuery(query, new String[]{});
        int position = 0;
        if (c != null) {
            if (c.moveToFirst()) {
                position = c.getInt(0);
            }
            c.close();
        }
        return position + 1;
    }
}
