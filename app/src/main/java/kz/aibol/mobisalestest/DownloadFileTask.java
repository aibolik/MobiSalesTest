package kz.aibol.mobisalestest;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import kz.aibol.mobisalestest.data.DataContract;

/**
 * Created by aibol on 1/31/16.
 */

public class DownloadFileTask extends AsyncTask<Void, Void, Boolean> {

    Context mContext;
    boolean download;
    boolean mainfile;
    String filename;
    int progress;
    static ProgressDialog dialog;

    public DownloadFileTask(Context context, boolean download, boolean mainfile, String filename, int progress) {
        mContext = context;
        this.download = download;
        this.mainfile = mainfile;
        this.filename = filename;
        this.progress = progress;

        if (mainfile && !download) {
            dialog = new ProgressDialog(mContext);
            dialog.setMessage("Downloading database");
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
        }
    }

    private final String LOG_TAG = DownloadFileTask.class.getSimpleName();

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        Log.d(LOG_TAG, "AsyncTask started: " + download + ", " + mainfile + ", " + filename);
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

    @Override
    protected void onPostExecute(Boolean result) {
        if (mainfile && !download && !result) {
            Toast.makeText(mContext, "Filetimes file not ready", Toast.LENGTH_LONG).show();
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
            return;
        }
        if (mainfile && result && !download) {
            new DownloadFileTask(mContext, true, true, "FILETIMES", 0).execute();
            return;
        }
        if (mainfile && download && result) {
            new DownloadFileTask(mContext, false, false, "ITEMS", 1).execute();
            new DownloadFileTask(mContext, false, false, "UNITS", 2).execute();
            new DownloadFileTask(mContext, false, false, "BARCODES", 3).execute();
            new DownloadFileTask(mContext, false, false, "PRICES", 4).execute();
            new DownloadFileTask(mContext, false, false, "ITEMFILES", 5).execute();
        }
        if (!mainfile && !download && result) {
            new DownloadFileTask(mContext, true, false, filename, progress).execute();
        }
        if (progress == 5 && download) {
            Toast.makeText(mContext, "Database ready to use", Toast.LENGTH_LONG).show();
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
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

            if (!download) {
                inputStream = ftp.retrieveFileStream(filename + ".RDY");
            } else {
                inputStream = ftp.retrieveFileStream(filename + ".XML");
            }

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
                    if (tag_open) {
                        instance.put(tag, text);
                    } else if (instance.size() > 0) {
                        listOfInstances.add(instance);
                        instance = new HashMap<String, String>();
                    }
                    tag_open = false;
                } else if (eventType == XmlPullParser.TEXT) {
                    text = xpp.getText();
                }
                eventType = xpp.next();
            }

            writeToDatabase(listOfInstances, filename);
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
        filename = filename.substring(6);
        Uri insertUri;
        switch (filename) {
            case "FILETIMES": {
                insertUri = DataContract.FiletimesEntry.CONTENT_URI;
                break;
            }
            case "ITEMS": {
                insertUri = DataContract.ItemsEntry.CONTENT_URI;
                break;
            }
            case "UNITS": {
                insertUri = DataContract.UnitsEntry.CONTENT_URI;
                break;
            }
            case "BARCODES": {
                insertUri = DataContract.BarcodesEntry.CONTENT_URI;
                break;
            }
            case "PRICES": {
                insertUri = DataContract.PricesEntry.CONTENT_URI;
                break;
            }
            case "ITEMFILES": {
                insertUri = DataContract.ItemfilesEntry.CONTENT_URI;
                break;
            }
            default: {
                throw new UnsupportedOperationException("Invalid filename:" + filename);
            }
        }
        for (Map<String, String> row : listOfInstances) {
            ContentValues values = new ContentValues();
            for (Map.Entry<String, String> entry : row.entrySet()) {
                if (entry.getKey().equals("ID")) {
                    values.put(DataContract.FiletimesEntry._ID, entry.getValue());
                } else if (entry.getKey().equals("DEFAULT")) {
                    values.put("default1", entry.getValue());
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
