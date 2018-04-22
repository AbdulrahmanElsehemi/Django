package com.example.abdulrahman.bobsplash.service;

import android.os.Environment;



public class Const {

    public static final int PER_PAGE = 15;

    public static final String END_POINT = "https://api.unsplash.com/search/";
    public static final String CLIENT_ID = "eb54e3b9dc12b9e0862b028b646085355d20b3442fbdfca4633ca0f7b01ef9a6";
    public static final String My_INTENT = "com.android.karthi.androidtask.Activity_myintent";
    public static final String My_INTENT_POSITION = "com.android.karthi.androidtask.Activity_myintent_position";
    public static final String My_INTENT_ARG_POSITION = "com.android.karthi.androidtask.Activity_arg_position";
    public static final String My_INTENT_DOWNLOAD = "com.android.karthi.androidtask.download_url";
    public static final String My_INTENT_RESPONSE_DOWNLOAD = "com.android.karthi.androidtask.download_url_response";
    public static final String My_BROADCAST_ACTION = "com.android.karthi.androidtask.intent_service.all_done";
    public static final String DOWNLOAD_PATH  = Environment.getExternalStorageDirectory().toString()+"/Unsplash/";
    public static final String NOTIFY_URL  = "com.android.karthi.androidtask.notify_url";
    public static final String NOTIFY_OPEN_URL  = "com.android.karthi.androidtask.notify_open_url";

}