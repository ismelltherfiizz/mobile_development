package com.example.a1lab;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class DataValidator {

    private final static Pattern IS_NAME_PATTERN = Pattern.compile("[A-Za-z]+");
    private final static Pattern IS_PHONE_PATTERN = Pattern.compile("\\+380[0-9]{9}");
    private final static int MIN_NAME_LENGTH = 2;
    private final static int MIN_PASSWORD_LENGTH = 8;
    private static DataValidator INSTANCE = new DataValidator();
    private Context mContext;

    private DataValidator() {
    }

    public static DataValidator getInstance() {
        return (INSTANCE);
    }

    public boolean isDataInvalid(EditText emailField, EditText passwordField, TextView emailValidation,
                                 TextView passwordValidation, Context context) {
        mContext = context;
        boolean hasError = false;

        if (isEmpty(emailField)) {
            hasError = true;
            showInvalid(emailField, emailValidation, getString(R.string.field_validation_is_empty_text));
        } else if (!isEmail(emailField)) {
            hasError = true;
            showInvalid(emailField, emailValidation, getString(R.string.email_validation_text));
        }

        if (isEmpty(passwordField)) {
            hasError = true;
            showInvalid(passwordField, passwordValidation, getString(R.string.field_validation_is_empty_text));
        } else if (!isPassword(passwordField)) {
            hasError = true;
            showInvalid(passwordField, passwordValidation, getString(R.string.password_validation_text));
        }

        return hasError;
    }

    public boolean isDataInvalid(EditText emailField, EditText passwordField, EditText nameField,
                                 EditText phoneField, TextView emailValidation, TextView passwordValidation,
                                 TextView nameValidation, TextView phoneValidation, Context context) {

        boolean hasError = isDataInvalid(emailField, passwordField, emailValidation, passwordValidation, context);

        if (isEmpty(emailField)) {
            hasError = true;
            showInvalid(nameField, nameValidation, getString(R.string.field_validation_is_empty_text));
        } else if (!isName(nameField)) {
            hasError = true;
            showInvalid(nameField, nameValidation, getString(R.string.name_validation_text));
        }

        if (isEmpty(passwordField)) {
            hasError = true;
            showInvalid(phoneField, phoneValidation, getString(R.string.field_validation_is_empty_text));
        } else if (!isPhone(phoneField)) {
            hasError = true;
            showInvalid(phoneField, phoneValidation, getString(R.string.phone_validation_text));
        }

        return hasError;
    }

    private void showInvalid(EditText textField, TextView textValidation, String errorText) {
        textField.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        textValidation.setVisibility(View.VISIBLE);
        textValidation.setText(errorText);
    }

    public boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    private boolean isPassword(EditText text) {
        CharSequence password = text.getText().toString();
        return password.length() >= MIN_PASSWORD_LENGTH;
    }

    private boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    public boolean isName(EditText text) {
        CharSequence name = text.getText().toString();
        Matcher matcher = IS_NAME_PATTERN.matcher(name);
        return (matcher.matches() && name.length() >= MIN_NAME_LENGTH);
    }

    private boolean isPhone(EditText text) {
        CharSequence phone = text.getText().toString();
        Matcher matcher = IS_PHONE_PATTERN.matcher(phone);
        return matcher.matches();
    }

    private String getString(int stringId) {
        return mContext.getResources().getString(stringId);
    }

}
