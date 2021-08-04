package com.dude.pianoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaRecorder;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.storage.StorageReference;

public class DrumsActivity extends AppCompatActivity implements View.OnClickListener {
    private SoundPool soundPool;
    private StorageReference mStorageRef;
    private MediaRecorder mediaRecorder;
    public static String fileName = null;
    String file = null;
    boolean mStartRecording = true;
    private int drum1;
    private int snare1;
    private int crash1;
    private int hihat1;
    private ImageView myKick;
    private ImageView mySnare;
    private ImageView myCrash;
    private ImageView myHiHat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drums);

        soundPool = new SoundPool.Builder()
                .setMaxStreams(6)
                .build();

        hihat1 = soundPool.load(this,R.raw.hihat,1);
        crash1 = soundPool.load(this,R.raw.crash,1);
        myHiHat = findViewById(R.id.hihat);
        myCrash = findViewById(R.id.crash);
        drum1 = soundPool.load(this, R.raw.kickdrum, 1);
        myKick = findViewById(R.id.drumkick);
        myKick.setOnClickListener(this);
        snare1 = soundPool.load(this, R.raw.snare, 1);
        mySnare = findViewById(R.id.snareDrum);
        mySnare.setOnClickListener(this);
        myHiHat.setOnClickListener(this);
        myCrash.setOnClickListener(this);



    }

    public void playKick(View view) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.drumkick:
                soundPool.play(drum1,1,1,0,0,1);
                break;
            case R.id.snareDrum:
                soundPool.play(snare1,1,1,0,0,1);
                break;
            case R.id.hihat:
                soundPool.play(hihat1,1,1,0,0,1);
                break;
            case R.id.crash:
                soundPool.play(crash1,1,1,0,0,1);
                break;
        }
    }
}