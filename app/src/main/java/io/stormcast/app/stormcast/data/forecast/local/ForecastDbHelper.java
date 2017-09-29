package io.stormcast.app.stormcast.data.forecast.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import io.stormcast.app.stormcast.data.PersistenceContract;

/**
 * Created by sudharti on 9/24/17.
 */

public class ForecastDbHelper extends SQLiteOpenHelper {

    private final static String CREATE_TABLE_SQL = " CREATE TABLE " + PersistenceContract.ForecastEntry.TABLE_NAME +
            " ( " +
            PersistenceContract.ForecastEntry.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            PersistenceContract.ForecastEntry.SUMMARY + " TEXT, " + PersistenceContract.ForecastEntry.ICON + " VARCHAR, " +
            PersistenceContract.ForecastEntry.TIMEZONE + " VARCHAR, " + PersistenceContract.ForecastEntry.UNITS + " VARCHAR, " +
            PersistenceContract.ForecastEntry.TEMPERATURE + " REAL, " + PersistenceContract.ForecastEntry.APPARENT_TEMPERATURE + " REAL, " +
            PersistenceContract.ForecastEntry.HUMIDITY + " REAL, " + PersistenceContract.ForecastEntry.WIND_SPEED + " REAL, " +
            PersistenceContract.ForecastEntry.PRESSURE + " REAL, " + PersistenceContract.ForecastEntry.VISIBILITY + " REAL, " +
            PersistenceContract.ForecastEntry.UPDATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP, " + PersistenceContract.ForecastEntry.LOCATION_ID +
            " INTEGER NOT NULL FOREIGN KEY ( " + PersistenceContract.ForecastEntry.LOCATION_ID + " ) " +
            " REFERENCES " + PersistenceContract.LocationEntry.TABLE_NAME + "(" + PersistenceContract.LocationEntry.ID + ")" +
            " ) ";

    public ForecastDbHelper(Context context) {
        super(context, PersistenceContract.ForecastEntry.TABLE_NAME, null, PersistenceContract.DATABASE_VERSION, null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
