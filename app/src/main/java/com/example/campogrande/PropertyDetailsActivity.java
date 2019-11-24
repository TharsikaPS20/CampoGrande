package com.example.campogrande;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.campogrande.Adapter.PropertyDetailsAdapter;
import com.example.campogrande.Adapter.PropertyImagesAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class PropertyDetailsActivity extends AppCompatActivity {

    private ViewPager propertyImagesViewPager;
    private TabLayout viewpagerIndicator;

    private ViewPager propertyDetailsViewpager;
    private TabLayout propertyDetailsTablayout;

    private LinearLayout rateNowLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_details);

        propertyImagesViewPager = findViewById(R.id.property_images_viewpager);
        viewpagerIndicator = findViewById(R.id.viewpager_indicator);
        propertyDetailsViewpager = findViewById(R.id.property_details_viewpager);
        propertyDetailsTablayout = findViewById(R.id.property_details_tablayout);

        List<Integer> propertyImages = new ArrayList<>();
        propertyImages.add(R.drawable.caravanground1);
        propertyImages.add(R.drawable.caravanground2);
        propertyImages.add(R.drawable.caravanground3);
        propertyImages.add(R.drawable.caravanground4);

        PropertyImagesAdapter propertyImagesAdapter = new PropertyImagesAdapter(propertyImages);
        propertyImagesViewPager.setAdapter(propertyImagesAdapter);
        viewpagerIndicator.setupWithViewPager(propertyImagesViewPager, true);

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
