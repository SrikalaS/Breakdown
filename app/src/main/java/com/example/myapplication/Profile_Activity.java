package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.Locale;

public class Profile_Activity extends AppCompatActivity {
    ;TextView username, phonenum, email, problem, vehicleno, lattitude, longitude;
    private DatabaseReference mDatabaseRef,databaseReference,database;
    Button track,accept,reject;
    String a,b;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_);
        username = findViewById(R.id.profileusername);
        phonenum = findViewById(R.id.profileuserphonenum);
        email = findViewById(R.id.profileuseremail);
        problem = findViewById(R.id.profileuserproblem);
        vehicleno = findViewById(R.id.profileuservehcileno);
        accept=findViewById(R.id.mechaccept);
        reject=findViewById(R.id.mechreject);
        lattitude = findViewById(R.id.profileuserlatitute);
        longitude = findViewById(R.id.profileuserlongitude);
        track=findViewById(R.id.tarck);
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();


        username.setText(getIntent().getStringExtra("username"));
        phonenum.setText(getIntent().getStringExtra("phonenum"));
        email.setText(getIntent().getStringExtra("email"));
        problem.setText(getIntent().getStringExtra("problem"));
        vehicleno.setText(getIntent().getStringExtra("vehicleno"));
        lattitude.setText(getIntent().getStringExtra("latitude"));
        longitude.setText(getIntent().getStringExtra("longitude"));
        a=email.getText().toString();
        b=problem.getText().toString();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("problem_details");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("reject_details");
        database=FirebaseDatabase.getInstance().getReference().child("message");


        accept.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                track.setVisibility(View.VISIBLE);
                SmsManager smsManager=SmsManager.getDefault();
                smsManager.sendTextMessage(phonenum.getText().toString(),null,"accepted for service ",null,null);
                Toast.makeText(getApplicationContext(),"sent successfully",Toast.LENGTH_LONG).show();
                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
               Message_Details x=new Message_Details(a,b,firebaseUser.getEmail().toString(),date);
               database.push().setValue(x);
                mDatabaseRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot data : snapshot.getChildren()) {
                            String m=data.child("email").getValue().toString();
                            String n=data.child("problem").getValue().toString();
                            if(a.equals(m) && b.equals(n))
                            {
                                data.getRef().removeValue();




                            }
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String q=username.getText().toString();
                String w=email.getText().toString();
                String e=phonenum.getText().toString();
                String r=vehicleno.getText().toString();
                String t=problem.getText().toString();
                String i=lattitude.getText().toString();
                String o=longitude.getText().toString();
                String k=firebaseUser.getEmail().toString();
                Reject_details h=new Reject_details(q,e,w,r,t,i,o,k);
                databaseReference.push().setValue(h);
                SmsManager smsManager=SmsManager.getDefault();
                smsManager.sendTextMessage(phonenum.getText().toString(),null,"accepted for service ",null,null);
                Toast.makeText(getApplicationContext(),"sent successfully",Toast.LENGTH_LONG).show();
            }
        });
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