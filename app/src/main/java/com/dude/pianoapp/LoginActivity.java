package com.dude.pianoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText userEmail,userPass;
    private Button btnlogin, btnSignup;
    private FirebaseAuth mAuth;




        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            mAuth = FirebaseAuth.getInstance();
            
            initViews();
            initListener();


        }

        private void initListener() {
            btnSignup.setOnClickListener(this);
            btnlogin.setOnClickListener(this);
        }

        private void initViews() {
            userEmail=findViewById(R.id.userEmail);
            userPass=findViewById(R.id.userPass);
            btnlogin=findViewById(R.id.btnlogin);
            btnSignup=findViewById(R.id.btnSignup);

            btnlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btnlogin: userLogin(); break;
                case R.id.btnSignup: userSignup(); break;

            }
        }

        private void userLogin() {
            mAuth.signInWithEmailAndPassword(userEmail.getText().toString(), userPass.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(getApplicationContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }

                            // ...
                        }
                    });

        }

        private void updateUI(FirebaseUser user) {
            if(user!=null)
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
        }

        private void userSignup() {
            startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
        }


    }


