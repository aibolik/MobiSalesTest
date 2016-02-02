package kz.aibol.mobisalestest;

import android.content.ContentValues;
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
import java.util.Map;
import java.util.Vector;

/**
 * Created by aibol on 1/31/16.
 */

public class DownloadFileTask extends AsyncTask<String, Void, Boolean> {

    boolean download;

    public DownloadFileTask(boolean download) {
        this.download = download;
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


            Vector<ContentValues> cVVector = new Vector<ContentValues>();
            ContentValues dataValues = new ContentValues();


            String tag = "";
            String text = "";
            Boolean tag_open = false;

            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_DOCUMENT) {
                    Log.d(LOG_TAG, "Start document");
                } else if (eventType == XmlPullParser.START_TAG) {
                    tag = xpp.getName();
                    tag_open = true;
                } else if (eventType == XmlPullParser.END_TAG) {
                    if (tag_open == true) {
                        dataValues.put(tag, text);
                    } else {
                        if (dataValues.size() > 0) {
                            cVVector.add(dataValues);
                            dataValues = new ContentValues();
                        }
                    }
                    tag_open = false;

                } else if (eventType == XmlPullParser.TEXT) {
                    text = xpp.getText();
                }
                eventType = xpp.next();
            }
            Log.d(LOG_TAG, "End document");


            Log.d(LOG_TAG, "Number of instances: " + cVVector.size());
            for (ContentValues row : cVVector) {
                Log.d(LOG_TAG, "" + "Size of new instance is: " + row.size());
                for (String key : row.keySet()) {
                    Object value = row.get(key);
                    Log.d(LOG_TAG, "Key: " + key + " Value: " + value);


                }
            }
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
}
