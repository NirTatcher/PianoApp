package com.dude.pianoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MyProfile extends AppCompatActivity {

    Button btnProfile;
    TextView txtEmail;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        txtEmail = findViewById(R.id.textView_email);
        txtEmail.setText(currentUser.getEmail().toString());


    }

    public void goToPlaying(View view){
        startActivity(new Intent(MyProfile.this,PLayingActivity.class));
    }
}