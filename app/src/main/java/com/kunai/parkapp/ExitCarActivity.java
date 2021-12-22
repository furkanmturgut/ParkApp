package com.kunai.parkapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

public class ExitCarActivity extends AppCompatActivity {
    EditText editPlate;
    Button buttonExitDate;
    FirebaseFirestore firebaseFirestore;
    Button buttonExit;
    Integer myhHour, myMinute;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit_car);

        getWindow().setStatusBarColor(getResources().getColor(R.color.colorGreen));
        editPlate = findViewById(R.id.editPlate);
        buttonExitDate = findViewById(R.id.buttonExitDate);
        buttonExit = findViewById(R.id.buttonExit);
        firebaseFirestore = FirebaseFirestore.getInstance();

        buttonExitDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar currentTime = Calendar.getInstance();
                int hour = currentTime.get(Calendar.HOUR);
                int minute = currentTime.get(Calendar.MINUTE);
                TimePickerDialog timePicker;
                timePicker = new TimePickerDialog(ExitCarActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        buttonExitDate.setText(i + "." + i1);
                        myhHour = i;
                        myMinute = i1;
                    }
                }, hour, minute, false);
                timePicker.show();

            }

        });



    }

    public void exitCarPlate(View view) {
        String plate = editPlate.getText().toString();
        String date = buttonExitDate.getText().toString();
        HashMap<String, Object> hashMap = new HashMap<>();
        String id = UUID.randomUUID().toString();

        hashMap.put("cikanPlaka", plate);
        hashMap.put("cikanSaat", date);
        hashMap.put("id", id);
        hashMap.put("cikisSaatTimestamp", FieldValue.serverTimestamp());

        SharedPreferences sharedPrefs = this.getSharedPreferences("gidenSaat", Context.MODE_PRIVATE);
        SharedPreferences.Editor editors = sharedPrefs.edit();
        editors.putInt("saat", myhHour);
        editors.putInt("dakika", myMinute);//string
        editors.commit();

        firebaseFirestore.collection("CikanArac").add(hashMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(@NonNull DocumentReference documentReference) {

                Intent intent = new Intent(ExitCarActivity.this, ExitDetailActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Exit Plate Error", e.getLocalizedMessage());
            }
        });

    }

}