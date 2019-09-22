package com.example.a1lab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        TextView welcome = findViewById(R.id.welcome_text);
        String welcomeText = "Welcome, " + FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        welcome.setText(welcomeText);
        findViewById(R.id.welcome_sign_out_button).setOnClickListener(v ->
        {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
}
