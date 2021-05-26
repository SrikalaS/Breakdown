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
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
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
    EditText username,vehicleno,phoennum,password,location,email;
    Spinner spin;
    String vehicletype;
    Button register,cancel;
    ImageView imageViewProfilePicture;
    private static final int pic=1;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference user;
    private StorageTask mUploadTask;
    private ProgressBar mProgressBar;
    private FirebaseAuth firebaseAuth;

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
        location = findViewById(R.id.location);
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


                User_details x=new User_details(a,d,e,c,vehicletype,b,k,f);
                user.push().setValue(x);
                firebaseAuth.createUserWithEmailAndPassword(f,d).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(getApplicationContext(),"Registered success",Toast.LENGTH_LONG).show();
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


    }
}