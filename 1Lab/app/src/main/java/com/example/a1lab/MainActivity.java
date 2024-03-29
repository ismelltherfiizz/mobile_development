package com.example.a1lab;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText emailField;
    private EditText passwordField;
    private TextView emailValidation;
    private TextView passwordValidation;
    private TextView signInLink;
    private Button signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewInit();

        signInButton.setOnClickListener(v -> onClick());
        signInLink.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignUpActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    private void onClick(){
        if (!DataValidator.getInstance().isDataInvalid(emailField, passwordField,
                emailValidation, passwordValidation, getApplicationContext())) {
            final String email = emailField.getText().toString();
            final String password = passwordField.getText().toString();
            signIn(email, password);
        }
    }


    private void viewInit(){
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        emailField = findViewById(R.id.login_screen_email);
        passwordField = findViewById(R.id.login_screen_password);
        emailValidation = findViewById(R.id.login_screen_email_validation);
        passwordValidation = findViewById(R.id.login_screen_password_validation);
        signInButton = findViewById(R.id.login_screen_sign_in_button);
        signInLink = findViewById(R.id.login_screen_link);
    }

    private void signIn(final String email, final String password) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                onSignInSuccess();
            } else {
                onSignInError();
            }
        });

    }

    protected void onSignInError() {
        Toast.makeText(MainActivity.this, getString(R.string.sign_in_incorrect), Toast.LENGTH_LONG).show();
    }

    protected void onSignInSuccess() {
        Intent intent = new Intent(this, TabbedActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        startActivity(intent);
    }

}