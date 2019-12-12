package com.example.a1lab.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.a1lab.DataValidator;
import com.example.a1lab.MainActivity;
import com.example.a1lab.R;
import com.example.a1lab.TabbedActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.Objects;

public class UserProfileFragment extends Fragment {

    private TextInputEditText emailEditText;
    private TextInputEditText nameEditText;
    private TextInputLayout emailLayout;
    private TextInputLayout nameLayout;
    private ImageView imageView;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private Button saveChangesButton;
    private Button signOutButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_user_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeFields();
        getCurrentUserProfilePicture();
        initOnClickListeners();
        initOnFocusChangeListeners();
    }

    private void initializeFields() {
        if (getActivity() != null) {
            TabbedActivity currentActivity = (TabbedActivity) getActivity();
            firebaseAuth = FirebaseAuth.getInstance();
            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            imageView = currentActivity.findViewById(R.id.user_profile_picture);
            saveChangesButton = currentActivity.findViewById(R.id.save_changes_button);
            signOutButton = currentActivity.findViewById(R.id.sign_out_button);
            nameLayout = currentActivity.findViewById(R.id.name_change_input);
            nameEditText = currentActivity.findViewById(R.id.name_change_edit_text);
            emailLayout = currentActivity.findViewById(R.id.email_change_input);
            emailEditText = currentActivity.findViewById(R.id.email_change_edit_text);

            nameEditText.setText(firebaseUser.getDisplayName());
            emailEditText.setText(firebaseUser.getEmail());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                if (data.getData() != null) {
                    Uri selectedImage = data.getData();
                    UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder().
                            setPhotoUri(selectedImage).build();

                    firebaseUser.updateProfile(userProfileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                getCurrentUserProfilePicture();
                                Toast.makeText(getContext(),
                                        getResources().getString(R.string.user_picture_upload), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        }

    }

    private void initOnClickListeners() {
        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailEditText.getText() != null && nameEditText.getText() != null) {
                    String emailText = emailEditText.getText().toString();
                    String nameText = nameEditText.getText().toString();
                    if (DataValidator.getInstance().isName(nameEditText) && DataValidator.getInstance().
                            isEmail(emailEditText)) {
                        firebaseUser.updateEmail(emailText).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(nameText).build();
                                    Objects.requireNonNull(user).updateProfile(profileChangeRequest).
                                            addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(getContext(), getResources().getString(R.string.user_info_updated), Toast.LENGTH_LONG).show();
                                                    }

                                                }
                                            });
                                } else {
                                    String errorString = getResources().getString(R.string.no_internet_connection);
                                    Toast.makeText(getActivity(), errorString, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                }
            }
        });
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);
            }
        });
    }

    private void getCurrentUserProfilePicture() {
        Uri photoUri = firebaseUser.getPhotoUrl();
        imageView.setImageURI(photoUri);
    }

    private void initOnFocusChangeListeners() {
        emailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (emailEditText.hasFocus()) {
                    emailEditText.setText("");
                }
                if (!hasFocus) {
                    if (DataValidator.getInstance().isEmail(emailEditText)) {
                        emailLayout.setErrorEnabled(false);
                    } else {
                        emailLayout.setError("Email is invalid");
                    }
                }
            }
        });

        nameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (nameEditText.hasFocus()) {
                    nameEditText.setText("");
                }
                if (!hasFocus) {
                    if (DataValidator.getInstance().isName(nameEditText)) {
                        nameLayout.setErrorEnabled(false);
                    } else {
                        nameLayout.setError("Name is invalid");
                    }
                }
            }
        });
    }

}
