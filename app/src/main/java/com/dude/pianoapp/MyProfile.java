package com.dude.pianoapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

public class MyProfile extends AppCompatActivity {

    Button btnProfile;
    TextView txtEmail;
    TextView name;
    TextView comps;
    Button btnLogout;
    int count;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseDatabase database;
    private DatabaseReference myRef ;
    private StorageReference mStorageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users");
        mStorageRef = FirebaseStorage.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        btnLogout = findViewById(R.id.btn_logout);
        txtEmail = findViewById(R.id.textView_email);
        name = findViewById(R.id.textView_name);
        comps = findViewById(R.id.textView_comps);

        txtEmail.setText(currentUser.getEmail().toString());

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout(v);
            }
        });

    }

    public void goToPlaying(View view){
        startActivity(new Intent(MyProfile.this,PLayingActivity.class));
    }

    public void Logout(View v){
        mAuth.signOut();
        startActivity(new Intent(MyProfile.this,LoginActivity.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Task t = myRef.child(currentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    Log.e("get name", "onComplete: "+task.getResult().getValue());
                    name.setText(String.valueOf(task.getResult().getValue()));
                }
                else{
                    name.setText("Beethoven");
                }
            }
        });
//        t.addOnCompleteListener(new OnCompleteListener() {
//            @Override
//            public void onComplete(@NonNull Task task) {
//                final StorageReference listRef = mStorageRef.child(currentUser.getUid());
//                count = 0;
//                listRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
//                    @Override
//                    public void onSuccess(ListResult listResult) {
//                        for (StorageReference item : listResult.getItems()) {
//                            count++;
//                        }
//
//                    }
//                }).addOnCompleteListener(new OnCompleteListener<ListResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<ListResult> task) {
//                        comps.setText(count);
//                    }
//                });
//
//            }
//        });
    }
}