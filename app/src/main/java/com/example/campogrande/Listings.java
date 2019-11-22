package com.example.campogrande;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import androidx.appcompat.widget.Toolbar;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Listings extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "Listings";

    //private SectionsPageAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;
    private String PropertyName, Address, Size, Price, Description, CurrentDate, CurrentTime, PropertyRandomKey, DownloadImageUrl;
    private Button AddNewPropertyButton;
    private EditText InputPropertyName,InputAddress,InputSize,InputPrice,InputDescription;
    private ImageButton SelectImage,SelectImage1,SelectImage2;
    private RadioButton RadioWater, RadioElectricity;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private StorageReference PropertyImageRef;
    private DatabaseReference PropertiesRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();

        mViewPager = (ViewPager) findViewById(R.id.container);


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.calendar:
                        Intent intent2 = new Intent(Listings.this, ActivityOne.class);
                        startActivity(intent2);
                        break;

                    case R.id.listings:
                        Intent intent3 = new Intent(Listings.this, ActivityTwo.class);
                        startActivity(intent3);
                        break;

                }


                return false;
            }
        });

        PropertyImageRef = FirebaseStorage.getInstance().getReference().child("Property Images");
        PropertiesRef = FirebaseDatabase.getInstance().getReference().child("Properties");
        AddNewPropertyButton = (Button) findViewById(R.id.AddButton);
        InputPropertyName = (EditText) findViewById(R.id.property_name);
        InputAddress = (EditText) findViewById(R.id.address);
        InputSize = (EditText) findViewById(R.id.size);
        InputPrice = (EditText) findViewById(R.id.price);
        InputDescription = (EditText) findViewById(R.id.description);
        SelectImage = (ImageButton) findViewById(R.id.select_image);
        SelectImage1 = (ImageButton) findViewById(R.id.select_image1);
        SelectImage2 = (ImageButton) findViewById(R.id.select_image2);
        RadioWater = (RadioButton) findViewById(R.id.radioWater);
        RadioElectricity = (RadioButton) findViewById(R.id.radioElectricity);

        SelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });

        AddNewPropertyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateProductData();
            }
        });
    }


    private void OpenGallery() {
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
            ImageUri = data.getData();
            SelectImage.setImageURI(ImageUri);
        }
    }

    private void ValidateProductData() {
        PropertyName = InputPropertyName.getText().toString();
        Address = InputAddress.getText().toString();
        Size = InputSize.getText().toString();
        Price = InputPrice.getText().toString();
        Description = InputDescription.getText().toString();

        if (ImageUri == null)
        {
            Toast.makeText(this, "Property image is mandatory...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(PropertyName))
        {
            Toast.makeText(this, "Please write a name for the property!", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Address))
        {
            Toast.makeText(this, "Please mention the address!", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Size))
        {
            Toast.makeText(this, "Please define the size!", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Price))
        {
            Toast.makeText(this, "Please define a price!", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Description))
        {
            Toast.makeText(this, "Please give a description!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            StorePropertyInformation();
        }
    }

    private void StorePropertyInformation() {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("dd. MMM. yyyy");
        CurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        CurrentTime = currentTime.format(calendar.getTime());

        PropertyRandomKey = CurrentDate + CurrentTime;

        final StorageReference filePath = PropertyImageRef.child(ImageUri.getLastPathSegment() + PropertyRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(Listings.this, "Error: " + message, Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Listings.this, "Image uploaded successfully... ", Toast.LENGTH_SHORT).show();

                Task<Uri> UrlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();
                        }

                        DownloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful())
                        {
                            DownloadImageUrl = task.getResult().toString();
                            Toast.makeText(Listings.this, "Got the Property image Url successfully... ", Toast.LENGTH_SHORT).show();

                            SavePropertyInfoToDatabase();
                        }
                    }
                });
            }
        });
    }

    private void SavePropertyInfoToDatabase() {
        CampingProperites campingProperites = new CampingProperites(CurrentDate, CurrentTime, PropertyName, Address, Size, Price, Description, DownloadImageUrl);
        PropertiesRef.push().setValue(campingProperites);
        SelectImage.setImageURI(Uri.parse(""));
        InputPropertyName.setText("");
        InputAddress.setText("");
        InputSize.setText("");
        InputPrice.setText("");
        InputDescription.setText("");

        /*HashMap<String, Object> propertyMap = new HashMap<>();
        propertyMap.put("pid", PropertyRandomKey.toString());
        propertyMap.put("date", CurrentDate.toString());
        propertyMap.put("time", CurrentTime.toString());
        propertyMap.put("pname", PropertyName.toString());
        propertyMap.put("address", Address.toString());
        propertyMap.put("size", Size.toString());
        propertyMap.put("price", Price.toString());
        propertyMap.put("description", Description.toString());
        propertyMap.put("image", DownloadImageUrl.toString());
        PropertiesRef.child(PropertyRandomKey).updateChildren(propertyMap);
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(AddNewActivity.this, "Property is added successfully... ", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            String message = task.getException().toString();
                            Toast.makeText(AddNewActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/
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


        else if(id ==R.id.nav_host)
        {
            openHost();
        }

        else if(id ==R.id.nav_messages)
        {
            openMessages();
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
        Intent intent = new Intent(Listings.this, Reservations.class);
        startActivity(intent);
    }

    private void openProfile() {
        Intent intent = new Intent(Listings.this, UserProfile.class);
        startActivity(intent);
    }

    private void openHost() {
        Intent intent = new Intent(Listings.this, Host.class);
        startActivity(intent);
    }

    private void openMessages() {
        Intent intent = new Intent(Listings.this, Messages.class);
        startActivity(intent);
    }


    private void openAbout() {
        Intent intent = new Intent(Listings.this, About.class);
        startActivity(intent);
    }


    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        Intent intent = new Intent(getApplicationContext(), Discover.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        Intent intent1 = new Intent(Listings.this,MainActivity.class);
        startActivity(intent1);
    }

    private void openFavourites() {
        Intent intent = new Intent(Listings.this, Favourites.class);
        startActivity(intent);
    }

    private void openHome() {
        Intent intent = new Intent(Listings.this, Discover.class);
        startActivity(intent);
    }


}





