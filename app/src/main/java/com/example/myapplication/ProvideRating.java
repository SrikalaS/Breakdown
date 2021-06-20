package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.Rating;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProvideRating extends AppCompatActivity {
    RatingBar ratingBar;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provide_rating);
        ratingBar=findViewById(R.id.ratingBar);
        String s=getIntent().getStringExtra("mechanic");
        databaseReference= FirebaseDatabase.getInstance().getReference().child("mechanic");
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

                Toast.makeText(getApplicationContext(),String.valueOf(v),Toast.LENGTH_LONG).show();
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot data : snapshot.getChildren()) {
                            String a = data.child("email").getValue().toString();
                            if(s.equals(a))
                            {
                                String m=data.getKey();
                                databaseReference.child(m).child("rating").setValue(String.valueOf(ratingBar.getRating()));
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