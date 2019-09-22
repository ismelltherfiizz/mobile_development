package com.example.a1lab;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        findViewById(R.id.sign_up_button).setOnClickListener(v -> {
            if(!isDataInvalid()) {
                final String email = emailField.getText().toString();
                final String password = passwordField.getText().toString();
                final String name = nameField.getText().toString();
                final String phoneField = nameField.getText().toString();
                createUser(email, password, name);
            }
        });
        findViewById(R.id.sign_up_link).setOnClickListener(v -> {
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


    protected boolean isName(EditText text) {
        CharSequence name = text.getText().toString();
        Pattern pattern = Pattern.compile("[A-Za-z]+");
        Matcher matcher = pattern.matcher(name);
        return (matcher.matches() && name.length()>=2);

    }
    protected boolean isPhone(EditText text) {
        CharSequence phone = text.getText().toString();
        Pattern pattern = Pattern.compile("\\+380[0-9]{9}");
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();

    }
    @Override
    protected boolean isDataInvalid(){
        boolean hasError = false;

        if (isEmpty(emailField)) {
            emailField.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            emailValidation.setVisibility(View.VISIBLE);
            emailValidation.setText(getString(R.string.field_validation_is_empty_text));
            hasError = true;
        }
        else if (!isEmail(emailField)) {
            emailField.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            emailValidation.setVisibility(View.VISIBLE);
            emailValidation.setText(getString(R.string.email_validation_text));
            hasError = true;
        }

        if (isEmpty(passwordField)) {
            passwordField.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            passwordValidation.setVisibility(View.VISIBLE);
            passwordValidation.setText(getString(R.string.field_validation_is_empty_text));
            hasError = true;
        }
        else if(!isPassword(passwordField)){
            passwordField.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            passwordValidation.setVisibility(View.VISIBLE);
            passwordValidation.setText(getString(R.string.password_validation_text));
            hasError = true;
        }
        if (isEmpty(nameField)) {
            nameField.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            nameValidation.setVisibility(View.VISIBLE);
            nameValidation.setText(getString(R.string.field_validation_is_empty_text));
            hasError = true;
        }
        else if (!isName(nameField)) {
            nameField.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            nameValidation.setVisibility(View.VISIBLE);
            nameValidation.setText(getString(R.string.name_validation_text));
            hasError = true;
        }

        if (isEmpty(phoneField)) {
            phoneField.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            phoneValidation.setVisibility(View.VISIBLE);
            phoneValidation.setText(getString(R.string.field_validation_is_empty_text));
            hasError = true;
        }
        else if(!isPhone(phoneField)){
            phoneField.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            phoneValidation.setVisibility(View.VISIBLE);
            phoneValidation.setText(getString(R.string.phone_validation_text));
            hasError = true;
        }
        return hasError;
    }

}
