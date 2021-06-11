package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class View_Mechanic extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ViewMechanicAdapter mAdapter;
    private ProgressBar mProgressCircle;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference databaseReference;
    private List<User_details> mUploads;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__mechanic);
        mRecyclerView = findViewById(R.id.recycler_view1);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressCircle = findViewById(R.id.progress_circle1);
        mUploads = new ArrayList<>();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("user");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    User_details upload = postSnapshot.getValue(User_details.class);
                    mUploads.add(upload);
                }
                mAdapter = new ViewMechanicAdapter(View_Mechanic.this, mUploads);
                mRecyclerView.setAdapter(mAdapter);
                mProgressCircle.setVisibility(View.INVISIBLE);
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(View_Mechanic.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
    }
}