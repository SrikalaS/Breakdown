package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Profile_Activity extends AppCompatActivity {
    TextView username, phonenum, email, problem, vehicleno, lattitude, longitude;
    Button track;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_);
        username = findViewById(R.id.profileusername);
        phonenum = findViewById(R.id.profileuserphonenum);
        email = findViewById(R.id.profileuseremail);
        problem = findViewById(R.id.profileuserproblem);
        vehicleno = findViewById(R.id.profileuservehcileno);

        lattitude = findViewById(R.id.profileuserlatitute);
        longitude = findViewById(R.id.profileuserlongitude);
        track=findViewById(R.id.tarck);

        username.setText(getIntent().getStringExtra("username"));
        phonenum.setText(getIntent().getStringExtra("phonenum"));
        email.setText(getIntent().getStringExtra("email"));
        problem.setText(getIntent().getStringExtra("problem"));
        vehicleno.setText(getIntent().getStringExtra("vehicleno"));
        lattitude.setText(getIntent().getStringExtra("latitude"));
        longitude.setText(getIntent().getStringExtra("longitude"));
        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double x=Double.parseDouble(lattitude.getText().toString());
                Double y=Double.parseDouble(longitude.getText().toString());

                Intent it=new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=x,y&mode=l"));
                it.setPackage("com.google.android.apps.maps");
                startActivity(it);
            }
        });


    }
}