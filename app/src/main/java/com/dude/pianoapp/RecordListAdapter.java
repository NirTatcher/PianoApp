package com.dude.pianoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class RecordListAdapter extends ArrayAdapter<Record> {
    private  Context mContext;
    int mResurce;

    public RecordListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Record> objects) {
        super(context, resource, objects);
        this.mContext = context;
        mResurce = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String filename;
        String path;

        filename = getItem(position).getFileName();
        path = getItem(position).getPath();

        Record r = new Record(filename,path);
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResurce,parent,false);
        TextView view = (TextView)convertView.findViewById(R.id.textView1);
        view.setText(filename);

        return convertView;
    }
}
