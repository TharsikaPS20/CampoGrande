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
import com.google.firebase.auth.FirebaseUser;
import com.rey.material.widget.CheckBox;

public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = "emailLogin";
    FirebaseAuth mAuth;
    private EditText InputEmail, InputPassword;
    private Button signInBtn, signOutBtn;
    private TextView forgot_password;
    private TextView mStatusTextView;
    private TextView mDetailTextView;

    // private CheckBox rememberMe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signInBtn = findViewById(R.id.login);
        signInBtn.setOnClickListener(this);
        signOutBtn = findViewById(R.id.logout);
        signOutBtn.setOnClickListener(this);
        InputEmail = findViewById(R.id.login_email_input);
        InputPassword = findViewById(R.id.login_password_input);
        forgot_password=findViewById(R.id.forgotpassword);
        forgot_password.setOnClickListener(this);
        mStatusTextView = findViewById(R.id.status);
        mDetailTextView = findViewById(R.id.detail);

        mAuth = FirebaseAuth.getInstance();

        //rememberMe = findViewById(R.id.remember_me);
        }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void LoginUser(String email,String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }
        showProgressDialog();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(user.isEmailVerified())
                            {Log.d(TAG, "signInWithEmail:success");
                            updateUI(user);
                            Intent i = new Intent(LoginActivity.this, Welcome.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);}

                            else{
                                Toast.makeText(LoginActivity.this,"Please verify your email by clicking the link that was sent to your email.", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                        hideProgressDialog();
                    }
                });

    }

    private boolean validateForm() {
        boolean validation = true;
        final String email = InputEmail.getText().toString();
        final String password = InputPassword.getText().toString();

        if (email.isEmpty()) {
            InputEmail.setError("Email is required");
            InputEmail.requestFocus();
            validation = false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            InputEmail.setError("Please enter a valid email");
            InputEmail.requestFocus();
            validation = false;
        }

        if (password.isEmpty()) {
            InputPassword.setError("Password is required");
            InputPassword.requestFocus();
            validation = false;
        }

        if (password.length() < 6) {
            InputPassword.setError("The password must contain minimum 6 characters");
            InputPassword.requestFocus();
            validation = false;
        }

        return validation;
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            mStatusTextView.setText(getString(R.string.emailpassword_status_fmt,
                    user.getEmail(), user.isEmailVerified()));
            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

            findViewById(R.id.login_email_input).setVisibility(View.GONE);
            findViewById(R.id.login_password_input).setVisibility(View.GONE);
            findViewById(R.id.logout).setVisibility(View.VISIBLE);

        } else {
            mStatusTextView.setText(R.string.signed_out);
            mDetailTextView.setText(null);

            findViewById(R.id.login_email_input).setVisibility(View.VISIBLE);
            findViewById(R.id.login_password_input).setVisibility(View.VISIBLE);
            findViewById(R.id.logout).setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.login) {
            LoginUser(InputEmail.getText().toString(), InputPassword.getText().toString());
        } else if (i == R.id.logout) {
            signOut();
        }
            else if(i==R.id.forgotpassword)
            {
                Intent intent=new Intent(LoginActivity.this,Password.class);
                startActivity(intent);
            }

    }

    private void signOut() {
        mAuth.signOut();
        updateUI(null);
    }
}
