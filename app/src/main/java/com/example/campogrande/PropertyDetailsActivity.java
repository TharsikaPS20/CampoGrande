package com.example.campogrande;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.campogrande.Adapter.PropertyDetailsAdapter;
import com.example.campogrande.Adapter.PropertyImagesAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PropertyDetailsActivity extends AppCompatActivity {

    private ViewPager propertyImagesViewPager;
    private TabLayout viewpagerIndicator;

    private ViewPager propertyDetailsViewpager;
    private TabLayout propertyDetailsTablayout;
    private TextView propertyTitle;
    private TextView propertyPrice;

    private String propertyID = "";
    private LinearLayout rateNowLayout;
    private List<String> propertyImages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        propertyID = getIntent().getStringExtra("pid");
        propertyImagesViewPager = findViewById(R.id.property_images_viewpager);
        viewpagerIndicator = findViewById(R.id.viewpager_indicator);
        propertyDetailsViewpager = findViewById(R.id.property_details_viewpager);
        propertyDetailsTablayout = findViewById(R.id.property_details_tablayout);
        propertyTitle = findViewById(R.id.property_title);
        propertyPrice = findViewById(R.id.property_price_detailview);

        getPropertyDetails(propertyID);

        //propertyImages.add(getResources().getDrawable(R.drawable.caravanground1).toString());
        //propertyImages.add(getResources().getDrawable(R.drawable.caravanground2).toString());
        //propertyImages.add(getResources().getDrawable(R.drawable.caravanground3).toString());
        /*propertyImages.add(R.drawable.caravanground2);
        propertyImages.add(R.drawable.caravanground3);
        propertyImages.add(R.drawable.caravanground4);*/



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
            rateNowLayout.getChildAt(x).setOnClickListener(new View.OnClickListener() {
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
                    propertyImages.add("https://www.campingandcaravanningclub.co.uk/GetAsset.aspx?id=fAAxADQANgA0ADIAfAB8AEYAYQBsAHMAZQB8AHwAMAB8AA2");
                    propertyImages.add("https://www.campingandcaravanningclub.co.uk/GetAsset.aspx?id=fAAxADQANgAzADUAfAB8AEYAYQBsAHMAZQB8AHwAMAB8AA2");

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