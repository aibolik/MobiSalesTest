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

        final String SQL_CREATE_PRICES_TABLE = "CREATE TABLE " + PricesEntry.TABLE_NAME + " (" +
                PricesEntry._ID + " INTEGER PRIMARY KEY," +
                PricesEntry.COLUMN_ID_UNIT + " INTEGER NOT NULL, " +
                PricesEntry.COLUMN_ID_ITEMS + " INTEGER NOT NULL, " +
                PricesEntry.COLUMN_CODE + " TEXT NOT NULL, " +
                PricesEntry.COLUMN_CLSPECODE + " STRING NOT NULL " +
                PricesEntry.COLUMN_BEGDATE + " DATE NOT NULL " +
                PricesEntry.COLUMN_ENDDATE + " DATE NOT NULL " +
                PricesEntry.COLUMN_UNIT_CONVERT + " BOOLEAN NOT NULL " +
                PricesEntry.COLUMN_CDATE + " DATE NOT NULL " +
                PricesEntry.COLUMN_CTIME + " DATETIME NOT NULL " +
                /*
                " FOREIGN KEY (" + PricesEntry.COLUMN_ID_UNIT + ") REFERENCES " +
                UnitsEntry.TABLE_NAME + " (" + UnitsEntry._ID + "), " +
                " FOREIGN KEY (" + PricesEntry.COLUMN_ID_ITEMS + ") REFERENCES " +
                Items.TABLE_NAME + " (" + Items._ID + "), " +
                */
                ");";

        final String SQL_CREATE_ITEMFILES_TABLE = "CREATE TABLE " + ItemfilesEntry.TABLE_NAME + " (" +
                ItemfilesEntry._ID + " INTEGER PRIMARY KEY," +
                ItemfilesEntry.COLUMN_ID_ITEMS + " INTEGER NOT NULL, " +
                ItemfilesEntry.COLUMN_FILETYPE + " TEXT NOT NULL, " +
                ItemfilesEntry.COLUMN_FILENAME + " TEXT NOT NULL, " +
                ItemfilesEntry.COLUMN_LINENO + " INTEGER NOT NULL, " +
                ItemfilesEntry.COLUMN_DEFAULT + " BOOLEAN NOT NULL, " +
                ItemfilesEntry.COLUMN_CDATE + " DATE NOT NULL " +
                ItemfilesEntry.COLUMN_CTIME + " DATETIME NOT NULL " +
                ");";

        db.execSQL(SQL_CREATE_FILETIMES_TABLE);
        db.execSQL(SQL_CREATE_ITEMS_TABLE);
        db.execSQL(SQL_CREATE_UNITS_TABLE);
        db.execSQL(SQL_CREATE_BARCODES_TABLE);
        db.execSQL(SQL_CREATE_PRICES_TABLE);
        db.execSQL(SQL_CREATE_ITEMFILES_TABLE);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FiletimesEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Items.TABLE_NAME);
        db.execSQL("DROP TALBE IF EXISTS " + UnitsEntry.TABLE_NAME);
        db.execSQL("DROP TALBE IF EXISTS " + BarcodesEntry.TABLE_NAME);
        db.execSQL("DROP TALBE IF EXISTS " + PricesEntry.TABLE_NAME);
        db.execSQL("DROP TALBE IF EXISTS " + ItemfilesEntry.TABLE_NAME);
        onCreate(db);
    }
}