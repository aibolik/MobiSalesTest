package kz.aibol.mobisalestest;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import kz.aibol.mobisalestest.data.DataContract;

/**
 * Created by aibol on 1/31/16.
 */

public class DownloadFileTask extends AsyncTask<String, Void, Boolean> {

    boolean download;
    Context mContext;

    public DownloadFileTask(boolean download, Context context) {
        this.download = download;
        mContext = context;
    }

    private final String LOG_TAG = DownloadFileTask.class.getSimpleName();

    @Override
    protected Boolean doInBackground(String... params) {
        String filename = params[0];
        if (filename == null) {
            Log.e(LOG_TAG, "No filename provided");
            return false;
        }
        try {
            return downloadFile("/Test/" + filename);
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage());
            return false;
        } catch (XmlPullParserException e) {
            Log.e(LOG_TAG, e.getMessage());
            return false;
        }
    }

    private boolean downloadFile(String filename) throws IOException, XmlPullParserException {
        FTPClient ftp = null;

        try {
            ftp = new FTPClient();
            ftp.connect("188.125.161.187", 1021);

            Log.d(LOG_TAG, "Connected. Reply: " + ftp.getReplyString());

            ftp.login("user", "asd.1234");
            Log.d(LOG_TAG, "Logged in");
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            Log.d(LOG_TAG, "Downloading");
            ftp.enterLocalPassiveMode();

            StringBuffer buffer = new StringBuffer();
            OutputStream outputStream = null;
            InputStream inputStream = null;

            BufferedReader reader = null;

            inputStream = ftp.retrieveFileStream(filename);

            if (inputStream == null) {
                Log.d(LOG_TAG, "File does not exist");
                return false;
            }

            if (!download) {
                return true;
            }


            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(inputStream, null);

//            Vector<ContentValues> cVVector = new Vector<ContentValues>();
//            ContentValues values = new ContentValues();

            ArrayList<Map<String, String>> listOfInstances = new ArrayList<Map<String, String>>();
            Map<String, String> instance = new HashMap<String, String>();
            String tag = "";
            String text = "";
            Boolean tag_open = false;


            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_DOCUMENT) {
                    //Log.d(LOG_TAG, "Start document");
                } else if (eventType == XmlPullParser.START_TAG) {
                    tag = xpp.getName();
                    tag_open = true;
                    //Log.d(LOG_TAG, "Start tag " + tag);
                } else if (eventType == XmlPullParser.END_TAG) {
                    //Log.d(LOG_TAG, "End tag " + xpp.getName());
                    if (tag_open) {
                        instance.put(tag, text);
                    } else {
                        if (instance.size() > 0)
                            listOfInstances.add(instance);
                        instance = new HashMap<String, String>();
                    }
                    tag_open = false;
                } else if (eventType == XmlPullParser.TEXT) {
                    text = xpp.getText();
                    //Log.d(LOG_TAG, "Text " + text);
                }
                eventType = xpp.next();
            }
            //Log.d(LOG_TAG, "End document");

            writeToDatabase(listOfInstances, filename);
            /*
            Log.d(LOG_TAG, "Number of instances: " + listOfInstances.size());
            for (Map<String, String> row : listOfInstances) {
                Log.d(LOG_TAG, "" + "Size of new instance is: " + row.size());
                for (Map.Entry<String, String> entry : row.entrySet()) {
                    Log.d(LOG_TAG, "Key: " + entry.getKey() + " Value: " + entry.getValue());
                }
            }
            */
            //Now just return listOfInstances arrayList.
            /*
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                Log.d(LOG_TAG, "Buffer line: " + line);
                buffer.append(line + "\n");
            }
            Log.d(LOG_TAG, "Output");
            Log.d(LOG_TAG, buffer.toString());
            */
            return true;

        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage());
            return false;
        } finally {
            if (ftp != null) {
                ftp.logout();
                ftp.disconnect();
            }
        }
    }

    private void writeToDatabase(ArrayList<Map<String, String>> listOfInstances, String filename) {
        filename = filename.substring(6, filename.length() - 4);
        ArrayList<String> keys = new ArrayList<>();
        Uri insertUri;
        switch (filename) {
            case "FILETIMES": {
                insertUri = DataContract.FiletimesEntry.CONTENT_URI;
                keys.add(DataContract.FiletimesEntry._ID);
                keys.add(DataContract.FiletimesEntry.COLUMN_FILENAME);
                keys.add(DataContract.FiletimesEntry.COLUMN_FILENAMEXML);
                keys.add(DataContract.FiletimesEntry.COLUMN_CDATE);
                keys.add(DataContract.FiletimesEntry.COLUMN_CTIME);
                break;
            }
            case "ITEMS": {
                insertUri = DataContract.ItemsEntry.CONTENT_URI;
                keys.add(DataContract.ItemsEntry._ID);
                keys.add(DataContract.ItemsEntry.COLUMN_CODE);
                keys.add(DataContract.ItemsEntry.COLUMN_NAME1);
                keys.add(DataContract.ItemsEntry.COLUMN_NAME2);
                keys.add(DataContract.ItemsEntry.COLUMN_SPECODE);
                keys.add(DataContract.ItemsEntry.COLUMN_STGRPCODE);
                keys.add(DataContract.ItemsEntry.COLUMN_CDATE);
                keys.add(DataContract.ItemsEntry.COLUMN_CTIME);
                break;
            }
            case "UNITS": {
                insertUri = DataContract.UnitsEntry.CONTENT_URI;
                keys.add(DataContract.UnitsEntry._ID);
                keys.add(DataContract.UnitsEntry.COLUMN_ID_ITEMS);
                keys.add(DataContract.UnitsEntry.COLUMN_CODE);
                keys.add(DataContract.UnitsEntry.COLUMN_NAME1);
                keys.add(DataContract.UnitsEntry.COLUMN_NAME2);
                keys.add(DataContract.UnitsEntry.COLUMN_LINE_NR);
                keys.add(DataContract.UnitsEntry.COLUMN_CONV_FACT1);
                keys.add(DataContract.UnitsEntry.COLUMN_CONV_FACT2);
                keys.add(DataContract.UnitsEntry.COLUMN_CDATE);
                keys.add(DataContract.UnitsEntry.COLUMN_CTIME);
                break;
            }
            case "BARCODES": {
                insertUri = DataContract.BarcodesEntry.CONTENT_URI;
                keys.add(DataContract.BarcodesEntry._ID);
                keys.add(DataContract.BarcodesEntry.COLUMN_ID_UNIT);
                keys.add(DataContract.BarcodesEntry.COLUMN_ID_ITEMS);
                keys.add(DataContract.BarcodesEntry.COLUMN_BARCODE);
                keys.add(DataContract.BarcodesEntry.COLUMN_LINE_NR);
                keys.add(DataContract.BarcodesEntry.COLUMN_CDATE);
                keys.add(DataContract.BarcodesEntry.COLUMN_CTIME);
                break;
            }
            case "PRICES": {
                insertUri = DataContract.PricesEntry.CONTENT_URI;
                keys.add(DataContract.PricesEntry._ID);
                keys.add(DataContract.PricesEntry.COLUMN_ID_UNIT);
                keys.add(DataContract.PricesEntry.COLUMN_ID_ITEMS);
                keys.add(DataContract.PricesEntry.COLUMN_CODE);
                keys.add(DataContract.PricesEntry.COLUMN_PRICE);
                keys.add(DataContract.PricesEntry.COLUMN_CLSPECODE);
                keys.add(DataContract.PricesEntry.COLUMN_BEGDATE);
                keys.add(DataContract.PricesEntry.COLUMN_ENDDATE);
                keys.add(DataContract.PricesEntry.COLUMN_UNIT_CONVERT);
                keys.add(DataContract.PricesEntry.COLUMN_CDATE);
                keys.add(DataContract.PricesEntry.COLUMN_CTIME);
                break;
            }
            case "ITEMFILES": {
                insertUri = DataContract.ItemfilesEntry.CONTENT_URI;
                keys.add(DataContract.ItemfilesEntry._ID);
                keys.add(DataContract.ItemfilesEntry.COLUMN_ID_ITEMS);
                keys.add(DataContract.ItemfilesEntry.COLUMN_FILETYPE);
                keys.add(DataContract.ItemfilesEntry.COLUMN_FILENAME);
                keys.add(DataContract.ItemfilesEntry.COLUMN_LINENO);
                keys.add(DataContract.ItemfilesEntry.COLUMN_DEFAULT);
                keys.add(DataContract.ItemfilesEntry.COLUMN_CDATE);
                keys.add(DataContract.ItemfilesEntry.COLUMN_CTIME);
                break;
            }
            default: {
                throw new UnsupportedOperationException("Invalid filename");
            }
        }
        for (Map<String, String> row : listOfInstances) {
            ContentValues values = new ContentValues();
            for (Map.Entry<String, String> entry : row.entrySet()) {
                if (entry.getKey().equals("ID")) {
                    values.put(DataContract.FiletimesEntry._ID, entry.getValue());
                } else {
                    values.put(entry.getKey().toLowerCase(), entry.getValue());
                }
            }
            mContext.getContentResolver().insert(insertUri, values);
            //printContentValues(values);
        }
    }

    public void printContentValues(ContentValues vals) {
        Set<Map.Entry<String, Object>> s = vals.valueSet();
        Iterator itr = s.iterator();

        Log.d("DatabaseSync", "ContentValue Length :: " + vals.size());

        while (itr.hasNext()) {
            Map.Entry me = (Map.Entry) itr.next();
            String key = me.getKey().toString();
            Object value = me.getValue();

            Log.d("DatabaseSync", "Key:" + key + ", values:" + (String) (value == null ? null : value.toString()));
        }
    }

}
