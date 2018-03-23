package com.example.android.notekeeper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mark aircon on 23-03-2018.
 */

public class CustomAdapter extends ArrayAdapter<String> {
    Context context;
    ArrayList<String> list;
    public CustomAdapter(Context context, ArrayList<String> resource) {
        super(context,-1, resource);
        this.context=context;
        this.list=resource;
    }
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view;
        if(convertView==null)
            view=inflater.inflate(R.layout.custom_item,parent,false);
        else
            view=convertView;
        TextView textView=view.findViewById(R.id.textView);
        textView.setText(list.get(position));
        return view;
    }
}
