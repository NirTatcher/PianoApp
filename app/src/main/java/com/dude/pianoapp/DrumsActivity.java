package com.dude.pianoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaRecorder;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

public class DrumsActivity extends AppCompatActivity implements View.OnClickListener {
    private SoundPool soundPool;
    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;
    private MediaRecorder mediaRecorder;
    public static String fileName = null;
    String file = null;
    boolean mStartRecording = true;
    private int drum1;
    private int snare1;
    private int crash1;
    private int hihat1;
    private int rim;
    private int openhat;
    private int tom;
    private int tamb;
    private ImageView myKick;
    private ImageView myTom;
    private ImageView mySnare;
    private ImageView myRim;
    private ImageView myCrash;
    private ImageView myHiHat;
    private ImageView myTamb;
private ImageView btnPofile;
private ImageView btnPiano;
private Button btnRec;
    private Boolean isRecording;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drums);

        soundPool = new SoundPool.Builder()
                .setMaxStreams(7)
                .build();
         rim = soundPool.load(this,R.raw.rim,1);
         openhat = soundPool.load(this,R.raw.openhat,1);
         tamb = soundPool.load(this,R.raw.tamb,1);
         tom = soundPool.load(this,R.raw.tom,1);
        hihat1 = soundPool.load(this,R.raw.hihat,1);
        crash1 = soundPool.load(this,R.raw.openhat,1);
        myHiHat = findViewById(R.id.hihat1);
        myCrash = findViewById(R.id.crash1);
        drum1 = soundPool.load(this, R.raw.kickdrum, 1);
        myKick = findViewById(R.id.kick1);
        btnPiano = findViewById(R.id.piano_icon);
        btnPofile = findViewById(R.id.profile_icon);
        btnRec = findViewById(R.id.btnRecord);
        snare1 = soundPool.load(this, R.raw.snare, 1);
        myTom = findViewById(R.id.kick2);
        myRim = findViewById(R.id.snare2);
        myTamb = findViewById(R.id.miriamdrum);
        myKick.setOnClickListener(this);
        mySnare = findViewById(R.id.snare1);
        mySnare.setOnClickListener(this);
        myHiHat.setOnClickListener(this);
        myCrash.setOnClickListener(this);
        myTom.setOnClickListener(this);
        myRim.setOnClickListener(this);
        myTamb.setOnClickListener(this);
        mAuth =  FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        btnPiano.setOnClickListener(this);
        btnRec.setOnClickListener(this);
        btnPofile.setOnClickListener(this);
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        fileName = ts+".3gp";
        file = getExternalCacheDir().getAbsolutePath()+ File.separator+fileName;
        isRecording=false;


    }

    public void playKick(View view) {

    }
    private void uploadRecord() {
        Uri fileUri = Uri.fromFile(new File(file));

        Long tsLong = System.currentTimeMillis();
        String ts = tsLong.toString();
        fileName = ts +".3gp";

        StorageReference fileRef = mStorageRef.child(mAuth.getCurrentUser().getUid()+"/"+fileName);

        fileRef.putFile(fileUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
//                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });
    }
    private void stopRecord() {
        mediaRecorder.stop();
        mediaRecorder.release();


    }
    private void startRecord() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(file);

        try {
            mediaRecorder.prepare();
            mediaRecorder.start();


        } catch (IOException e) {
            e.printStackTrace();
        }



    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.kick1:
                soundPool.play(drum1,1,1,0,0,1);
                break;
            case R.id.snare1:
                soundPool.play(snare1,1,1,0,0,1);
                break;
            case R.id.hihat1:
                soundPool.play(hihat1,1,1,0,0,1);
                break;
            case R.id.crash1:
                soundPool.play(openhat,1,1,0,0,1);
                break;
            case R.id.kick2:
                soundPool.play(tom,1,1,0,0,1);
                break;
            case R.id.snare2:
                soundPool.play(rim,1,1,0,0,1);
                break;
            case R.id.miriamdrum:
                soundPool.play(tamb,1,1,0,0,1);
                break;
            case R.id.profile_icon:
                startActivity(new Intent(DrumsActivity.this,MyProfile.class));

                break;
            case R.id.piano_icon:
                startActivity(new Intent(DrumsActivity.this,MainActivity.class));
                 break;
            case R.id.btnRecord:
                if(isRecording==false){
                    btnRec.setText("FINISH");
                    startRecord();
                }
                else
                {
                    stopRecord();
                    btnRec.setText("RECORD");
                    uploadRecord();
                }

                isRecording=!isRecording;

                break;


        }
    }
}