package com.example.a1lab;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignUpActivity extends MainActivity {

    private FirebaseAuth auth;
    private EditText emailField;
    private EditText passwordField;
    private EditText nameField;
    private EditText phoneField;
    private TextView emailValidation;
    private TextView passwordValidation;
    private TextView nameValidation;
    private TextView phoneValidation;
    private TextView signUpLink;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();
        emailField = findViewById(R.id.sign_up_email);
        passwordField = findViewById(R.id.sign_up_password);
        nameField = findViewById(R.id.sign_up_name);
        phoneField = findViewById(R.id.sign_up_phone);
        emailValidation = findViewById(R.id.sign_up_email_validation);
        passwordValidation = findViewById(R.id.sign_up_password_validation);
        nameValidation = findViewById(R.id.sign_up_name_validation);
        phoneValidation = findViewById(R.id.sign_up_phone_validation);
        signUpLink = findViewById(R.id.sign_up_link);
        signUpButton = findViewById(R.id.sign_up_button);

        signUpButton.setOnClickListener(v -> onClick());

        signUpLink.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    private void createUser(String email, String password, String name) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(name)
                        .build();

                user.updateProfile(profileUpdates).addOnCompleteListener(this, t -> {
                    if (t.isSuccessful()) {
                        Log.d("SignUpActivity", "User profile updated.");
                        onSignInSuccess();
                    }
                });
            } else {
                onSignInError();
            }
        });
    }

    private void onClick(){
        if (!DataValidator.getInstance().isDataInvalid(emailField, passwordField, nameField, phoneField,
                emailValidation, passwordValidation, nameValidation, phoneValidation, getApplicationContext())) {
            final String email = emailField.getText().toString();
            final String password = passwordField.getText().toString();
            final String name = nameField.getText().toString();
            createUser(email, password, name);
        }
    }
}
