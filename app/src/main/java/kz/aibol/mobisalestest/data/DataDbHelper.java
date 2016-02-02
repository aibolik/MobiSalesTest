package kz.aibol.mobisalestest.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import kz.aibol.mobisalestest.data.DataContract.*;

/**
 * Created by aibol on 2/2/16.
 */
public class DataDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;

    static final String DATABASE_NAME = "mobisales.db";

    public DataDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_UNITS_TABLE = "CREATE TABLE " + UnitsEntry.TABLE_NAME + " (" +
                UnitsEntry._ID + " INTEGER PRIMARY KEY, " +
                UnitsEntry.COLUMN_ID_ITEMS + " INTEGER NOT NULL, " +
                UnitsEntry.COLUMN_CODE + " TEXT NOT NULL, " +
                UnitsEntry.COLUMN_NAME1 + " TEXT NOT NULL, " +
                UnitsEntry.COLUMN_NAME2 + " TEXT NOT NULL, " +
                UnitsEntry.COLUMN_LINE_NR + " INTEGER NOT NULL, " +
                UnitsEntry.COLUMN_CONV_FACT1 + " REAL NOT NULL, " +
                UnitsEntry.COLUMN_CONV_FACT2 + " REAL NOT NULL, " +
                UnitsEntry.COLUMN_CDATE + " DATE NOT NULL, " +
                UnitsEntry.COLUMN_CTIME + " DATETIME NOT NULL " +
                ");";

        final String SQL_CREATE_BARCODES_TABLE = "CREATE TABLE " + BarcodesEntry.TABLE_NAME + " (" +
                BarcodesEntry._ID + " INTEGER PRIMARY KEY, " +
                BarcodesEntry.COLUMN_ID_UNIT + " INTEGER NOT NULL, " +
                BarcodesEntry.COLUMN_ID_ITEMS + " INTEGER NOT NULL, " +
                BarcodesEntry.COLUMN_BARCODE + " TEXT NOT NULL, " +
                BarcodesEntry.COLUMN_LINE_NR + " INTEGER NOT NULL, " +
                BarcodesEntry.COLUMN_CDATE + " DATE NOT NULL, " +
                BarcodesEntry.COLUMN_CTIME + " DATETIME NOT NULL " +
                ");";

        db.execSQL(SQL_CREATE_UNITS_TABLE);
        db.execSQL(SQL_CREATE_BARCODES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TALBE IF EXISTS " + UnitsEntry.TABLE_NAME);
        db.execSQL("DROP TALBE IF EXISTS " + BarcodesEntry.TABLE_NAME);
    }
}