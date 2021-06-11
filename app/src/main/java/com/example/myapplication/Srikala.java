package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Srikala extends AppCompatActivity {
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_srikala);
        listView=findViewById(R.id.listview);
        ArrayList<String> s=new ArrayList<>();
        s.add("Add Requests");
        s.add("View mechanic Details");
        s.add("Service  Provider Manual");
        ArrayAdapter arrayAdapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1,s);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(Srikala.this,s.get(i).toString(),Toast.LENGTH_LONG).show();
                String k=s.get(i).toString();
                if(k.equals("Add Requests"))
                {
                    Intent it=new Intent(Srikala.this,AddRequest.class);
                    String x=getIntent().getStringExtra("user");
                    it.putExtra("viewUser",x);
                    startActivity(it);
                }

                else if(k.equals("View mechanic Details"))
                {
                    Intent it=new Intent(Srikala.this,MainPage.class);
                    startActivity(it);
                }
                else if(k.equals("Service  Provider Manual"))
                {
                    Intent it=new Intent(Srikala.this,Service.class);
                    startActivity(it);
                }
            }
        });
    }
}