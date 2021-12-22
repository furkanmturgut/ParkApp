package com.kunai.parkapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class ExitDetailActivity extends AppCompatActivity {
    TextView textExitDate, textAddDate, textBrandExtra;
    FirebaseFirestore firebaseFirestore;
    String id;
    String myPlate;
    String plateCikan;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit_detail);

        getWindow().setStatusBarColor(getResources().getColor(R.color.colorGreen));

        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");

        initialize();
        getData();
/*
        SharedPreferences sharedPref = this.getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
        myPlate = sharedPref.getString("plaka", "Kayıt Yok");
*/

/*
        try {
            FileInputStream fileInputStream = openFileInput("myFiles.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer =new StringBuffer();

            String lines;

            while ((lines = bufferedReader.readLine()) != null){
                stringBuffer.append(lines);
            }
            myPlate = stringBuffer.toString();

        }catch (Exception e){
            e.printStackTrace();
        }

        Toast.makeText(this, myPlate+"", Toast.LENGTH_SHORT).show();

        */


    }



    private void getData() {
        CollectionReference collectionReference = firebaseFirestore.collection("CikanArac");
        collectionReference.whereEqualTo("id", id).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null) {
                    for (DocumentSnapshot snapshot : value.getDocuments()) {
                        Map<String, Object> myMap = snapshot.getData();

                        String plate = (String) myMap.get("cikanPlaka");
                        String date = (String) myMap.get("cikanSaat");

                        textBrandExtra.setText(plate);
                        textExitDate.setText(date);
                        myPlate = plate;

                        firebaseFirestore.collection("MevcutPark").whereEqualTo("plaka", plate).addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                                for (DocumentSnapshot snapshot : value.getDocuments()) {
                                    Map<String, Object> maps = snapshot.getData();
                                    String date = (String) maps.get("saat");

                                    textAddDate.setText(date);

                                }
                            }
                        });


                    }
                }
            }
        });
    }


    private void initialize() {
        textBrandExtra = findViewById(R.id.textBrandExtra);
        textAddDate = findViewById(R.id.textAddDate);
        textExitDate = findViewById(R.id.textExitDate);
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public void addPayment(View view) {
        Intent intent = new Intent(ExitDetailActivity.this, PaymentActivity.class);
        intent.putExtra("plaka", myPlate);
        startActivity(intent);

    }
}