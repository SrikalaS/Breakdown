package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
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

public class Message extends AppCompatActivity {
    TextView username, phonenum, email, vehicleno, lattitude, longitude;
    Button send;
    private DatabaseReference mDatabaseRef,message;
    String h,m;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        username = findViewById(R.id.messageusername);
        phonenum = findViewById(R.id.messageuserphonenum);
        email = findViewById(R.id.messageuseremail);
        vehicleno = findViewById(R.id.messageuservehcileno);

        send=findViewById(R.id.send);
        username.setText(getIntent().getStringExtra("username"));
        phonenum.setText(getIntent().getStringExtra("phonenum"));
        email.setText(getIntent().getStringExtra("email"));
        vehicleno.setText(getIntent().getStringExtra("vehicleno"));
        h=getIntent().getStringExtra("srikala");
        m=getIntent().getStringExtra("problems");
        message= FirebaseDatabase.getInstance().getReference().child("message");
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("problem_details");
        message=FirebaseDatabase.getInstance().getReference().child("message");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot data : snapshot.getChildren()) {
                    String s=data.child("email").getValue().toString();

                    if(s.equals(h))
                    {

                       Log.d("problem",data.child("problem").getValue().toString());
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(Message.this, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED)
                {
                    sendMessage();
                }
                else {
                    ActivityCompat.requestPermissions(Message.this,new String[]{Manifest.permission.SEND_SMS},100);
                }
            }
        });

    }
    private void sendMessage() {
        String s=phonenum.getText().toString();
        SmsManager smsManager=SmsManager.getDefault();
        SharedPreferences sharedPreferences=getSharedPreferences("name",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("user",username.getText().toString());
        editor.putString("emailid",email.getText().toString());

        editor.putString("srikalas",h);
        editor.putString("problem",m);



        editor.putString("phone",s);
        editor.apply();


        smsManager.sendTextMessage(s,null,"request for service "+m,null,null);
        Toast.makeText(getApplicationContext(),"sent successfully",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==100 && grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            sendMessage();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"denied",Toast.LENGTH_LONG).show();
        }
    }
}