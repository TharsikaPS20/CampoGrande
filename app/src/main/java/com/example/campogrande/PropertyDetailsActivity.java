package com.example.campogrande;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.campogrande.Adapter.PropertyDetailsAdapter;
import com.example.campogrande.Adapter.PropertyImagesAdapter;
import com.example.campogrande.Model.CampingProperties;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.*;

public class PropertyDetailsActivity extends AppCompatActivity {

    private ViewPager propertyImagesViewPager;
    private TabLayout viewpagerIndicator;

    private Button checkButton;
    private ViewPager propertyDetailsViewpager;
    private TabLayout propertyDetailsTablayout;
    private TextView propertyTitle;
    private TextView propertyPrice;

    private String propertyID= "";
    private LinearLayout rateNowLayout;
    private List<String> propertyImages = new ArrayList<>();

    FloatingActionButton fabBtn, fab2;
    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.floatingaction_property_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        propertyID = getIntent().getStringExtra("pid");
        propertyImagesViewPager = findViewById(R.id.property_images_viewpager);
        viewpagerIndicator = findViewById(R.id.viewpager_indicator);
        propertyDetailsViewpager = findViewById(R.id.property_details_viewpager);
        propertyDetailsTablayout = findViewById(R.id.property_details_tablayout);
        propertyTitle = findViewById(R.id.property_title);
        propertyPrice = findViewById(R.id.property_price_detailview);
        checkButton = findViewById(R.id.check_btn);
        getPropertyDetails(propertyID);

        //propertyImages.add(getResources().getDrawable(R.drawable.caravanground1).toString());
        //propertyImages.add(getResources().getDrawable(R.drawable.caravanground2).toString());
        //propertyImages.add(getResources().getDrawable(R.drawable.caravanground3).toString());
        /*propertyImages.add(R.drawable.caravanground2);
        propertyImages.add(R.drawable.caravanground3);
        propertyImages.add(R.drawable.caravanground4);*/

        checkButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(PropertyDetailsActivity.this, Availability.class);
                startActivity(intent1);
                return;
            }
        });



        fabBtn = findViewById(R.id.fab);
        fabBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PropertyDetailsActivity.this, BookingActivity.class);
                startActivity(i);
            }
        });

        fab2 = findViewById(R.id.fab2);
        fab2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if(flag){

                    fab2.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_heartfull));
                    flag = false;

                }else if(!flag){

                    fab2.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_heartborder));
                    flag = true;

                }

            }
        });


        propertyDetailsViewpager.setAdapter(new PropertyDetailsAdapter(getSupportFragmentManager(), propertyDetailsTablayout.getTabCount()));
        propertyDetailsViewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(propertyDetailsTablayout));
        propertyDetailsTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                propertyDetailsViewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        rateNowLayout = findViewById(R.id.rate_now_container);
        for (int x = 0; x < rateNowLayout.getChildCount(); x++)
        {
            final int starPosition = x;
            rateNowLayout.getChildAt(x).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    setRating(starPosition);
                }
            });
        }
    }

    private void getPropertyDetails(final String propertyID) {
        DatabaseReference propertyRef = FirebaseDatabase.getInstance().getReference().child("Properties");
        propertyRef.child(propertyID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    CampingProperties properties = dataSnapshot.getValue(CampingProperties.class);
                    propertyTitle.setText(properties.getPropertyName());
                    propertyPrice.setText(properties.getPrice() + "DKK");
                    propertyImages.add(properties.getImageUrl());
                    propertyImages.add("https://d5r9gdi4mky31.cloudfront.net/imagevault/publishedmedia/0a2gfy9bddu89kw981bb/18747018_img_1551.jpg");
                    propertyImages.add("https://images.startsat60.com/wp-content/uploads/20160413145010/130416_caravan_holiday-720x405.jpg");
                    propertyImages.add("https://www.natur-on-line.dk/wp-content/uploads/2019/11/green-1072828_1920-720x405.jpg");
                    propertyImages.add("https://havesektionen.dk/wp-content/uploads/2019/11/orginal-1-1140x641.jpg");

                    PropertyImagesAdapter propertyImagesAdapter = new PropertyImagesAdapter(propertyImages);
                    propertyImagesViewPager.setAdapter(propertyImagesAdapter);
                    viewpagerIndicator.setupWithViewPager(propertyImagesViewPager, true);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void setRating(int starPosition) {
        for (int x = 0; x < rateNowLayout.getChildCount(); x++)
        {
            ImageView starBtn = (ImageView) rateNowLayout.getChildAt(x);
            starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#BEBEBE")));
            if (x <= starPosition)
            {
                starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFBB00")));
            }
        }
    }
}