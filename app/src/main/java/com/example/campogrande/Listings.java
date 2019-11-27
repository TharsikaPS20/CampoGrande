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
import com.google.firebase.auth.FirebaseUser;
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
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Listings extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "Listings";
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
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private Boolean checked;
    private Boolean checked1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();

        mViewPager = findViewById(R.id.container);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.calendar:
                        Intent intent2 = new Intent(Listings.this, HostBookings.class);
                        startActivity(intent2);
                        break;

                    case R.id.listings:
                        Intent intent3 = new Intent(Listings.this, HostListings.class);
                        startActivity(intent3);
                        break;

                }


                return false;
            }
        });

        PropertyImageRef = FirebaseStorage.getInstance().getReference().child("Property Images");
        PropertiesRef = FirebaseDatabase.getInstance().getReference().child("Properties");
        AddNewPropertyButton =findViewById(R.id.AddButton);
        InputPropertyName = findViewById(R.id.property_name);
        InputAddress = findViewById(R.id.address);
        InputSize = findViewById(R.id.size);
        InputPrice = findViewById(R.id.price);
        InputDescription = findViewById(R.id.description);
        SelectImage = findViewById(R.id.select_image);
        RadioWater = findViewById(R.id.radioWater);
        RadioElectricity = findViewById(R.id.radioElectricity);

        SelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });

        checked = RadioWater.isChecked();

        RadioWater.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(checked)
                {
                    RadioWater.setChecked(false);
                    checked = RadioWater.isChecked();
                }

                else
                {
                    RadioWater.setChecked(true);
                    checked = RadioWater.isChecked();
                }


            }
        });

        checked1 = RadioElectricity.isChecked();
        RadioElectricity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checked1)
                {
                    RadioElectricity.setChecked(false);
                    checked1 = RadioElectricity.isChecked();
                }

                else
                {
                    RadioElectricity.setChecked(true);
                    checked1 = RadioElectricity.isChecked();
                }

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
            Toast.makeText(this, getString(R.string.img_listings), Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(PropertyName))
        {
            Toast.makeText(this, getString(R.string.name_listings), Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Address))
        {
            Toast.makeText(this, getString(R.string.addr_listings), Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Size))
        {
            Toast.makeText(this, getString(R.string.size_listings), Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Price))
        {
            Toast.makeText(this, getString(R.string.price_listings), Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Description))
        {
            Toast.makeText(this, getString(R.string.desc_listings), Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(Listings.this, getString(R.string.img_result), Toast.LENGTH_SHORT).show();

                            SavePropertyInfoToDatabase();
                        }
                    }
                });
            }
        });
    }

    private void SavePropertyInfoToDatabase() {
        String PropertyId = PropertiesRef.push().getKey();
        CampingProperties campingProperties = new CampingProperties(PropertyId, CurrentDate, CurrentTime, PropertyName, Address, Size, Price, Description, DownloadImageUrl);
        PropertiesRef.child(PropertyId).setValue(campingProperties);
        SelectImage.setImageURI(Uri.parse(""));
        InputPropertyName.setText("");
        InputAddress.setText("");
        InputSize.setText("");
        InputPrice.setText("");
        InputDescription.setText("");
        RadioWater.setChecked(false);
        RadioElectricity.setChecked(false);

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
        user =mAuth.getCurrentUser();
        if (user != null) {
            Intent intent = new Intent(Listings.this, UserProfile.class);
            startActivity(intent);
        }
        else{
            AccessFragment dialog = new AccessFragment();
            dialog.show(getSupportFragmentManager(),"this");
        }
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