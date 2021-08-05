package com.dude.pianoapp;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static com.dude.pianoapp.MainActivity.fileName;
import static com.dude.pianoapp.MainActivity.mFileName1;
import static com.dude.pianoapp.MainActivity.mFileName2;
import static com.dude.pianoapp.MainActivity.mFileName3;
import static com.dude.pianoapp.MainActivity.mFileName4;
import static com.dude.pianoapp.MainActivity.mFileName5;
import static com.dude.pianoapp.MainActivity.mFileName6;

public class PLayingActivity extends AppCompatActivity {

    private MediaPlayer mPlayer;
    private Button record1,record2,record3,record4,record5,record6;
    private StorageReference mStorageRef;
    private MediaPlayer mediaPlayer;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private ListView record_list;
    private ArrayList<String> arr_list_record;
    private SharedPreferences sp;


    // boolean variables

    private boolean isplaying1;
    private boolean isplaying2;
    private boolean isplaying3;
    private boolean isplaying4;
    private boolean isplaying5;
    private boolean isplaying6;


    private boolean isplaying;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        record_list = findViewById(R.id.list_view);
        arr_list_record = new ArrayList<String>();
//        GetRecordings();

        //RecordListAdapter recordListAdapter = new RecordListAdapter(this,R.layout.adapter_view);


        isplaying = false;

        isplaying1 = false;
        isplaying2 = false;
        isplaying3 = false;
        isplaying4 = false;
        isplaying5 = false;
        isplaying6 = false;

    }

    @Override
    protected void onStart() {
        super.onStart();
        GetRecords();
    }


    private void stopPlaying(){

        mPlayer.release();
        mPlayer = null;

    }



    @Override
    protected void onPause() {
        super.onPause();

        if(mPlayer!=null){
            mPlayer.release();
            mPlayer = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(mPlayer!=null){
            mPlayer.release();
            mPlayer = null;
        }
    }

    public void GetRecords() {
        final StorageReference listRef = mStorageRef.child(currentUser.getUid());


        Task task = listRef.listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        Log.e("", "smk");

//                        for (StorageReference prefix : listResult.getPrefixes()) {
//                            // All the prefixes under listRef.
//                            // You may call listAll() recursively on them.
//                            Log.e("", prefix.getName());
//                        }

                        for (StorageReference item : listResult.getItems()) {
                            // All the items under listRef.
                            Log.e("", item.getName());
                            arr_list_record.add(item.getPath());
                            //arrayAdapter.add(item.getPath());
                        }

                        Log.e("",arr_list_record.toString());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Uh-oh, an error occurred!
                    }
                });
        task.addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                findViewById(R.id.loading_bar).setVisibility(View.GONE);
                RecordListAdapter adapter = new RecordListAdapter(PLayingActivity.this,R.layout.adapter_view,arr_list_record);
                record_list.setAdapter(adapter);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PLayingActivity.this, "Unable to load recodings", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
