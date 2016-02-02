package kz.aibol.mobisalestest.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import kz.aibol.mobisalestest.data.DataContract.*;

/**
 * Created by Amirzhan on 2/2/16.
 */
public class DataDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;

    static final String DATABASE_NAME = "mobisales.db";

    public DataDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_FILETIMES_TABLE = "CREATE TABLE " + FiletimesEntry.TABLE_NAME + " (" +
                FiletimesEntry._ID + " INTEGER PRIMARY KEY," +
                FiletimesEntry.COLUMN_FILENAME + " TEXT UNIQUE NOT NULL, " +
                FiletimesEntry.COLUMN_FILENAMEXML + " TEXT NOT NULL, " +
                FiletimesEntry.COLUMN_CDATE + " DATE NOT NULL, " +
                FiletimesEntry.COLUMN_CTIME + " DATETIME NOT NULL " +
                ");";

        final String SQL_CREATE_ITEMS_TABLE = "CREATE TABLE " + Items.TABLE_NAME + " (" +
                Items._ID + " INTEGER PRIMARY KEY," +
                Items.COLUMN_CODE + " TEXT NOT NULL, " +
                Items.COLUMN_NAME1 + " TEXT NOT NULL, " +
                Items.COLUMN_NAME2 + " TEXT NOT NULL, " +
                Items.COLUMN_SPECODE + " TEXT NOT NULL," +
                Items.COLUMN_STGRPCODE + " TEXT NOT NULL, " +
                Items.COLUMN_CDATE + " DATE NOT NULL, " +
                Items.COLUMN_CTIME + " DATETIME NOT NULL" +
                ");";


//
//                // Set up the location column as a foreign key to location table.
//                " FOREIGN KEY (" + WeatherEntry.COLUMN_LOC_KEY + ") REFERENCES " +
//                LocationEntry.TABLE_NAME + " (" + LocationEntry._ID + "), " +
//
//                // To assure the application have just one weather entry per day
//                // per location, it's created a UNIQUE constraint with REPLACE strategy
//                " UNIQUE (" + WeatherEntry.COLUMN_DATE + ", " +
//                WeatherEntry.COLUMN_LOC_KEY + ") ON CONFLICT REPLACE);";

        db.execSQL(SQL_CREATE_FILETIMES_TABLE);
        db.execSQL(SQL_CREATE_ITEMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FiletimesEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Items.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}


