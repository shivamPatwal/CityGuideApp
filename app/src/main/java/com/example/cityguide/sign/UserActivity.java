package com.example.cityguide.sign;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.cityguide.activity.MainActivity;
import com.example.cityguide.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class UserActivity extends AppCompatActivity {
    FirebaseStorage storage;
    FirebaseAuth mAuth;
    private ShapeableImageView userImage;

    StorageReference storageReference;
    private Button button;
    public String download=null;
    private EditText name;
    private EditText phone;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);

    userImage = findViewById(R.id.imageView5);
    button = findViewById(R.id.btnnext);
    name = findViewById(R.id.inputName);
    phone = findViewById(R.id.inputphone);
    storage = FirebaseStorage.getInstance();

        //for upload task
        storageReference = storage.getReference();
        mAuth=FirebaseAuth.getInstance();

        userImage.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            checkPermissionForImage();
        }
    });
        button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (name.getText().toString().isEmpty() ) {
                Toast.makeText(getApplicationContext(), "Please enter name", Toast.LENGTH_SHORT).show();
            }else  if (phone.getText().toString().isEmpty() ) {
                Toast.makeText(getApplicationContext(), "Please enter Phone no", Toast.LENGTH_SHORT).show();
            }else if (download == null) {
                Toast.makeText(getApplicationContext(), "Please upload image", Toast.LENGTH_SHORT).show();
            } else if(phone.getText().toString().length()<10){
                Toast.makeText(getApplicationContext(), "wrong Phone no", Toast.LENGTH_SHORT).show();

            }
            else {
                String userName = name.getText().toString();
                String mphone=phone.getText().toString();
                regiser(userName,mphone);
            }
        }
    }); }
    private void regiser(String userName,String phon) {

        firebaseUser = mAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", firebaseUser.getUid());
        hashMap.put("username", userName);
        hashMap.put("imageURL", download);
        hashMap.put("phone", phon);
Toast.makeText(getApplicationContext(),"dome",Toast.LENGTH_SHORT).show();

        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),
                            "Registration successful!",
                            Toast.LENGTH_LONG)
                            .show();
                    Intent intent = new Intent(UserActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        });
reference.setValue(hashMap).addOnFailureListener(new OnFailureListener() {
    @Override
    public void onFailure(@NonNull Exception e) {
        Log.d("error",e.getMessage());
    }
});

    }
    private void checkPermissionForImage() {
        if ( (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)      ) {


            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},
                    100);

        } else {
            pickImageFromGallary();
        }

    }

    private void pickImageFromGallary() {
        Intent gallery = new Intent(Intent.ACTION_PICK);
        gallery.setType("image/*");
        startActivityForResult(gallery,100);


    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK && requestCode==100) {

            Uri selectedImageUri = data.getData();
            if(selectedImageUri!=null){
                userImage.setImageURI(selectedImageUri);
                upLoadImage(selectedImageUri);
            }
        }

    }
    private void upLoadImage(Uri selectedImageUri) {

        StorageReference ref  = storageReference.child("uploads/"+mAuth.getUid().toString());
        UploadTask uploadTask=ref.putFile(selectedImageUri);
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {

                    download = task.getResult().toString();
                    Log.i("DOWNLOAD",download);


                } else {

                }
            }
        });


    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 100: {
                if (grantResults.length > 0
                        &&  ( grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED   )) {
                    pickImageFromGallary();

                } else {

                    Toast.makeText(getApplicationContext(), "PERMISSION DENIED", Toast.LENGTH_SHORT).show();
                }
                break;
            }


        }
    }

}


