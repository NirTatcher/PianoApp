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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnSignup;
    EditText name,userEmail,userPass,userRePass;
    private FirebaseAuth mAuth;
    private FirebaseUser user2Register;
    private FirebaseDatabase database;
    private DatabaseReference myRef ;

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

        name = findViewById(R.id.txtInpt_name);
        userEmail=findViewById(R.id.userEmail);
        userPass=findViewById(R.id.userPass);
        userRePass=findViewById(R.id.userRePass);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users");


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnSignup:userSignup();break;
        }
    }

    private void userSignup() {
        String txt_name  = name.getText().toString();
        String email = userEmail.getText().toString();
        String pass = userPass.getText().toString();
        String repass = userRePass.getText().toString();

        if (email.length() == 0){
            Toast.makeText(this, "Please Fill Email", Toast.LENGTH_SHORT).show();
            return;
        }


        if( pass.length() > 3 && pass.equals(repass)) {
            mAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information

                                FirebaseUser u = mAuth.getCurrentUser();
                                if (u != null){
                                    if (txt_name.length() > 0)
                                    {
                                        myRef.child(u.getUid()).setValue(txt_name);
                                    }
                                    else {
                                        myRef.child(u.getUid()).setValue("Beethoven");
                                    }
                                }else
                                {
                                    Toast.makeText(RegisterActivity.this, "User is null", Toast.LENGTH_LONG).show();
                                }

                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }else{
            Toast.makeText(getApplicationContext(), "Password not match OR to Short",
                    Toast.LENGTH_SHORT).show();
        }
    }
}