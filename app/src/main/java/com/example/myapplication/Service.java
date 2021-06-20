package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Service extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        listView=findViewById(R.id.listserv);
        ArrayList<String> s=new ArrayList<>();
        s.add("When Puncture occurs");
        s.add("when battery downs");
        s.add("when engine problem occurs");
        ArrayAdapter arrayAdapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1,s);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String k=s.get(i).toString();
                if(k.equals("When Puncture occurs"))
                {
                    Intent it=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=xZ8HqKPrRXY"));
                    startActivity(it);
                }
                else if(k.equals("when battery downs"))
                {
                    Intent it=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=xZ8HqKPrRXY"));
                    startActivity(it);
                }

            }
        });

    }
}