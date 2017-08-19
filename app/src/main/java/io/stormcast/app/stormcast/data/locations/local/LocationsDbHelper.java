package io.stormcast.app.stormcast.data.locations.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sudharti on 8/18/17.
 */

public class LocationsDbHelper extends SQLiteOpenHelper {

    public final static int DATABASE_VERSION = 1;
    public final static String DATABASE_NAME = "Stormcast.db";

    protected final static String TABLE_NAME = "locations";

    protected final static String ID = "id";
    protected final static String NAME = "name";
    protected final static String LATITUDE = "latitude";
    protected final static String LONGITUDE = "longitude";
    protected final static String BG_COLOR = "bg_color";
    protected final static String TEXT_COLOR = "text_color";
    protected final static String UNIT = "unit";

    private final static String CREATE_TABLE_SQL = " CREATE TABLE " + TABLE_NAME + " ( " +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NAME + " TEXT, " + LATITUDE + " REAL, " + LONGITUDE + " REAL, " + BG_COLOR + " VARCHAR, " +
            TEXT_COLOR + " VARCHAR, " + UNIT + " INTEGER)";

    public LocationsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
