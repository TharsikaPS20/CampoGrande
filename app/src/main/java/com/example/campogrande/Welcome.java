package com.example.campogrande;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.os.Build;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Welcome extends AppCompatActivity {
    Button start;
    ImageView campogrande;
    ObjectAnimator animation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        campogrande = findViewById(R.id.cglogo);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Path path = new Path();
            path.arcTo(0f, 0f, 800f, 500f, 230f, -90f, true);
            animation = ObjectAnimator.ofFloat(campogrande, View.X, View.Y, path);
            animation.setDuration(2000);
            animation.start();
        } else {
            animation = ObjectAnimator.ofFloat(campogrande, "translationX", 0f,0f,500f,0f);
            animation.setDuration(4000);
            animation.start();
        }

        animation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });


        start = findViewById(R.id.getStarted);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }
        });

    }

    private void openActivity() {
        Intent i= new Intent(this, Discover.class);
        startActivity(i);
    }


}