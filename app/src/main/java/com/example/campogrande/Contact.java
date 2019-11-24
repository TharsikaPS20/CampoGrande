package com.example.campogrande;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Contact extends AppCompatActivity implements View.OnClickListener {
    TextView email;
    ImageView fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        email = findViewById(R.id.email_contact);
        email.setOnClickListener(this);
        fb = findViewById(R.id.facebook_contact);
        fb.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.email_contact) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"240249@via.dk"});
            intent.putExtra(Intent.EXTRA_SUBJECT, "Campo Grande: Customer Support");
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
            }

        else if (v.getId() == R.id.facebook_contact) {
            Intent intentFb = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/CG-Test-Page-103857107751152/"));
            startActivity(intentFb);
        }



    }
}
