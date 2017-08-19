package io.stormcast.app.stormcast.data.locations.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import io.stormcast.app.stormcast.common.Location;
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
}
