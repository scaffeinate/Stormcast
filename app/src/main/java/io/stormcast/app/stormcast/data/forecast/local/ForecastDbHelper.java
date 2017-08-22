package io.stormcast.app.stormcast.data.forecast.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import io.stormcast.app.stormcast.data.PersistenceContract;

/**
 * Created by sudharti on 8/22/17.
 */

public class ForecastDbHelper extends SQLiteOpenHelper {

    protected final static String TABLE_NAME = "forecast";

    public ForecastDbHelper(Context context) {
        super(context, PersistenceContract.DATABASE_NAME, null, PersistenceContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
