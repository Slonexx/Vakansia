package com.example.vakansia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class RegistrActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDataBase;
    private String USER_KEY = "User";
    private EditText new_login, new_email;
    private TextInputEditText new_pass;
    private String new_oblast;

    private String[] oblast = {"АКМОЛИНСКАЯ ОБЛАСТЬ", "АКТЮБИНСКАЯ ОБЛАСТЬ", "АЛМАТИНСКАЯ ОБЛАСТЬ",
            "АТЫРАУСКАЯ ОБЛАСТЬ",  "ВОСТОЧНО-КАЗАХСТАНСКАЯ ОБЛАСТЬ", "ЖАМБЫЛСКАЯ ОБЛАСТЬ", "ЗАПАДНО-КАЗАХСТАНСКАЯ ОБЛАСТЬ",
            "КАРАГАНДИНСКАЯ ОБЛАСТЬ", "КОСТАНАЙСКАЯ ОБЛАСТЬ", "КЫЗЫЛОРДИНСКАЯ ОБЛАСТЬ", "МАНГИСТАУСКАЯ ОБЛАСТЬ",
            "ПАВЛОДАРСКАЯ ОБЛАСТЬ","СЕВЕРО-КАЗАХСТАНСКАЯ ОБЛАСТЬ", "ТУРКЕСТАНСКАЯ ОБЛАСТЬ"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registr);

        init();

        ArrayAdapter<String> oblastAdapter = new ArrayAdapter<String>(this,
                                            android.R.layout.simple_spinner_item, oblast);

        oblastAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spOblast = (Spinner) findViewById(R.id.new_sp_oblast);
        spOblast.setAdapter(oblastAdapter);
        spOblast.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                new_oblast = adapterView.getItemAtPosition(i).toString();
                // new_oblast = ;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void init(){
        mAuth = FirebaseAuth.getInstance();
        new_login = (EditText) findViewById(R.id.new_et_login);
        new_email = (EditText) findViewById(R.id.new_et_email);
        new_pass = (TextInputEditText) findViewById(R.id.new_et_password);
        mDataBase = FirebaseDatabase.getInstance().getReference(USER_KEY);
    }

    public void onClickSaveBD(View view){

        String Login = new_login.getText().toString();
        String Email = new_email.getText().toString();
        String Oblast = new_oblast;
        String Pass = new_pass.getText().toString();

        if (TextUtils.isEmpty(Login)) Toast.makeText(RegistrActivity.this, "Введите Поля", Toast.LENGTH_SHORT).show();
        if (TextUtils.isEmpty(Email)) Toast.makeText(RegistrActivity.this, "Введите Поля", Toast.LENGTH_SHORT).show();
        if (TextUtils.isEmpty(Pass)) Toast.makeText(RegistrActivity.this, "Введите Поля", Toast.LENGTH_SHORT).show();


        if ((!TextUtils.isEmpty(Login)) && (!TextUtils.isEmpty(Email)) && (!TextUtils.isEmpty(Pass)))
        {
            registration(Email, Pass, Login, Oblast);

            Toast.makeText(RegistrActivity.this, "Сохранение", Toast.LENGTH_SHORT).show();
        }
        else Toast.makeText(RegistrActivity.this, "Введите Поля", Toast.LENGTH_SHORT).show();
    }


    public void registration (String email, String password, String Login, String Oblast){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser rUser = FirebaseAuth.getInstance().getCurrentUser();
                    String userId = rUser.getUid();
                    mDataBase = FirebaseDatabase.getInstance().getReference(USER_KEY).child(userId);
                    HashMap<String, String> hashMap = new HashMap<>();
                   // hashMap.put("userId", userId);
                    hashMap.put("Login", Login);
                    hashMap.put("Email", email);
                    hashMap.put("Oblast", Oblast);
                    hashMap.put("Pass", password);
                    mDataBase.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });

                } else
                    Toast.makeText(RegistrActivity.this, "Регистрация провалена", Toast.LENGTH_SHORT).show();
            }
        });
    }

}