package com.kunai.parkapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddCarActivity extends AppCompatActivity {
    EditText editTextPlate, editTextBrand;
    FirebaseFirestore firebaseFirestore;
    Integer myhHour, myMinute;
    Button buttonDate;
    String myPlate;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);


        getWindow().setStatusBarColor(getResources().getColor(R.color.colorGreen));
        editTextPlate = findViewById(R.id.editTextPlate);
        editTextBrand = findViewById(R.id.editTextBrand);
        buttonDate = findViewById(R.id.buttonDate);
        firebaseFirestore = FirebaseFirestore.getInstance();


    }

    public void addCar(View view) {
        String brand = editTextBrand.getText().toString();
        String plate = editTextPlate.getText().toString().trim();
        String date = buttonDate.getText().toString();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("marka", brand);
        hashMap.put("plaka", plate);
        hashMap.put("saat", date);
        hashMap.put("tarihTimestamp", FieldValue.serverTimestamp());
/*
        SharedPreferences sharedPref = this.getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("plaka", plate); //string
        editor.commit();
        */

        // Internal storage
/*
        try {
            FileOutputStream fileOutputStream = openFileOutput("myFiles.txt", MODE_PRIVATE);
            fileOutputStream.write(plate.getBytes());
            fileOutputStream.close();

            Toast.makeText(this, "Ok internal", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        */



        SharedPreferences sharedPrefs = this.getSharedPreferences("gelenSaat", Context.MODE_PRIVATE);
        SharedPreferences.Editor editors = sharedPrefs.edit();
        editors.putInt("saat", myhHour);
        editors.putInt("dakika", myMinute);//string
        editors.commit();


        firebaseFirestore.collection("MevcutPark").add(hashMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(@NonNull DocumentReference documentReference) {
                Log.e("dB Save Succes!", "Add car dB Succes!");
                Snackbar snackbar = Snackbar.make(view, "Araç eklendi.", BaseTransientBottomBar.LENGTH_LONG);
                snackbar.show();

                //Son çare
                HashMap<String,Object> newHashMap = new HashMap<>();
                newHashMap.put("plaka",plate);
                firebaseFirestore.collection("Plakalar").document(plate).set(newHashMap);
                //

                Intent intent = new Intent(AddCarActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Snackbar snackbar = Snackbar.make(view, "İletişime geçiniz.", BaseTransientBottomBar.LENGTH_LONG);
                snackbar.show();
                Log.e("Add car dB Error!", e.getLocalizedMessage());
            }
        });


    }

    public void addDateText(View view) {
        Calendar currentTime = Calendar.getInstance();
        int hour = currentTime.get(Calendar.HOUR);
        int minute = currentTime.get(Calendar.MINUTE);
        TimePickerDialog timePicker;
        timePicker = new TimePickerDialog(AddCarActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                buttonDate.setText(i + "." + i1);
                myhHour = i;
                myMinute = i1;

            }
        }, hour, minute, false);
        timePicker.show();

    }
}