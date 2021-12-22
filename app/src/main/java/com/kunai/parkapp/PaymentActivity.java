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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class PaymentActivity extends AppCompatActivity {
    TextView textPayment;
    Integer s1, s2;
    Integer i1, i2;
    Double minute, exitMinute;
    FirebaseFirestore firebaseFirestore;
    String myPlate;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        textPayment = findViewById(R.id.textPayment);

        Bundle bundle = getIntent().getExtras();
        myPlate = bundle.getString("plaka");

        getWindow().setStatusBarColor(getResources().getColor(R.color.colorGreen));
        firebaseFirestore = FirebaseFirestore.getInstance();



        SharedPreferences sharedPref = this.getSharedPreferences("gelenSaat", Context.MODE_PRIVATE);
        s1 = sharedPref.getInt("saat", 0);
        s2 = sharedPref.getInt("dakika", 0);
        //Toast.makeText(this, s1 + "/" + s2, Toast.LENGTH_SHORT).show();

        SharedPreferences sharedPrefs = this.getSharedPreferences("gidenSaat", Context.MODE_PRIVATE);
        i1 = sharedPrefs.getInt("saat", 0);
        i2 = sharedPrefs.getInt("dakika", 0);
        Toast.makeText(this, i1 + "/" + i2, Toast.LENGTH_SHORT).show();

        //Gelen Saat
        if (i1 > s1 && i2 == s2) {
            double minute = i1 - s1;
            double addMinute = minute * 60;
            double convertMinute = addMinute / 30;
            double price = convertMinute * 5;
            Integer myPrice = Integer.valueOf((int) price);
            textPayment.setText(myPrice + " TL");

        } else if(i1 > s1 && i2 > s2){
            double minuteToHour = 60 - s2 ;  // 40
            s1 = s1+1; // 9
            double hours = i1 - s1 ; // 1
            double convert = hours * 60 + i2 + minuteToHour;
            double continueConvert = convert / 30;
            double price = continueConvert * 5;
            Integer myPrice = Integer.valueOf((int) price);
            textPayment.setText(myPrice + " TL");
        }
        else if (s1 < i1 && s2 > i2){
            double minuteToHour = 60 - s2 ;
            s1 = s1+1;
            double hours = i1 - s1 ;
            double convert = hours * 60 + i2 + minuteToHour;
            double continueConvert = convert / 30;
            double price = continueConvert * 5;
            Integer myPrice = Integer.valueOf((int) price);
            textPayment.setText(myPrice + " TL");
        }
        else if (s1 == i1) {
            // 1 saatten az!

            double minute = s2 + i2;
            double convertMinute = minute / 30;
            double price = convertMinute * 5;
            Integer myPrice = Integer.valueOf((int) price);
            textPayment.setText(myPrice + " TL");


        } else if (s1 == 10 && i1 != 11 && i1 != 12) {

            double tenHour = 120;
            double minute = i1 * 60 + tenHour + s2 + i2;
            double convertMinute = minute / 30;
            double price = convertMinute * 5;
            Integer myPrice = Integer.valueOf((int) price);
            textPayment.setText(myPrice + " TL");

        } else if (s1 == 11 && i1 != 11) {

            double elevenHour = 60;
            double minute = i1 * 60 + elevenHour + i2 + s2;
            double convertMinute = minute / 30;
            double price = convertMinute * 5;
            Integer myPrice = Integer.valueOf((int) price);
            textPayment.setText(myPrice + " TL");

        } else if (s1 == 12 && i1 != 12) {
            double minute = i1 * 60 + s2 + i2;
            double convertMinute = minute / 30;
            double price = convertMinute * 5;
            Integer myPrice = Integer.valueOf((int) price);
            textPayment.setText(myPrice + " TL");
        }

    }

    public void addHome(View view) {

        firebaseFirestore.collection("MevcutPark").whereEqualTo("plaka", myPlate).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                    String documentsId = documentSnapshot.getId();

                    firebaseFirestore.collection("MevcutPark").document(documentsId).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(@NonNull Void aVoid) {
                            Intent intent = new Intent(PaymentActivity.this,HomeActivity.class);
                            startActivity(intent);
                            //Toast.makeText(PaymentActivity.this, "silindi eov!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}