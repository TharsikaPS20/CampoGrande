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
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.rey.material.widget.CheckBox;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    FirebaseAuth mAuth;
    private EditText InputEmail, InputPassword;
    private Button LoginButton;
    private ProgressBar progressBar;
    private TextView forgot_password;
   // private CheckBox rememberMe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        mAuth = FirebaseAuth.getInstance();

        LoginButton = findViewById(R.id.login);
        InputEmail = findViewById(R.id.login_email_input);
        InputPassword = findViewById(R.id.login_password_input);
        progressBar = findViewById(R.id.progressbar);
        forgot_password=findViewById(R.id.forgotpassword);
        forgot_password.setOnClickListener(this);

        //rememberMe = findViewById(R.id.remember_me);




        findViewById(R.id.login).setOnClickListener(this);
        ;}

    private void LoginUser() {
        final String email = InputEmail.getText().toString();
        final String password = InputPassword.getText().toString();

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
                    Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_LONG).show();

                    Intent i = new Intent(LoginActivity.this, Welcome.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }

                else
                {
                  Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }}
        });
    }



    @Override
    public void onClick(View view) {
        int i = view.getId();
            if(i==R.id.login) {
                    LoginUser();
        }
            if(i==R.id.forgotpassword)
            {
                Intent intent=new Intent(LoginActivity.this,Password.class);
                startActivity(intent);
            }

    }
}
