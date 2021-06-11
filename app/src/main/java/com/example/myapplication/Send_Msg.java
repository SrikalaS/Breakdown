package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Send_Msg extends AppCompatActivity {
    private DatabaseReference mDatabaseRef;
    private DatabaseReference databaseReference;
    double a, b;
    ListView listView;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send__msg);
        listView = findViewById(R.id.list1);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("mechanic");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("user");
        arrayList = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter<String>(Send_Msg.this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);





        String k = getIntent().getStringExtra("view");
        String t=getIntent().getStringExtra("problem");
       

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot data : snapshot.getChildren()) {
                    String s = data.child("email").getValue().toString();

                    if (s.equals(k)) {
                        a = Double.parseDouble(data.child("latitude").getValue().toString());
                        b = Double.parseDouble(data.child("longitude").getValue().toString());

                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Send_Msg.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    double p = Double.parseDouble(data.child("latitude").getValue().toString());
                    double q = Double.parseDouble(data.child("longitude").getValue().toString());
                    double m = getKilometers(a, b, p, q);
                    Log.d("longitude", String.valueOf(m));

                    if (m < 10) {
                        String h = data.child("username").getValue().toString();
                        arrayList.add(h+" distance: "+m);
                        arrayAdapter.notifyDataSetChanged();
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                String[] x=arrayList.get(i).split(" distance: ");
                               Log.d("mech",x[0]);
                               mDatabaseRef.addValueEventListener(new ValueEventListener() {
                                   @Override
                                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                                       for (DataSnapshot data : snapshot.getChildren()) {
                                           String w=data.child("username").getValue().toString();
                                           if(x[0].equals(w))
                                           {
                                               Intent it=new Intent(Send_Msg.this,Message.class);

                                               it.putExtra("username",data.child("username").getValue().toString());
                                               it.putExtra("email",data.child("email").getValue().toString());
                                               it.putExtra("phonenum",data.child("phonenum").getValue().toString());
                                               it.putExtra("srikala",k);
                                               it.putExtra("problems",t);



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
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Send_Msg.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    public double getKilometers(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = (double) (earthRadius * c);

        return dist / 1000;
    }

}

