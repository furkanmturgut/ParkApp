package com.kunai.parkapp.login;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.kunai.parkapp.HomeActivity;
import com.kunai.parkapp.R;

public class LoginActivity extends AppCompatActivity {

    EditText editTextMail,editTextPass;
    FirebaseAuth firebaseAuth;
    Boolean mailControl;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setStatusBarColor(getResources().getColor(R.color.colorGreen));

        init();

    }

    public void init(){
        editTextMail = findViewById(R.id.editTextMail);
        editTextPass = findViewById(R.id.editTextPass);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void toHomeActivity(View view) {

        String mail = editTextMail.getText().toString();
        String pass = editTextPass.getText().toString();

        mailControl = mail.contains("@");
        mailControl = mail.contains(".com");


        if (TextUtils.isEmpty(mail) || TextUtils.isEmpty(pass)){
            Snackbar snackbar = Snackbar.make(view,"Tüm alanları doldurunuz.", BaseTransientBottomBar.LENGTH_LONG);
            snackbar.show();
        }else if(pass.length() < 6){
            Snackbar snackbar = Snackbar.make(view,"En az 6 karakter şifrenizi unutmayın.",BaseTransientBottomBar.LENGTH_LONG);
            snackbar.show();
        }else if(mailControl != true){
            Snackbar snackbar = Snackbar.make(view,"Geçerli bir mail adresi giriniz.",BaseTransientBottomBar.LENGTH_LONG);
            snackbar.show();
        }else{
            firebaseAuth.signInWithEmailAndPassword(mail,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(@NonNull AuthResult authResult) {
                    Log.e("Park Login","Login Succes!");
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(LoginActivity.this, "İletişime Geç", Toast.LENGTH_SHORT).show();
                    Log.e("Error Login",e.getLocalizedMessage());
                }
            });

        }


    }
}