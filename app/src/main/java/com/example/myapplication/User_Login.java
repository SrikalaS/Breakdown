package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class User_Login extends AppCompatActivity {
    EditText username,password;
    Button login;
    TextView register;
    private FirebaseAuth auth;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__login);
        username=findViewById(R.id.inputEmail);
        password=findViewById(R.id.inputPassword);
        login=findViewById(R.id.btnLogin);
        register=findViewById(R.id.usergotoRegister);
        auth = FirebaseAuth.getInstance();


        login.setOnClickListener(new android.view.View.OnClickListener() {

            @Override

            public void onClick(android.view.View view) {
                String a = username.getText().toString();
                String b = password.getText().toString();

                auth.signInWithEmailAndPassword(a, b).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent it = new Intent(User_Login.this, Srikala.class);
                            startActivity(it);
                        }

                    }
                });
            }

        });
        register.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent(User_Login.this,User_Register.class);
                startActivity(it);
            }
        });
    }
}