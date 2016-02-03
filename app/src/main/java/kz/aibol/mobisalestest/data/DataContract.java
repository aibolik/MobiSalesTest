package kz.aibol.mobisalestest.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**

 * Created by Amirzhan on 2/2/16.
 */
public class DataContract {
    public static final String CONTENT_AUTHORITY = "kz.aibol.mobisalestest";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_FILETIMES = "filetimes";
    public static final String PATH_ITEMS = "items";
    public static final String PATH_UNITS = "units";
    public static final String PATH_BARCODES = "barcodes";
    public static final String PATH_PRICES = "prices";
    public static final String PATH_ITEMFILES = "itemfiles";

    public static final class FiletimesEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FILETIMES).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FILETIMES;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FILETIMES;

        public static final String TABLE_NAME = "filetimes";

        public static final String COLUMN_FILENAME = "filename";
        public static final String COLUMN_FILENAMEXML = "filenamexml";
        public static final String COLUMN_CDATE = "cdate";
        public static final String COLUMN_CTIME = "ctime";

        public static Uri buildDataUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
    public static final class ItemsEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ITEMS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ITEMS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ITEMS;

        public static final String TABLE_NAME = "items";

        public static final String COLUMN_CODE = "code";
        public static final String COLUMN_NAME1 = "name1";
        public static final String COLUMN_NAME2 = "name2";
        public static final String COLUMN_SPECODE = "specode";
        public static final String COLUMN_STGRPCODE = "stgrpcode";
        public static final String COLUMN_CDATE = "cdate";
        public static final String COLUMN_CTIME = "ctime";

        public static Uri buildDataUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class UnitsEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_UNITS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_UNITS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_UNITS;

        public static final String TABLE_NAME = "units";

        public static final String COLUMN_ID_ITEMS = "iditems";
        public static final String COLUMN_CODE = "code";
        public static final String COLUMN_NAME1 = "name1";
        public static final String COLUMN_NAME2 = "name2";
        public static final String COLUMN_LINE_NR = "linenr";
        public static final String COLUMN_CONV_FACT1 = "convfact1";
        public static final String COLUMN_CONV_FACT2 = "convfact2";
        public static final String COLUMN_CDATE = "cdate";
        public static final String COLUMN_CTIME = "ctime";

        public static Uri buildDataUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class BarcodesEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_BARCODES).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BARCODES;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BARCODES;

        public static final String TABLE_NAME = "barcodes";

        public static final String COLUMN_ID_UNIT = "idunit";
        public static final String COLUMN_ID_ITEMS = "iditems";
        public static final String COLUMN_BARCODE = "barcode";
        public static final String COLUMN_LINE_NR = "linenr";
        public static final String COLUMN_CDATE = "cdate";
        public static final String COLUMN_CTIME = "ctime";

        public static Uri buildDataUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
    public static final class PricesEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_PRICES).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRICES;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRICES;

        public static final String TABLE_NAME = "prices";

        public static final String COLUMN_ID_UNIT = "idunit";
        public static final String COLUMN_ID_ITEMS = "iditems";
        public static final String COLUMN_CODE = "code";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_CLSPECODE = "clspecode";
        public static final String COLUMN_BEGDATE = "begdate";
        public static final String COLUMN_ENDDATE = "enddate";
        public static final String COLUMN_UNIT_CONVERT = "unitconvert";
        public static final String COLUMN_CDATE = "cdate";
        public static final String COLUMN_CTIME = "ctime";

        public static Uri buildDataUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
    public static final class ItemfilesEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ITEMFILES).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ITEMFILES;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ITEMFILES;

        public static final String TABLE_NAME = "itemfiles";

        public static final String COLUMN_ID_ITEMS = "iditems";
        public static final String COLUMN_FILETYPE = "filetype";
        public static final String COLUMN_FILENAME = "filename";
        public static final String COLUMN_LINENO = "lineno";
        public static final String COLUMN_DEFAULT = "default1";
        public static final String COLUMN_CDATE = "cdate";
        public static final String COLUMN_CTIME = "ctime";

        public static Uri buildDataUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
