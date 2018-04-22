package com.example.abdulrahman.bobsplash;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.abdulrahman.bobsplash.service.Const;
import com.example.abdulrahman.bobsplash.service.DownloadService;
import com.squareup.picasso.Picasso;


import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class ImageDetail extends AppCompatActivity {

    private static final String TAG = "ViewPagerActivity";

    public static final String KEY_USER_NAME = "userName";
    public static final String KEY_USER_LOCATION = "userLocation";
    public static final String KEY_PHOTO_URL = "photoUrl";
    public static final String KEY_PHOTOGRAPHER_IMG = "photographerIMG";
    public static final String KEY_PHOTO_TIME="photoTime";

    private String userName;
    private String userLocation;
    private String photoUrlRegular;
    private String photographerImgUrl;
    private String photoCreatedAt;


    Toolbar mToolbar;


    ImageView detailPhoto;
    CircleImageView photographerImg;
    TextView photographerName;
    TextView photographerLocation;
    TextView photoTime;


    NotificationManager mNotifyManager;
    NotificationCompat.Builder mBuilder;


    ResponseReceiver responseReceiver;

    FloatingActionButton fabDownlaod;
    FloatingActionButton fabSetWallpaper;
    FloatingActionButton fabSharePhoto;




    WallpaperManager wallpaperManager ;
    Bitmap bitmap1, bitmap2 ;
    DisplayMetrics displayMetrics ;
    int width, height;
    BitmapDrawable bitmapDrawable ;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);

        initToolbar ();
        initView ();
        setupIntentExtras();
        setupViewsWithExtras();



        fabSetWallpaper=findViewById(R.id.fab_setwallpaper_photo);
        fabDownlaod=findViewById(R.id.fab_download_photo);
        fabSharePhoto=findViewById(R.id.fab_share_photo_);

        fabDownlaod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isStoragePermissionGranted())
                {
                    downLoadphoto();
                }
            }
        });

        fabSharePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, photoUrlRegular);
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, "Share download link to "));
            }
        });



        bitmapDrawable=(BitmapDrawable)detailPhoto.getDrawable();
        bitmap1= bitmapDrawable.getBitmap();

        fabSetWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GetScreenWidthHeight();

                SetBitmapSize();

                wallpaperManager=WallpaperManager.getInstance(ImageDetail.this);

                try {

                    wallpaperManager.setBitmap(bitmap2);
                    wallpaperManager.suggestDesiredDimensions(width,height);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });




    }




    public void GetScreenWidthHeight(){

        displayMetrics = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        width = displayMetrics.widthPixels;

        height = displayMetrics.heightPixels;

    }


    public void SetBitmapSize(){

        bitmap2 = Bitmap.createScaledBitmap(bitmap1, width, height, false);

    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter intentFilter = new IntentFilter(Const.My_BROADCAST_ACTION);
        responseReceiver = new ResponseReceiver();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.registerReceiver(responseReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.unregisterReceiver(responseReceiver);
    }

    private void initToolbar()
    {

        mToolbar=findViewById(R.id.profile_toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    private void initView()
    {
        detailPhoto=findViewById(R.id.img_photo);
        photographerImg=findViewById(R.id.img_author_header);
        photographerName=findViewById(R.id.tv_author);
        photographerLocation=findViewById(R.id.tv_location);
        photoTime=findViewById(R.id.tv_time);

    }


    private void setupIntentExtras()
    {
        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            userName = extras.getString(KEY_USER_NAME);
            userLocation = extras.getString(KEY_USER_LOCATION);
            photoUrlRegular = extras.getString(KEY_PHOTO_URL);
            photographerImgUrl=extras.getString(KEY_PHOTOGRAPHER_IMG);
            photoCreatedAt=extras.getString(KEY_PHOTO_TIME);

        }
    }

    private void  setupViewsWithExtras()
    {
        photographerName.setText(userName);
        photographerLocation.setText(userLocation);
        photoTime.setText("Shot in "+photoCreatedAt.split("T")[0]);

        Picasso.with(ImageDetail.this).load(photoUrlRegular).into(detailPhoto);
        Picasso.with(ImageDetail.this).load(photographerImgUrl).into(photographerImg);
    }

    private void downLoadphoto()
    {
        String url=photoUrlRegular;

        mNotifyManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder=new NotificationCompat.Builder(ImageDetail.this,"channel_id");
        mBuilder.setContentTitle("File Download")
                .setContentText("Download in progress")
                .setSmallIcon(android.R.drawable.stat_sys_download)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        R.mipmap.ic_launcher));

        Intent intent = new Intent(ImageDetail.this, DownloadService.class);
        intent.putExtra(Const.My_INTENT_DOWNLOAD,url);
        startService(intent);


    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else {

            return true;
        }
    }

    public class ResponseReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent) {
            int message = intent.getIntExtra(Const.My_INTENT_RESPONSE_DOWNLOAD, 0);
            if (message != 0) {
                mBuilder.setProgress(100, message, false);
                // Displays the progress bar on notification
                mNotifyManager.notify(0, mBuilder.build());
            } else {
                Intent notificationIntent = new Intent(ImageDetail.this, MainActivity.class);
                Log.d(TAG, "NOTIFY_URL: "+intent.getStringExtra(Const.NOTIFY_URL));
                notificationIntent.putExtra(Const.NOTIFY_OPEN_URL,intent.getStringExtra(Const.NOTIFY_URL));
                notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(ImageDetail.this, 0,
                        notificationIntent, 0);
                mBuilder.setContentIntent(pendingIntent);
                mBuilder.setAutoCancel(true);
                mBuilder.setContentText("Download complete");
                // Removes the progress bar
                mBuilder.setProgress(0, 0, false);
                mBuilder.setSmallIcon(android.R.drawable.stat_sys_download_done);
                mNotifyManager.notify(0, mBuilder.build());
            }
        }
    }





}
