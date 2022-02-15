package com.example.vakansia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private EditText login_Email;
    private TextInputEditText loginPass;
    public String UID_ID_FIER;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null){

                }else {

                }
            }
        };

    }

    private void init(){
        mAuth = FirebaseAuth.getInstance();
        login_Email = (EditText) findViewById(R.id.login_et_email);
        loginPass = (TextInputEditText) findViewById(R.id.login_et_password);
       /* FirebaseUser user = mAuth.getCurrentUser();
        if (user != null){
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
        }*/
    }

    public void signin (String email, String password){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(LoginActivity.this, "Не верный E-mail или пароль", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onClickSign(View view){
        String Time_email = login_Email.getText().toString();
        String Time_pass = Objects.requireNonNull(loginPass.getText()).toString();
        if (!TextUtils.isEmpty(Time_email)) {
            if (!TextUtils.isEmpty(Time_pass)) signin(Time_email, Time_pass);
            else Toast.makeText(LoginActivity.this, "Введите поля", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(LoginActivity.this, "Введите поля", Toast.LENGTH_SHORT).show();
    }

    public void onClickRegistr(View view){
        startActivity(new Intent(LoginActivity.this, RegistrActivity.class));
    }

}