package com.example.campogrande.Adapter;


import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class PropertyImagesAdapter extends PagerAdapter {

    private List<Integer> propertyImages;

    public PropertyImagesAdapter(List<Integer> propertyImages) {
        this.propertyImages = propertyImages;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView propertyImage = new ImageView(container.getContext());
        propertyImage.setImageResource(propertyImages.get(position));
        container.addView(propertyImage, 0);
        return propertyImage;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView)object);
    }

    @Override
    public int getCount() {
        return propertyImages.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
