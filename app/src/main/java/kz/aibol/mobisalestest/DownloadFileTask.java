package kz.aibol.mobisalestest;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Created by aibol on 1/31/16.
 */
public class DownloadFileTask extends AsyncTask<Void, Void, Void> {

    private final String LOG_TAG = DownloadFileTask.class.getSimpleName();

    @Override
    protected Void doInBackground(Void... params) {
        try {
            downloadFile("/Test/FILETIMES.XML");
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage());
        }
        return null;
    }

    private void downloadFile(String filename) throws IOException {
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
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                Log.d(LOG_TAG, "Buffer line: " + line);
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                Log.d(LOG_TAG, "Buffer with size = 0");
            }
            Log.d(LOG_TAG, "Here is our output");
            Log.d(LOG_TAG, buffer.toString());

        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage());
        } finally {
            if (ftp != null) {
                ftp.logout();
                ftp.disconnect();
            }
        }
    }
}
