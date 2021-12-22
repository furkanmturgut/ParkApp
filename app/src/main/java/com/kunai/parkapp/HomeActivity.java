package com.kunai.parkapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.kunai.parkapp.fragment.AddParkingFragment;
import com.kunai.parkapp.fragment.ParkingListFragment;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    FirebaseAuth firebaseAuth;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getWindow().setStatusBarColor(getResources().getColor(R.color.colorGreen));


        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        replace(new ParkingListFragment());
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.parkList:
                        replace(new ParkingListFragment());
                        break;
                    case R.id.addPark:
                        replace(new AddParkingFragment());
                        break;

                    case R.id.appClose:
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeActivity.this);
                        alertDialog.setTitle("Turgut Otopark");
                        alertDialog.setMessage("Uygulamadan Çıkış Yapmak İstiyor Musun ?");
                        alertDialog.setNegativeButton("Hayır",null);
                        alertDialog.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                firebaseAuth.signOut();
                                HomeActivity.this.finish();
                                System.exit(0);

                            }
                        });
                        alertDialog.show();

                        break;
                }

                return true;
            }
        });


    }

    private void replace(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }
}
