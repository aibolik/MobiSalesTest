package kz.aibol.mobisalestest.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by aibol on 2/2/16.
 */
public class DataContract {
    public static final String CONTENT_AUTHORITY = "kz.aibol.mobisalestest";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_UNITS = "units";
    public static final String PATH_BARCODES = "barcodes";


    public static final class UnitsEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_UNITS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_UNITS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_UNITS;

        public static final String TABLE_NAME = "units";

        public static final String COLUMN_ID_ITEMS = "id_items";
        public static final String COLUMN_CODE = "code";
        public static final String COLUMN_NAME1 = "name1";
        public static final String COLUMN_NAME2 = "name2";
        public static final String COLUMN_LINE_NR = "line_nr";
        public static final String COLUMN_CONV_FACT1 = "conv_fact1";
        public static final String COLUMN_CONV_FACT2 = "conv_fact2";
        public static final String COLUMN_CDATE = "cdate";
        public static final String COLUMN_CTIME = "ctime";
    }

    public static final class BarcodesEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_BARCODES).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BARCODES;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BARCODES;

        public static final String TABLE_NAME = "barcodes";

        public static final String COLUMN_ID_UNIT = "id_unit";
        public static final String COLUMN_ID_ITEMS = "id_items";
        public static final String COLUMN_BARCODE = "barcode";
        public static final String COLUMN_LINE_NR = "line_nr";
        public static final String COLUMN_CDATE = "cdate";
        public static final String COLUMN_CTIME = "ctime";
    }

}
