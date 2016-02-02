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

    public static final class FiletimesEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FILETIMES).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FILETIMES;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FILETIMES;



        public static final String TABLE_NAME = "filetimes";
        public static final String COLUMN_FILENAME = "filename";
        public static final String COLUMN_FILENAMEXML = "filnamexml";
        public static final String COLUMN_CDATE = "cdate";
        public static final String COLUMN_CTIME = "ctime";

    }
    public static final class Items implements BaseColumns {

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

    }


}
