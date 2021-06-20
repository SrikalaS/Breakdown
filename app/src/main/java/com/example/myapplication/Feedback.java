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

public class Feedback extends AppCompatActivity {
    DatabaseReference databaseReference;
    ListView listView;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        listView=findViewById(R.id.listfeedabck);
        ArrayList<String> s=new ArrayList<>();
        ArrayAdapter arrayAdapter;
        arrayAdapter=new ArrayAdapter<String>(Feedback.this, android.R.layout.simple_list_item_1,s);
        listView.setAdapter(arrayAdapter);
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("message");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String b=firebaseUser.getEmail().toString();
                for (DataSnapshot data : snapshot.getChildren()) {
                    if(b.equals(data.child("username").getValue().toString()))
                    {
                        s.add(data.child("mechanicname").getValue().toString()+" problem "+data.child("problem").getValue().toString()+" "+data.child("date").getValue().toString());
                        arrayAdapter.notifyDataSetChanged();
                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                            String a = data.child("mechanicname").getValue().toString();
                            String b = data.child("problem").getValue().toString();
                            if(p[0].equals(a) && p[1].equals(b)) {
                                Intent it=new Intent(Feedback.this,ProvideRating.class);
                                it.putExtra("mechanic",p[0]);
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