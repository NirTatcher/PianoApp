package com.dude.pianoapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.dude.pianoapp.PLayingActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.Inflater;

public class RecordListAdapter extends ArrayAdapter<String> {
    private StorageReference mStorageRef;
    private MediaPlayer mediaPlayer;

    private  Context mContext;
    int mResurce;

    public RecordListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<String> objects) {
        super(context, resource, objects);
        this.mContext = context;
        mResurce = resource;
        mStorageRef = FirebaseStorage.getInstance().getReference();


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final String path;

        path = getItem(position);

        String [] str = path.split("/");
        String [] disp_name = str[str.length-1].split(".3gp");
        SimpleDateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss");
        Long ts = Long.parseLong(disp_name[0]);
        Date d = new Date(ts);
        String time = dateFormat.format(d);


        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResurce,parent,false);
        TextView view = (TextView)convertView.findViewById(R.id.rec_name);
        final Button btn = (Button)convertView.findViewById(R.id.play_rec_btn);
        final Button del_btn = (Button)convertView.findViewById(R.id.del_rec_btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        playFromFirebaseADP(path ,v);
            }
        });

        view.setText(time);

        del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStorageRef.child(path).delete();
                btn.setEnabled(false);
            }
        });

        return convertView;
    }
    public void playFromFirebaseADP(String path, final View v){

        ((Button)v).setText("PAUSE");
        StorageReference storageRef = mStorageRef.child(path);
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(uri.toString());
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mp.start();
                            ((Button)v).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (mediaPlayer.isPlaying()) {
                                        Log.e("PAUSE", "onClick: PAUSE");
                                        ((Button)v).setText("PLAY");
                                        //((Button)v).setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
                                        mediaPlayer.pause();

                                    }else {
                                        ((Button)v).setText("PAUSE");

                                        //((Button)v).setBackgroundResource(R.drawable.ic_baseline_pause_24);
                                        mediaPlayer.start();
                                    }
                                }
                            });
                        }
                    });


                    mediaPlayer.prepare();
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {

                            ((Button)v).setText("PLAY");
                            //((Button)v).setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
                            mediaPlayer.stop();
                            mediaPlayer.release();
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
}
