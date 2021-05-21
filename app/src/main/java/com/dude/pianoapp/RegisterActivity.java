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

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnSignup;
    EditText userEmail,userPass,userRePass;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        initViews();
        initListener();
    }

    private void initListener() {
        btnSignup.setOnClickListener(this);
    }

    private void initViews() {
        btnSignup=findViewById(R.id.btnSignup);
        userEmail=findViewById(R.id.userEmail);
        userPass=findViewById(R.id.userPass);
        userRePass=findViewById(R.id.userRePass);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnSignup:userSignup();break;
        }
    }

    private void userSignup() {
        String email=userEmail.getText().toString();
        String pass=userPass.getText().toString();
        String repass=userRePass.getText().toString();

        if(pass.equals(repass)) {
            mAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }else{
            Toast.makeText(getApplicationContext(), "Password not match!",
                    Toast.LENGTH_SHORT).show();
        }
    }
}