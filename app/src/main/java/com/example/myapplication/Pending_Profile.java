package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
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

public class Pending_Profile extends AppCompatActivity {
    TextView username, phonenum, email, problem, vehicleno, lattitude, longitude;
    private DatabaseReference mDatabaseRef,databaseReference;
    Button track,accept,reject;
    String a,b;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending__profile);
        username = findViewById(R.id.pendingprofileusername);
        phonenum = findViewById(R.id.pendingprofileuserphonenum);
        email = findViewById(R.id.pendingprofileuseremail);
        problem = findViewById(R.id.pendingprofileuserproblem);
        vehicleno = findViewById(R.id.pendingprofileuservehcileno);
        accept=findViewById(R.id.pendingaccept);
        reject=findViewById(R.id.pendingreject);
        lattitude = findViewById(R.id.pendingprofileuserlatitute);
        longitude = findViewById(R.id.pendingprofileuserlongitude);
        track=findViewById(R.id.pendingtarck);
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
        databaseReference = FirebaseDatabase.getInstance().getReference().child("reject_details");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("problem_details");
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                track.setVisibility(View.VISIBLE);
                SmsManager smsManager=SmsManager.getDefault();
                smsManager.sendTextMessage(phonenum.getText().toString(),null,"accepted for service ",null,null);
                Toast.makeText(getApplicationContext(),"sent successfully",Toast.LENGTH_LONG).show();
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
                databaseReference.addValueEventListener(new ValueEventListener() {
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