package com.example.abdulrahman.bobsplash.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Abdulrahman on 4/20/2018.
 */

public class DownloadService  extends IntentService{

    private static final String TAG = "DownloadService";
    String downloadPath = "";

    public DownloadService() {
        super("My download service");
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String strUrl = intent.getStringExtra(Const.My_INTENT_DOWNLOAD);
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(Const.My_BROADCAST_ACTION);
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        //download file
        int count;
        URL url;
        try {
            Log.d(TAG, "path: " + strUrl);

            url = new URL(strUrl);
            try {
                File directory = new File(Const.DOWNLOAD_PATH);
                if (! directory.exists()){
                    directory.mkdir();
                }
                File f = new File(Const.DOWNLOAD_PATH);
                if (f.exists()) {
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    InputStream is = con.getInputStream();
                    String pathr = url.getPath();
                    String filename = pathr.substring(pathr.lastIndexOf('/') + 1);
                    downloadPath =Const. DOWNLOAD_PATH + filename + ".jpg";
                    Log.d(TAG, "path: " + downloadPath);
                    FileOutputStream fos = new FileOutputStream(downloadPath);
                    int lenghtOfFile = con.getContentLength();
                    byte data[] = new byte[1024];
                    long total = 0, result, old_result = 0;
                    while ((count = is.read(data)) != -1) {
                        total += count;
                        // send broadcast of progress update
                        result = (int) ((total * 100) / lenghtOfFile);
                        if (result != 0 && old_result != result) {
                            Log.d(TAG, "onHandleIntent_count: " + result);
                            old_result = result;
                            //send broadcast only at modulo(%) of 5
                            if (old_result % 5 == 0) {
                                broadcastIntent.putExtra(Const.My_INTENT_RESPONSE_DOWNLOAD, (int) result);
                                localBroadcastManager.sendBroadcast(broadcastIntent);
                            }
                        }
                        // writing data to output file
                        fos.write(data, 0, count);
                    }
                    is.close();
                    fos.flush();
                    fos.close();
                } else {
                    Log.e("Error", "Not found: " +Const. DOWNLOAD_PATH);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        broadcastIntent.putExtra(Const.My_INTENT_RESPONSE_DOWNLOAD, 0);
        broadcastIntent.putExtra(Const.NOTIFY_URL,downloadPath);
        localBroadcastManager.sendBroadcast(broadcastIntent);
        //send broadcast to activity
        Log.d(TAG, "onHandleIntent: service_stopped");
    }
}
