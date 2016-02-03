package kz.aibol.mobisalestest.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by aibol on 2/2/16.
 */
public class DataProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private DataDbHelper mOpenHelper;

    static final int FILETIMES = 100;
    static final int ITEMS = 200;
    static final int UNITS = 300;
    static final int BARCODES = 400;
    static final int PRICES = 500;
    static final int ITEMFILES = 600;

    private static final SQLiteQueryBuilder sItemWithPriceQueryBuiler;

    static {
        sItemWithPriceQueryBuiler = new SQLiteQueryBuilder();

        sItemWithPriceQueryBuiler.setTables(
                DataContract.ItemsEntry.TABLE_NAME + " INNER JOIN " +
                        DataContract.PricesEntry.TABLE_NAME +
                        " ON " + DataContract.ItemsEntry.TABLE_NAME +
                        "." + DataContract.ItemsEntry._ID +
                        " = " + DataContract.PricesEntry.TABLE_NAME +
                        "." + DataContract.PricesEntry.COLUMN_ID_ITEMS);
    }


    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = DataContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, DataContract.PATH_FILETIMES, FILETIMES);
        matcher.addURI(authority, DataContract.PATH_ITEMS, ITEMS);
        matcher.addURI(authority, DataContract.PATH_UNITS, UNITS);
        matcher.addURI(authority, DataContract.PATH_BARCODES, BARCODES);
        matcher.addURI(authority, DataContract.PATH_PRICES, PRICES);
        matcher.addURI(authority, DataContract.PATH_ITEMFILES, ITEMFILES);

        return matcher;
    }

    private boolean isDataInTable(String TableName, String dbfield, long fieldValue) {
        SQLiteDatabase sqldb = mOpenHelper.getReadableDatabase();
        String Query = "Select * from " + TableName + " where " + dbfield + " = " + fieldValue;
        Cursor cursor = sqldb.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new DataDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case FILETIMES:
                return DataContract.FiletimesEntry.CONTENT_TYPE;
            case ITEMS:
                return DataContract.ItemsEntry.CONTENT_TYPE;
            case UNITS:
                return DataContract.UnitsEntry.CONTENT_TYPE;
            case BARCODES:
                return DataContract.BarcodesEntry.CONTENT_TYPE;
            case PRICES:
                return DataContract.PricesEntry.CONTENT_TYPE;
            case ITEMFILES:
                return DataContract.ItemfilesEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;

        switch (sUriMatcher.match(uri)) {
            case FILETIMES:
                retCursor = mOpenHelper.getReadableDatabase().query(
                        DataContract.FiletimesEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case ITEMS:
                retCursor = sItemWithPriceQueryBuiler.query(
                        mOpenHelper.getReadableDatabase(),
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
//                retCursor = mOpenHelper.getReadableDatabase().query(
//                        DataContract.ItemsEntry.TABLE_NAME,
//                        projection,
//                        selection,
//                        selectionArgs,
//                        null,
//                        null,
//                        sortOrder
//                );
                break;
            case UNITS:
                retCursor = mOpenHelper.getReadableDatabase().query(
                        DataContract.UnitsEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case BARCODES:
                retCursor = mOpenHelper.getReadableDatabase().query(
                        DataContract.BarcodesEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case PRICES:
                retCursor = mOpenHelper.getReadableDatabase().query(
                        DataContract.PricesEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case ITEMFILES:
                retCursor = mOpenHelper.getReadableDatabase().query(
                        DataContract.ItemfilesEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
//            case ITEMS_WITH_PRICES:
//                retCursor = sItemWithPriceQueryBuiler.query(
//                        mOpenHelper.getReadableDatabase(),
//                        projection,
//                        selection,
//                        selectionArgs,
//                        null,
//                        null,
//                        sortOrder
//                );
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case FILETIMES: {
                long id = values.getAsInteger(DataContract.FiletimesEntry._ID);
                if(isDataInTable(DataContract.FiletimesEntry.TABLE_NAME,
                        DataContract.FiletimesEntry._ID,
                        id)) {
                    returnUri = DataContract.FiletimesEntry.buildDataUri(id);
                }
                else {
                    long _id = db.insert(DataContract.FiletimesEntry.TABLE_NAME, null, values);
                    if (_id > 0) {
                        returnUri = DataContract.FiletimesEntry.buildDataUri(_id);
                    } else {
                        throw new android.database.SQLException("Failed to insert row into " + uri);
                    }
                }
                break;
            }
            case ITEMS: {
                long id = values.getAsInteger(DataContract.ItemsEntry._ID);
                if(isDataInTable(DataContract.ItemsEntry.TABLE_NAME,
                        DataContract.ItemsEntry._ID,
                        id)) {
                    returnUri = DataContract.ItemsEntry.buildDataUri(id);
                }
                else {
                    long _id = db.insert(DataContract.ItemsEntry.TABLE_NAME, null, values);
                    if (_id > 0) {
                        returnUri = DataContract.ItemsEntry.buildDataUri(_id);
                    } else {
                        throw new android.database.SQLException("Failed to insert row into " + uri);
                    }
                }

                break;
            }
            case UNITS: {
                long id = values.getAsInteger(DataContract.UnitsEntry._ID);
                if(isDataInTable(DataContract.UnitsEntry.TABLE_NAME,
                        DataContract.UnitsEntry._ID,
                        id)) {
                    returnUri = DataContract.UnitsEntry.buildDataUri(id);
                }
                else {
                    long _id = db.insert(DataContract.UnitsEntry.TABLE_NAME, null, values);
                    if (_id > 0) {
                        returnUri = DataContract.UnitsEntry.buildDataUri(_id);
                    } else {
                        throw new android.database.SQLException("Failed to insert row into " + uri);
                    }
                }
                break;
            }
            case BARCODES: {
                long id = values.getAsInteger(DataContract.BarcodesEntry._ID);
                if(isDataInTable(DataContract.BarcodesEntry.TABLE_NAME,
                        DataContract.BarcodesEntry._ID,
                        id)) {
                    returnUri = DataContract.BarcodesEntry.buildDataUri(id);
                }
                else {
                    long _id = db.insert(DataContract.BarcodesEntry.TABLE_NAME, null, values);
                    if (_id > 0) {
                        returnUri = DataContract.BarcodesEntry.buildDataUri(_id);
                    } else {
                        throw new android.database.SQLException("Failed to insert row into " + uri);
                    }
                }
                break;
            }
            case PRICES: {
                long id = values.getAsInteger(DataContract.PricesEntry._ID);
                if(isDataInTable(DataContract.PricesEntry.TABLE_NAME,
                        DataContract.PricesEntry._ID,
                        id)) {
                    returnUri = DataContract.PricesEntry.buildDataUri(id);
                }
                else {
                    long _id = db.insert(DataContract.PricesEntry.TABLE_NAME, null, values);
                    if (_id > 0) {
                        returnUri = DataContract.PricesEntry.buildDataUri(_id);
                    } else {
                        throw new android.database.SQLException("Failed to insert row into " + uri);
                    }
                }
                break;
            }
            case ITEMFILES: {
                long id = values.getAsInteger(DataContract.ItemfilesEntry._ID);
                if(isDataInTable(DataContract.ItemfilesEntry.TABLE_NAME,
                        DataContract.ItemfilesEntry._ID,
                        id)) {
                    returnUri = DataContract.ItemfilesEntry.buildDataUri(id);
                }
                else {
                    long _id = db.insert(DataContract.ItemfilesEntry.TABLE_NAME, null, values);
                    if (_id > 0) {
                        returnUri = DataContract.ItemfilesEntry.buildDataUri(_id);
                    } else {
                        throw new android.database.SQLException("Failed to insert row into " + uri);
                    }
                }
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);

        int rowsDeleted;

        if(selection == null) selection = "1";
        switch (match) {
            case FILETIMES:
                rowsDeleted = db.delete(DataContract.FiletimesEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case ITEMS:
                rowsDeleted = db.delete(DataContract.ItemsEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case UNITS:
                rowsDeleted = db.delete(DataContract.UnitsEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case BARCODES:
                rowsDeleted = db.delete(DataContract.BarcodesEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case PRICES:
                rowsDeleted = db.delete(DataContract.PricesEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case ITEMFILES:
                rowsDeleted = db.delete(DataContract.ItemfilesEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if(rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);

        int rowsUpdated;

        switch (match) {
            case FILETIMES:
                rowsUpdated = db.update(DataContract.FiletimesEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case ITEMS:
                rowsUpdated = db.update(DataContract.ItemsEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case UNITS:
                rowsUpdated = db.update(DataContract.UnitsEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case BARCODES:
                rowsUpdated = db.update(DataContract.BarcodesEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case PRICES:
                rowsUpdated = db.update(DataContract.PricesEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case ITEMFILES:
                rowsUpdated = db.update(DataContract.ItemfilesEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if(rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

}
