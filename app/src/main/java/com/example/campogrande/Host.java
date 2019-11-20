package com.example.campogrande;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rengwuxian.materialedittext.MaterialEditText;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Host extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

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
        setContentView(R.layout.activity_host);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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


        Button changePhoto = findViewById(R.id.photochange);
        updateProfile = findViewById(R.id.fab1);


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

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();

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

                else{Toast.makeText(Host.this, "Please create a user or sign in to use this feature!",Toast.LENGTH_SHORT).show();

                }

            }

            private void addProfileInformation() {


                final StorageReference filePath = profilePictureRef.child(uriProfilePic.getLastPathSegment() + ".jpg");

                final UploadTask uploadTask = filePath.putFile(uriProfilePic);

                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String message = e.toString();
                        Toast.makeText(Host.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(Host.this, "Data stored successfully", Toast.LENGTH_SHORT).show();

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
                //rootRef.push().setValue(profileInformation);
                Intent i = new Intent (Host.this,Listings.class);
                startActivity(i);

        }





        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.action_settings){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

  /*  @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }*/



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();


        if(id==R.id.nav_home){
            openHome();
        }
        else if(id==R.id.nav_reservations){
            openReservations();
        }

        else if(id == R.id.nav_favourites){
            openFavourites();
        }

        else if(id==R.id.nav_profile)
        {
            openProfile();
        }


        else if(id ==R.id.nav_messages)
        {
            openMessages();
        }

        else if(id ==R.id.nav_listings)
        {
            openListings();
        }


        else if (id==R.id.nav_about)
        {
            openAbout();
        }

        else if(id==R.id.nav_signout)
        {
            signOut();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openReservations() {
        Intent intent = new Intent(Host.this, Reservations.class);
        startActivity(intent);
    }

    private void openProfile() {
        Intent intent = new Intent(Host.this, EditProfileActivity.class);
        startActivity(intent);
    }

    private void openMessages() {
        Intent intent = new Intent(Host.this, Messages.class);
        startActivity(intent);
    }

    private void openListings() {
        Intent intent = new Intent(Host.this, Listings.class);
        startActivity(intent);
    }


    private void openAbout() {
        Intent intent = new Intent(Host.this, About.class);
        startActivity(intent);
    }


    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        Intent intent = new Intent(getApplicationContext(),Home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        Intent intent1 = new Intent(Host.this,MainActivity.class);
        startActivity(intent1);
    }

    private void openFavourites() {
        Intent intent = new Intent(Host.this, Favourites.class);
        startActivity(intent);
    }

    private void openHome() {
        Intent intent = new Intent(Host.this, Home.class);
        startActivity(intent);
    }


}




