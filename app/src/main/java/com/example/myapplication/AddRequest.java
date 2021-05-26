package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddRequest extends AppCompatActivity
{
    EditText vehicleno,problemno;
    Button add,proceed;
    TextView text1,text2;

    private DatabaseReference mDatabaseRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_request);
        vehicleno=findViewById(R.id.addrequest);
        add=findViewById(R.id.add);

        problemno=findViewById(R.id.problem);

        add.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {

                mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("user");
                mDatabaseRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot data : snapshot.getChildren()) {
                            String s=data.child("vehicleno").getValue().toString();
                            if(s.equals(vehicleno.getText().toString()))
                            {
                                String a=data.child("username").getValue().toString();
                                String b=data.child("phonenum").getValue().toString();
                                String c=data.child("email").getValue().toString();
                                Intent it=new Intent(AddRequest.this, ViewLocation.class);
                                it.putExtra("username",a);
                                it.putExtra("phonenum",b);
                                it.putExtra("email",c);
                                it.putExtra("vehicleno",s);
                                it.putExtra("problem",problemno.getText().toString());
                                startActivity(it);
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
}