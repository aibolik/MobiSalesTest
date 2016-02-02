package kz.aibol.mobisalestest.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
                return DataContract.Items.CONTENT_TYPE;
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
                retCursor = mOpenHelper.getReadableDatabase().query(
                        DataContract.Items.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
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
                long _id = db.insert(DataContract.FiletimesEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = DataContract.FiletimesEntry.buildWeatherUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case ITEMS: {
                long _id = db.insert(DataContract.Items.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = DataContract.Items.buildWeatherUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case UNITS: {
                long _id = db.insert(DataContract.UnitsEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = DataContract.UnitsEntry.buildWeatherUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case BARCODES: {
                long _id = db.insert(DataContract.BarcodesEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = DataContract.BarcodesEntry.buildWeatherUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case PRICES: {
                long _id = db.insert(DataContract.PricesEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = DataContract.PricesEntry.buildWeatherUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case ITEMFILES: {
                long _id = db.insert(DataContract.ItemfilesEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = DataContract.ItemfilesEntry.buildWeatherUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
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
                rowsDeleted = db.delete(DataContract.Items.TABLE_NAME, selection, selectionArgs);
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
                rowsUpdated = db.update(DataContract.Items.TABLE_NAME, values, selection, selectionArgs);
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
