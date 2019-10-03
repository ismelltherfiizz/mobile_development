package com.example.a1lab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    private EditText emailField;
    private EditText passwordField;
    private TextView emailValidation;
    private TextView passwordValidation;

    private final static int minPasswordLength = 8;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        emailField = findViewById(R.id.login_screen_email);
        passwordField = findViewById(R.id.login_screen_password);
        emailValidation = findViewById(R.id.login_screen_email_validation);
        passwordValidation = findViewById(R.id.login_screen_password_validation);

        findViewById(R.id.login_screen_sign_in_button).setOnClickListener(v -> {
            if (!isDataInvalid()) {
                final String email = emailField.getText().toString();
                final String password = passwordField.getText().toString();
                signIn(email, password);
            }
        });
        findViewById(R.id.login_screen_link).setOnClickListener(v -> {
            Intent intent = new Intent(this, SignUpActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
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

    protected void showInvalid(EditText textField, TextView textValidation, String errorText) {
        textField.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        textValidation.setVisibility(View.VISIBLE);
        textValidation.setText(errorText);
    }

    protected boolean isDataInvalid() {
        boolean hasError = false;
        if (isEmpty(emailField)) {
            showInvalid(emailField, emailValidation, getString(R.string.field_validation_is_empty_text));
            hasError = true;
        } else if (!isEmail(emailField)) {
            showInvalid(emailField, emailValidation, getString(R.string.email_validation_text));
            hasError = true;
        }

        if (isEmpty(passwordField)) {
            showInvalid(passwordField, passwordValidation, getString(R.string.field_validation_is_empty_text));
            hasError = true;
        } else if (!isPassword(passwordField)) {
            showInvalid(passwordField, passwordValidation, getString(R.string.password_validation_text));
            hasError = true;
        }

        return hasError;
    }

    protected boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    protected boolean isPassword(EditText text) {
        CharSequence password = text.getText().toString();
        return password.length() >= minPasswordLength;

    }


    protected boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    protected void onSignInError() {
        Toast.makeText(MainActivity.this, getString(R.string.sign_in_incorrect), Toast.LENGTH_LONG).show();
    }

    protected void onSignInSuccess() {
        Intent intent = new Intent(this, WelcomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
