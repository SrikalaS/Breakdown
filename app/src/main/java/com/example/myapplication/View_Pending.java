package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class View_Pending extends AppCompatActivity {
ListView listview;
    private DatabaseReference mDatabaseRef,databaseReference;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__pending);
        listview=findViewById(R.id.pendinglist);
        ArrayList<String> s=new ArrayList<>();
        ArrayAdapter<String> arrayAdapter;
        arrayAdapter=new ArrayAdapter<String>(View_Pending.this, android.R.layout.simple_list_item_1,s);
        listview.setAdapter(arrayAdapter);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("reject_details");
        databaseReference=FirebaseDatabase.getInstance().getReference().child("problem_details");
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String b=firebaseUser.getEmail().toString();
                for (DataSnapshot data : snapshot.getChildren()) {
                    if(b.equals(data.child("emailId").getValue().toString()))
                    {
                        s.add(data.child("email").getValue().toString()+" problem "+data.child("problem").getValue().toString());
                        arrayAdapter.notifyDataSetChanged();
                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String m=s.get(i).toString();
                String[] x=m.split(" problem ");
                String k=String.join(" ",x);
                String[] p=k.split(" ");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot data : snapshot.getChildren()) {
                            String a = data.child("email").getValue().toString();
                            String b = data.child("problem").getValue().toString();
                            if(p[0].equals(a) && p[1].equals(b))
                            {
                                Intent it=new Intent(View_Pending.this,Pending_Profile.class);
                                it.putExtra("username",data.child("username").getValue().toString());
                                it.putExtra("email",data.child("email").getValue().toString());
                                it.putExtra("phonenum",data.child("phonenum").getValue().toString());
                                it.putExtra("problem",data.child("problem").getValue().toString());
                                it.putExtra("latitude",data.child("lattitude").getValue().toString());
                                it.putExtra("longitude",data.child("longitude").getValue().toString());
                                it.putExtra("vehicleno",data.child("vehicleno").getValue().toString());
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