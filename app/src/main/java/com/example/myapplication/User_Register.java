package com.example.myapplication;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

public class User_Register extends AppCompatActivity {
    EditText username,vehicleno,phoennum,password,email;
    TextView latitude,longitude;
    Spinner spin;
    String vehicletype;
    Button register,cancel,location;
    ImageView imageViewProfilePicture;
    private static final int pic=1;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference user;
    private StorageTask mUploadTask;
    private ProgressBar mProgressBar;
    private FirebaseAuth firebaseAuth;
    FusedLocationProviderClient fusedLocationProviderClient;
    String x,y;

    String k=" ";
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == pic && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(imageViewProfilePicture);
            uploadFile();
        }
        }


    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        if (mImageUri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));
            fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // profileImageUrl taskSnapshot.getDownloadUrl().toString(); //this is depreciated

                            //this is the new way to do it
                            fileReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    k=task.getResult().toString();
                                    Toast.makeText(User_Register.this, k, Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {


                        }
                    });

        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__register);
        username = findViewById(R.id.registerinputEmail);
        vehicleno = findViewById(R.id.registervehicleno);
        phoennum = findViewById(R.id.phoneno);
        password = findViewById(R.id.registerinputPassword);
        location = findViewById(R.id.userregisterlocation);
        latitude=findViewById(R.id.registerlatitude);
        longitude=findViewById(R.id.registerlongitude);
        spin = findViewById(R.id.spinner1);
        register = findViewById(R.id.Button02);
        cancel = findViewById(R.id.Button03);
        email=findViewById(R.id.registeremail);
        imageViewProfilePicture=findViewById(R.id.imageViewMainActivityProfilePicture);
        user= FirebaseDatabase.getInstance().getReference().child("user");
        firebaseAuth=FirebaseAuth.getInstance();


        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");


        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                vehicletype = spin.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a=username.getText().toString();
                String b=vehicleno.getText().toString();
                String c=phoennum.getText().toString();
                String d=password.getText().toString();
                String e=location.getText().toString();
                String f=email.getText().toString();
                firebaseAuth.fetchSignInMethodsForEmail(f).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        boolean check=!task.getResult().getSignInMethods().isEmpty();
                        if(!check)
                        {
                            User_details p=new User_details(a,d,e,c,vehicletype,b,k,f,x,y);
                            user.push().setValue(p);
                            firebaseAuth.createUserWithEmailAndPassword(f,d).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Toast.makeText(getApplicationContext(),"Registered success",Toast.LENGTH_LONG).show();
                                }
                            });

                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Already registered",Toast.LENGTH_LONG).show();
                        }

                    }
                });






            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username.setText(" ");
                vehicleno.setText(" ");
                phoennum.setText(" ");
                password.setText(" ");
                location.setText(" ");
                email.setText(" ");


            }
        });
       imageViewProfilePicture.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent gallery=new Intent();
               gallery.setType("image/*");
               gallery.setAction(Intent.ACTION_GET_CONTENT);
               startActivityForResult(Intent.createChooser(gallery,"select picture"),pic);
           }
       });
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(User_Register.this);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(User_Register.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(User_Register.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getCurrentLocation();
                } else {
                    ActivityCompat.requestPermissions(User_Register.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
                }
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100 && grantResults.length > 0 && (grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
            getCurrentLocation();
        } else {
            Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_LONG).show();
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
                        x = String.valueOf(location.getLatitude());
                        y= String.valueOf(location.getLongitude());
                        latitude.setText(String.valueOf(location.getLatitude()));
                        longitude.setText(String.valueOf(location.getLongitude()));

                    } else {
                        LocationRequest locationRequest = new LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setInterval(10000).setFastestInterval(1000).setNumUpdates(1);
                        LocationCallback locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                Location location1 = locationResult.getLastLocation();
                                x = String.valueOf(location.getLatitude());
                                y= String.valueOf(location.getLongitude());
                                latitude.setText(String.valueOf(location1.getLatitude()));
                                longitude.setText(String.valueOf(location1.getLongitude()));
                            }
                        };
                        if (ActivityCompat.checkSelfPermission(User_Register.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(User_Register.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
        } else {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }





}
