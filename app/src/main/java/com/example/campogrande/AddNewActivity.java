package com.example.campogrande;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import io.grpc.Context;

public class AddNewActivity extends AppCompatActivity {

    private String PropertyName, Address, Size, Price, Description, CurrentDate, CurrentTime, PropertyRandomKey, DownloadImageUrl;
    private ArrayList<String> ImageUrlList = new ArrayList<String>();
    private Button AddNewPropertyButton;
    private EditText InputPropertyName,InputAddress,InputSize,InputPrice,InputDescription;
    private ImageButton SelectImage,SelectImage1,SelectImage2;
    private RadioButton RadioWater, RadioElectricity;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private ArrayList<Uri> ImageList = new ArrayList<Uri>();
    private int upload_count = 0;
    private StorageReference PropertyImageRef;
    private DatabaseReference PropertiesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);

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
        galleryIntent.setType("image/*");
        galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(galleryIntent, GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GalleryPick && resultCode==RESULT_OK && data!=null)
        {
            int countData = data.getClipData().getItemCount();
            int currentImageSelect = 0;

            while (currentImageSelect <= countData)
            {
                ImageUri = data.getClipData().getItemAt(currentImageSelect).getUri();
                ImageList.add(ImageUri);

                currentImageSelect = currentImageSelect + 1;
            }
            Toast.makeText(this, "You have selected: " + ImageList.size() +" Images", Toast.LENGTH_LONG).show();


        }
    }




    private void ValidateProductData() {
        PropertyName = InputPropertyName.getText().toString();
        Address = InputAddress.getText().toString();
        Size = InputSize.getText().toString();
        Price = InputPrice.getText().toString();
        Description = InputDescription.getText().toString();

        if (ImageList == null)
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

        for(upload_count = 0; upload_count < ImageList.size(); upload_count++)
        {
            Calendar calendar = Calendar.getInstance();

            SimpleDateFormat currentDate = new SimpleDateFormat("dd. MMM. yyyy");
            CurrentDate = currentDate.format(calendar.getTime());

            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
            CurrentTime = currentTime.format(calendar.getTime());

            PropertyRandomKey = CurrentDate + CurrentTime;

            Uri IndividualImage = ImageList.get(upload_count);
            final StorageReference filePath = PropertyImageRef.child(ImageUri.getLastPathSegment() + PropertyRandomKey + ".jpg");

            filePath.putFile(IndividualImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            DownloadImageUrl = String.valueOf(uri);
                            ImageUrlList.add(DownloadImageUrl);
                        }
                    });
                }
            });
        }

        SavePropertyInfoToDatabase();
    }

    private void SavePropertyInfoToDatabase() {
        //String key = PropertyRandomKey.toString();
        DownloadImageUrl = ImageUrlList.get(0).toString();
        CampingProperites campingProperites = new CampingProperites(CurrentDate, CurrentTime, PropertyName, Address, Size, Price, Description, DownloadImageUrl, ImageUrlList);
        PropertiesRef.push().setValue(campingProperites);
        //PropertiesRef.child(key).setValue(campingProperites);

        SelectImage.setImageURI(Uri.parse(""));
        InputPropertyName.setText("");
        InputAddress.setText("");
        InputSize.setText("");
        InputPrice.setText("");
        InputDescription.setText("");
    }
}
