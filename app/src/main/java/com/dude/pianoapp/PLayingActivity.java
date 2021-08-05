package com.dude.pianoapp;
import android.content.Intent;
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


//        record1 = (Button) findViewById(R.id.button);
//        record2 = (Button) findViewById(R.id.button2);
//        record3 = (Button) findViewById(R.id.button3);
//        record4 = (Button) findViewById(R.id.button4);
//        record5 = (Button) findViewById(R.id.button5);
//        record6 = (Button) findViewById(R.id.button6);


//        record1.setBackgroundResource(R.drawable.playsongshape);
//        record2.setBackgroundResource(R.drawable.playsongshape);
//        record3.setBackgroundResource(R.drawable.playsongshape);
//        record4.setBackgroundResource(R.drawable.playsongshape);
//        record5.setBackgroundResource(R.drawable.playsongshape);
//        record6.setBackgroundResource(R.drawable.playsongshape);

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
        Stam2();
    }




    public void playFromFirebase(View view){
        StorageReference storageRef = mStorageRef.child("sound/"+fileName);

        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                MediaPlayer mediaPlayer = new MediaPlayer();

                try {
                    mediaPlayer.setDataSource(uri.toString());
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mp.start();
                            record1.setText("Playing Recording From Firebase");

                        }
                    });

                    mediaPlayer.prepare();
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            mediaPlayer.stop();
                            mediaPlayer.release();
                            record1.setText("FINISHED");
                        }
                    });
//            mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }

    public void playFromFirebaseADP(String path){
        StorageReference storageRef = mStorageRef.child(path);

        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                MediaPlayer mediaPlayer = new MediaPlayer();

                try {
                    mediaPlayer.setDataSource(uri.toString());
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mp.start();
                            //record1.setText("Playing Recording From Firebase");
                        }
                    });

                    mediaPlayer.prepare();
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            mediaPlayer.stop();
                            mediaPlayer.release();
                            //record1.setText("FINISHED");
                        }
                    });
//            mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }



    private void startplaying(int recordingno) {

        mPlayer = new MediaPlayer();

        try{

            switch (recordingno){

                case 1:
                    // Create a reference with an initial file path and name
                    mPlayer.setDataSource(mFileName1);
                    break;
                case 2:
                    mPlayer.setDataSource(mFileName2);
                    break;

                case 3:
                    mPlayer.setDataSource(mFileName3);
                    break;

                case 4:
                    mPlayer.setDataSource(mFileName4);
                    break;

                case 5:
                    mPlayer.setDataSource(mFileName5);
                    break;
                case 6:
                    mPlayer.setDataSource(mFileName6);
                    break;
            }

           mPlayer.prepare();
           mPlayer.start();


        }catch (IOException e){

            Log.e("failed","Failes");
        }


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

    public void Stam2() {
        final StorageReference listRef = mStorageRef.child(currentUser.getUid());


//        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
//                (this, R.layout.adapter_view);
//        arrayAdapter.setNotifyOnChange(true);
//
//        record_list.setAdapter(arrayAdapter);



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
