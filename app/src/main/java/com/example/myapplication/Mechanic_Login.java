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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Mechanic_Login extends AppCompatActivity {
    EditText username,password;
    Button login;
    TextView register;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic__login);
        username=findViewById(R.id.mechanicinputEmail);
        password=findViewById(R.id.mechanicinputPassword);
        login=findViewById(R.id.mechanicbtnLogin);
        register=findViewById(R.id.mechnaicgotoRegister);
        firebaseAuth=FirebaseAuth.getInstance();
        login.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                String a = username.getText().toString();
                String b = password.getText().toString();

                firebaseAuth.signInWithEmailAndPassword(a, b).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent it = new Intent(Mechanic_Login.this,Mechanic_Page.class);
                            it.putExtra("name",a);
                            startActivity(it);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Enter valid credentials,if new user register",Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }
        });
        register.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent(Mechanic_Login.this,Mechanic_Register.class);
                startActivity(it);
            }
        });

    }
}