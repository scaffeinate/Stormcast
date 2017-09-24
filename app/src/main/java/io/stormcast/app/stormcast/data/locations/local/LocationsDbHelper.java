package io.stormcast.app.stormcast.data.locations.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import io.stormcast.app.stormcast.data.PersistenceContract;

/**
 * Created by sudharti on 9/24/17.
 */

public class LocationsDbHelper extends SQLiteOpenHelper {

    private final static String CREATE_TABLE_SQL = " CREATE TABLE " + PersistenceContract.LocationEntry.TABLE_NAME + " ( " +
            PersistenceContract.LocationEntry.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PersistenceContract.LocationEntry.POSITION + " INTEGER, " +
            PersistenceContract.LocationEntry.NAME + " TEXT, " + PersistenceContract.LocationEntry.LATITUDE + " REAL, " +
            PersistenceContract.LocationEntry.LONGITUDE + " REAL, " + PersistenceContract.LocationEntry.BG_COLOR + " VARCHAR, " +
            PersistenceContract.LocationEntry.TEXT_COLOR + " VARCHAR, " + PersistenceContract.LocationEntry.UNIT + " INTEGER)";

    public LocationsDbHelper(Context context) {
        super(context, PersistenceContract.DATABASE_NAME, null, PersistenceContract.DATABASE_VERSION, null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
