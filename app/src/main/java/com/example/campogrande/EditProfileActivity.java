package com.example.campogrande;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rengwuxian.materialedittext.MaterialEditText;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {

    private String uUserId, uName, uAge, uCity, uIntro, uDownloadProfImageUrl;
    private FloatingActionButton updateProfile;
    private static final int GalleryPick = 1;
    private Uri uriProfilePic;
    private MaterialEditText mName, mCity,mAge,mIntroduction;
    private StorageReference profilePictureRef;
    private DatabaseReference rootRef;

    private static final String TAG = "AddToDB";

    FirebaseDatabase mFirebaseDatabase;
    CircleImageView profilepic;
    private static final int CHOOSE_IMAGE =101;
    String profilePicUrl;
    FirebaseAuth mAuth;
    FirebaseUser user;
    String userid;

    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        profilePictureRef = FirebaseStorage.getInstance().getReference().child("Profile pics");
        rootRef = FirebaseDatabase.getInstance().getReference().child("Users");


        profilepic = findViewById(R.id.image_profile);
        mName = findViewById(R.id.fullname);
        mCity = findViewById(R.id.city);
        mAge = findViewById(R.id.age);
        mIntroduction=findViewById(R.id.profiletext);

        mAuth=FirebaseAuth.getInstance();
        user= mAuth.getCurrentUser();
        userid=user.getUid();


        ImageView changePhoto = findViewById(R.id.photochange);
        updateProfile = findViewById(R.id.fab);


        changePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhoto();
            }
        });

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkInformation();
            }
        });

    }


    private void choosePhoto() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GalleryPick && resultCode==RESULT_OK && data!=null)
        {
            uriProfilePic = data.getData();
            profilepic.setImageURI(uriProfilePic);
        }
    }



    private void checkInformation() {
        uName = mName.getText().toString();
        uAge = mAge.getText().toString();
        uCity = mCity.getText().toString();
        uIntro = mIntroduction.getText().toString();

        user = mAuth.getCurrentUser();
        if(user!=null) {


            if (uriProfilePic == null) {
                Toast.makeText(this, "Please upload a profile picture", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(uName)) {
                Toast.makeText(this, "Please enter your full name", Toast.LENGTH_SHORT).show();
                mName.requestFocus();
            } else if (TextUtils.isEmpty(uAge)) {
                Toast.makeText(this, "Please enter your birthday", Toast.LENGTH_SHORT).show();
                mAge.requestFocus();
            } else if (TextUtils.isEmpty(uCity)) {
                Toast.makeText(this, "Please enter your city", Toast.LENGTH_SHORT).show();
                mCity.requestFocus();
            } else if (TextUtils.isEmpty(uIntro)) {
                Toast.makeText(this, "Please introduce yourself", Toast.LENGTH_SHORT).show();
                mIntroduction.requestFocus();
            } else {
                addProfileInformation();
            }
        }

        else{Toast.makeText(EditProfileActivity
                .this, "Please create a user or sign in to use this feature!",Toast.LENGTH_SHORT).show();

        }

    }

    private void addProfileInformation() {


        final StorageReference filePath = profilePictureRef.child(uriProfilePic.getLastPathSegment() + ".jpg");

        final UploadTask uploadTask = filePath.putFile(uriProfilePic);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(EditProfileActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(EditProfileActivity.this, "Data stored successfully", Toast.LENGTH_SHORT).show();

                Task<Uri> UrlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();
                        }

                        uDownloadProfImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful())
                        {
                            uDownloadProfImageUrl = task.getResult().toString();

                            SaveToDatabase();
                        }
                    }
                });
            }
        });
    }

    private void SaveToDatabase() {
        user=mAuth.getCurrentUser();
        userid = user.getUid();
        ProfileInformation profileInformation = new ProfileInformation(userid, uName, uAge, uCity, uIntro, uDownloadProfImageUrl);
        rootRef.child(userid).setValue(profileInformation);
        Intent i = new Intent (EditProfileActivity.this,UserProfile.class);
        startActivity(i);

    }



}




