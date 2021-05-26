package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class Mechanic_Register extends AppCompatActivity {
    EditText username,vehicleno,phoennum,password,location,email;
    Button register,cancel;
    ImageView imageViewProfilePicture;
    private static final int pic=1;
    Uri mImageUri;
    private FirebaseAuth firebaseAuth;
    DatabaseReference mechanic;
    private StorageReference mStorageRef;
    String m=" ";
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
                                    m=task.getResult().toString();
                                    Toast.makeText(Mechanic_Register.this, m, Toast.LENGTH_SHORT).show();

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
        setContentView(R.layout.activity_mechanic__register);
        username=findViewById(R.id.mechanicregisterinputEmail);
        vehicleno=findViewById(R.id.mechanicregistervehicleno);
        phoennum=findViewById(R.id.mechanicregisterphoneno);
        password=findViewById(R.id.registerinputPassword);
        location=findViewById(R.id.mechanicregisterlocation);
        email=findViewById(R.id.mechanicregisteremail);
        register=findViewById(R.id.mechanicButton02);
        cancel=findViewById(R.id.mechanicButton03);
        mechanic= FirebaseDatabase.getInstance().getReference().child("mechanic");
        mStorageRef = FirebaseStorage.getInstance().getReference("mechanicuploads");
        firebaseAuth=FirebaseAuth.getInstance();
        imageViewProfilePicture=findViewById(R.id.registerimageViewMainActivityProfilePicture);
        imageViewProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery=new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery,"select picture"),pic);
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

                Mechanic_Detials x=new Mechanic_Detials(a,d,e,c,b,m,f);
                mechanic.push().setValue(x);
                firebaseAuth.createUserWithEmailAndPassword(f,d).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(getApplicationContext(),"Registered Success",Toast.LENGTH_LONG).show();

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

    }
}