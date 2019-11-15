package com.example.campogrande;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.campogrande.Adapter.PropertyImagesAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class PropertyDetailsActivity extends AppCompatActivity {

    private ViewPager propertyImagesViewPager;
    private TabLayout viewpagerIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_details);

        propertyImagesViewPager = findViewById(R.id.property_images_viewpager);
        viewpagerIndicator = findViewById(R.id.viewpager_indicator);

        List<Integer> propertyImages = new ArrayList<>();
        propertyImages.add(R.drawable.ic_campogrande);
        propertyImages.add(R.drawable.background2);
        propertyImages.add(R.drawable.backgroundgreen);
        propertyImages.add(R.drawable.ic_menu_camera);

        PropertyImagesAdapter propertyImagesAdapter = new PropertyImagesAdapter(propertyImages);
        propertyImagesViewPager.setAdapter(propertyImagesAdapter);
        viewpagerIndicator.setupWithViewPager(propertyImagesViewPager, true);
    }
}
