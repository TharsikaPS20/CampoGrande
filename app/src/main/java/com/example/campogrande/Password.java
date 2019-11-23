package com.example.campogrande;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Password extends AppCompatActivity {
    EditText email_password;
    Button get_password;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        email_password = findViewById(R.id.emailforpassword);
        get_password = findViewById(R.id.getPassword);
        progressBar = findViewById(R.id.progress_circular);


        firebaseAuth = FirebaseAuth.getInstance();
        get_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.sendPasswordResetEmail(email_password.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Password.this, "Your password was reset. Check your email!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Password.this,LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Password.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            Intent intent1 = new Intent(Password.this, MainActivity.class);
                            startActivity(intent1);
                        }
                    }
                });
            }
        });

    }




    }




