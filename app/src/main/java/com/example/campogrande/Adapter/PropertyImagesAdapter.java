package com.example.campogrande.Adapter;


import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.campogrande.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PropertyImagesAdapter extends PagerAdapter {

    private List<String> propertyImages;

    public PropertyImagesAdapter(List<String> propertyImages) {
        this.propertyImages = propertyImages;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView propertyImage = new ImageView(container.getContext());
        Picasso.get().load(propertyImages.get(position)).into(propertyImage);
        //propertyImage.setImageURI(Uri.parse(propertyImages.get(position)));
        //Glide.with(container.getContext()).load(propertyImages.get(position)).apply(new RequestOptions().placeholder(R.mipmap.ic_launcher)).into(propertyImage);
        //propertyImage.setImageResource(propertyImages.get(position));
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
