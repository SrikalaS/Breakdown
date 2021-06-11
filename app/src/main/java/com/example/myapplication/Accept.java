package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Accept extends AppCompatActivity {

    Button accept,reject;
    SharedPreferences sharedPreferences;
    private DatabaseReference mDatabaseRef,databaseReference;
    FirebaseUser firebaseUser;
    String m,n;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept);

        accept = findViewById(R.id.accept);
        reject = findViewById(R.id.reject);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("problem_details");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("message");

        sharedPreferences = getSharedPreferences("name", MODE_PRIVATE);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        String a = sharedPreferences.getString("srikalas", null);
        String y = sharedPreferences.getString("problem", null);
        Log.d("y", y);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s=firebaseUser.getEmail().toString();
                String j=getIntent().getStringExtra("problem");
                String[] x=j.split(" :problem: ");
                String k=String.join(" ",x);
                String[] p=k.split(" ");
                Toast.makeText(getApplicationContext(),k,Toast.LENGTH_LONG).show();
                Log.d("regex",p[0]);
                Log.d("pass",p[1]);

                mDatabaseRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot data : snapshot.getChildren()) {
                            m=data.child("email").getValue().toString();
                            n=data.child("problem").getValue().toString();
                            if(p[0].equals(m) && p[1].equals(n))
                            {
                                data.getRef().removeValue();
                                send();



                            }
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void send() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            String s=firebaseUser.getEmail().toString();
            String j=getIntent().getStringExtra("problem");
            String[] x=j.split(" :problem: ");
            String k=String.join(" ",x);
            String[] p=k.split(" ");


            @Override

            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    m=data.child("username").getValue().toString();
                    n=data.child("problem").getValue().toString();
                    if(p[0].equals(m) && p[1].equals(n))
                    {
                        data.getRef().removeValue();
                        Intent it=new Intent(Accept.this,Mechanic_Page.class);
                        startActivity(it);


                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}