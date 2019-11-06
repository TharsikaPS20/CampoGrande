package com.example.campogrande;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    FirebaseAuth mAuth;
    private EditText InputEmail, InputPassword;
    private Button LoginButton;
    private ProgressBar progressBar;

    private String parentDbName = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        LoginButton = findViewById(R.id.login_btn);
        InputEmail = findViewById(R.id.login_email_input);
        InputPassword = findViewById(R.id.login_password_input);
        progressBar = findViewById(R.id.progressbar);

        findViewById(R.id.login_btn).setOnClickListener(this); }

    private void LoginUser() {
        String email = InputEmail.getText().toString();
        String password = InputPassword.getText().toString();

        if (email.isEmpty()) {
            InputEmail.setError("Email is required");
            InputEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            InputEmail.setError("Please enter a valid email");
            InputEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            InputPassword.setError("Password is required");
            InputPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            InputPassword.setError("The password must contain minimum 6 characters");
            InputPassword.requestFocus();
            return;
        }


        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressBar.setVisibility(View.VISIBLE);
                    Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                }

                else
                {
                  Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }}
        });
    }



    @Override
    public void onClick(View view) {
            switch (view.getId()) {
                case R.id.login_btn:
                    LoginUser();
                    break;

                case R.id.google:
                    finish();
                    startActivity(new Intent(this, RegisterActivity_new.class));
                    break;
        }

    }
}
