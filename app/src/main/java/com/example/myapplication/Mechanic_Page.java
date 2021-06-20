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

public class Mechanic_Page extends AppCompatActivity {
ListView listView;
String a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic__page);
        listView=findViewById(R.id.listview1);
        ArrayList<String> s=new ArrayList<>();
        s.add("Search for user");
        s.add("View Requests");
        s.add("View Other Mechanic Details");
        s.add("View Pending requests");

        ArrayAdapter arrayAdapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1,s);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(Mechanic_Page.this,s.get(i).toString(),Toast.LENGTH_LONG).show();
                String k=s.get(i).toString();
                if(k.equals("View Requests"))
                {
                    Intent it=new Intent(Mechanic_Page.this,ViewRequest.class);
                    a=getIntent().getStringExtra("name");
                    it.putExtra("username",a);
                    startActivity(it);
                }
                else if(k.equals("View Other Mechanic Details"))
                {
                    Intent it=new Intent(Mechanic_Page.this,MainPage.class);
                    startActivity(it);
                }
                else if(k.equals("View Pending requests"))
                {
                    Intent it=new Intent(Mechanic_Page.this,View_Pending.class);
                    startActivity(it);
                }


            }
        });
    }
}