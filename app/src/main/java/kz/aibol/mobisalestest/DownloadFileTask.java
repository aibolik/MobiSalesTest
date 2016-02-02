package kz.aibol.mobisalestest;

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

            ArrayList<Map<String, String>> listOfInstances = new ArrayList<Map<String, String>>();
            Map<String, String> instance = new HashMap<String, String>();
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
                    Log.d(LOG_TAG, "Start tag " + tag);
                } else if (eventType == XmlPullParser.END_TAG) {
                    Log.d(LOG_TAG, "End tag " + xpp.getName());
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
                    Log.d(LOG_TAG, "Text " + text);
                }
                eventType = xpp.next();
            }
            Log.d(LOG_TAG, "End document");

            Log.d(LOG_TAG, "Number of instances: " + listOfInstances.size());
            for (Map<String, String> row : listOfInstances) {
                Log.d(LOG_TAG, "" + "Size of new instance is: " + row.size());
                for (Map.Entry<String, String> entry : row.entrySet()) {
                    Log.d(LOG_TAG, "Key: " + entry.getKey() + " Value: " + entry.getValue());
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
