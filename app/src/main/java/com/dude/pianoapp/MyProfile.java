package com.dude.pianoapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MyProfile extends AppCompatActivity {

    Button btnProfile;
    TextView txtEmail;
    Button btnLogout;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        btnLogout = findViewById(R.id.btn_logout);
        txtEmail = findViewById(R.id.textView_email);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout(v);
            }
        });
        txtEmail.setText(currentUser.getEmail().toString());

    }

    public void goToPlaying(View view){
        startActivity(new Intent(MyProfile.this,PLayingActivity.class));
    }

    public void Logout(View v){
        mAuth.signOut();
        startActivity(new Intent(MyProfile.this,LoginActivity.class));
    }
}