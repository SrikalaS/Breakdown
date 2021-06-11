package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Message_Request extends AppCompatActivity{
    SharedPreferences sharedPreferences;
    ListView listView;
    String s;
    RadioGroup radioGroup;
    RadioButton radioButton1,radioButton2;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    private DatabaseReference message;
    FirebaseUser firebaseUser;
    String m,f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message__request);
        listView=findViewById(R.id.msg);
        arrayList=new ArrayList<String>();
        arrayAdapter=new ArrayAdapter<String>(Message_Request.this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);
        message=FirebaseDatabase.getInstance().getReference().child("message");
        sharedPreferences=getSharedPreferences("name",MODE_PRIVATE);
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        Toast.makeText(getApplicationContext(),firebaseUser.getEmail().toString(),Toast.LENGTH_LONG).show();
        Log.d("currentuser",firebaseUser.getEmail().toString());

        String a=sharedPreferences.getString("user",null);
        String j=sharedPreferences.getString("emailid",null);
        String k=sharedPreferences.getString("phone",null);
        String p=sharedPreferences.getString("srikalas",null);
        String e=sharedPreferences.getString("problem",null);


        s=getIntent().getStringExtra("username");
        Log.d("mail",j);
        Log.d("njh",s);
            message.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        String r = data.child("mechanicname").getValue().toString();
                        if(firebaseUser.getEmail().toString().equals(r) && r!=null) {
                             m = data.child("problem").getValue().toString();
                            f=data.child("username").getValue().toString();
                            arrayList.add(f + " :problem: " + m);
                            arrayAdapter.notifyDataSetChanged();
                        }

                    }



                    }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });



            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String u=arrayList.get(i).toString();




                    Intent it=new Intent(Message_Request.this,Accept.class);
                    it.putExtra("username",m);
                    it.putExtra("problem",u);
                    startActivity(it);


                }
            });




    }
}