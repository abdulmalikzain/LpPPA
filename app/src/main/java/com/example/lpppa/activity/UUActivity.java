package com.example.lpppa.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.lpppa.R;

import java.io.File;
import java.io.IOException;

public class UUActivity extends AppCompatActivity {
    private Button button;
    private static final int PERMISSION_STORAGE_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u_u);

        button = findViewById(R.id.btn_download);
        button.setOnClickListener(view -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                    String [] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    requestPermissions(permissions, PERMISSION_STORAGE_CODE);
                }else {
                    download();
                }
            }else {
                download();
            }
        });

//        button.setOnClickListener(view -> ());
    }

    private void download(){

        String url = "https://drive.google.com/file/d/1B9_5QovYedIhXnHi1VL3-VoNKdCAFTsC/view?usp=sharing";
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle("Download");
        request.setDescription("Downloading file...");

        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, ""+ System.currentTimeMillis());

        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_STORAGE_CODE:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    download();
                }else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

//    private void downlod(){
//        new AsyncTask<Void, Integer, Boolean>() {
//
//            @Override
//            protected Boolean doInBackground(Void... params) {
//                try {
//                    File file = service.files().get("path in drive").execute();
//                    java.io.File toFile = new java.io.File("where you want to store");
//                    toFile.createNewFile();
//                    HttpDownloadManager downloader = new HttpDownloadManager(file, toFile);
//                    downloader.setListener(new HttpDownloadManager.FileDownloadProgressListener() {
//
//                        public void downloadProgress(long bytesRead, long totalBytes) {
//                            Log.i("chauster",totalBytes);
//                            Log.i("chauster",bytesRead);
//                        }
//
//                        @Override
//                        public void downloadFinished() {
//                            // TODO Auto-generated method stub
//                        }
//
//                        @Override
//                        public void downloadFailedWithError(Exception e) {
//                            // TODO Auto-generated method stub
//                        }
//                    });
//                    return downloader.download(service);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                return false;
//            }
//            protected void onPostExecute(Boolean result) {
//            };
//        }.execute();
//    }
}