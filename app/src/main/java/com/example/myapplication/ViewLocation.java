package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewLocation extends AppCompatActivity {
    TextView username, phonenum, email, problem, vehicleno, lattitude, longitude;
    Button getlocation,addrequest;
    String a,b;
    private DatabaseReference problemdetails;
    FusedLocationProviderClient fusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        username = findViewById(R.id.textusername);
        phonenum = findViewById(R.id.textphonenum);
        email = findViewById(R.id.textemail);
        problem = findViewById(R.id.textproblem);
        vehicleno = findViewById(R.id.textvehicleno);
        addrequest=findViewById(R.id.addrequestbtn);
        lattitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);
        getlocation = findViewById(R.id.getlocation);
        username.setText(getIntent().getStringExtra("username"));
        phonenum.setText(getIntent().getStringExtra("phonenum"));
        email.setText(getIntent().getStringExtra("email"));
        problem.setText(getIntent().getStringExtra("problem"));
        vehicleno.setText(getIntent().getStringExtra("vehicleno"));
        problemdetails= FirebaseDatabase.getInstance().getReference().child("problem_details");
        addrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String x=username.getText().toString();
                String y=vehicleno.getText().toString();
                String c=phonenum.getText().toString();
                String d=problem.getText().toString();
                String e=email.getText().toString();
                Problem_Details k=new Problem_Details(x,c,e,d,y,a,b);
                problemdetails.push().setValue(k);

            }
        });
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(ViewLocation.this);
        getlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(ViewLocation.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ViewLocation.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getCurrentLocation();
                }
                else {
                    ActivityCompat.requestPermissions(ViewLocation.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},100);
                }
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==100 && grantResults.length>0 && (grantResults[0]+grantResults[1]==PackageManager.PERMISSION_GRANTED))
        {
            getCurrentLocation();
        }
        else {
            Toast.makeText(getApplicationContext(),"Permission denied",Toast.LENGTH_LONG).show();
        }
    }

    private void getCurrentLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location != null) {
                        a=String.valueOf(location.getLatitude());
                        b=String.valueOf(location.getLongitude());
                        lattitude.setText(String.valueOf(location.getLatitude()));
                        longitude.setText(String.valueOf(location.getLongitude()));
                    } else {
                        LocationRequest locationRequest = new LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setInterval(10000).setFastestInterval(1000).setNumUpdates(1);
                        LocationCallback locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                Location location1 = locationResult.getLastLocation();
                                a=String.valueOf(location.getLatitude());
                                b=String.valueOf(location.getLongitude());
                                lattitude.setText(String.valueOf(location1.getLatitude()));
                                longitude.setText(String.valueOf(location1.getLongitude()));
                            }
                        };
                        if (ActivityCompat.checkSelfPermission(ViewLocation.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ViewLocation.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                    }
                }
            });
        }
        else
        {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }


}