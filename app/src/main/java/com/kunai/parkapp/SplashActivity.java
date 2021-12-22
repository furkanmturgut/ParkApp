package com.kunai.parkapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import com.kunai.parkapp.login.MainActivity;

public class SplashActivity extends AppCompatActivity {



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().setStatusBarColor(getResources().getColor(R.color.colorGreen));

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService (Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if (activeNetwork != null) {
            // internet var ise


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            },3000);

        } else {
            // eğer internet yok ise
           new Handler().postDelayed(new Runnable() {
               @Override
               public void run() {
                   AlertDialog.Builder alertDialog = new AlertDialog.Builder(SplashActivity.this);
                   alertDialog.setTitle("Turgut Otopark");
                   alertDialog.setMessage("İnternet bağlantınızı kontrol ediniz.");
                   alertDialog.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                           finish();
                       }
                   });
                   alertDialog.show();
               }
           },3000);
        }

    }
}