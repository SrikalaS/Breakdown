package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.acos;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class ViewRequest extends AppCompatActivity {

    private DatabaseReference mDatabaseRef;
    private DatabaseReference databaseReference, databaseReference2;
    double a, b;
    ListView listView;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_request);
        listView=findViewById(R.id.list);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("problem_details");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("mechanic");
        arrayList=new ArrayList<String>();
        arrayAdapter=new ArrayAdapter<String>(ViewRequest.this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);


        String k = getIntent().getStringExtra("username");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot data : snapshot.getChildren()) {
                    String s = data.child("email").getValue().toString();
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                    if (s.equals(k)) {
                        a = Double.parseDouble(data.child("latitude").getValue().toString());
                        b = Double.parseDouble(data.child("longitude").getValue().toString());

                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewRequest.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    double p = Double.parseDouble(data.child("lattitude").getValue().toString());
                    double q = Double.parseDouble(data.child("longitude").getValue().toString());
                    double m = getKilometers(a, b, p, q);
                    Log.d("longitude", String.valueOf(m));

                    if(m<10)
                    {
                        String h=data.child("username").getValue().toString();
                        arrayList.add(h+" problem: "+data.child("problem").getValue().toString());
                        arrayAdapter.notifyDataSetChanged();
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent it=new Intent(ViewRequest.this,Profile_Activity.class);
                                it.putExtra("username",data.child("username").getValue().toString());
                                it.putExtra("email",data.child("email").getValue().toString());
                                it.putExtra("phonenum",data.child("phonenum").getValue().toString());
                                it.putExtra("problem",data.child("problem").getValue().toString());
                                it.putExtra("latitude",data.child("lattitude").getValue().toString());
                                it.putExtra("longitude",data.child("longitude").getValue().toString());
                                it.putExtra("vehicleno",data.child("vehicleno").getValue().toString());
                                startActivity(it);
                            }
                        });

                    }

                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewRequest.this, error.getMessage(), Toast.LENGTH_SHORT).show();

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

        return dist/1000;
    }
}