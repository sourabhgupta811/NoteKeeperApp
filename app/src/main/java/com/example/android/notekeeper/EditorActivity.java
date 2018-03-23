package com.example.android.notekeeper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashSet;

public class EditorActivity extends AppCompatActivity {
    EditText noteView;
    int noteID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        noteView=findViewById(R.id.note);
        Intent intent=getIntent();
        noteID=intent.getIntExtra("noteID",-1);
        if(noteID!=-1){
            noteView.setText(MainActivity.array.get(noteID));
        }
        else{
//            MainActivity.array.add("");
//            noteID=MainActivity.array.size()-1;
//            MainActivity.adapter.notifyDataSetChanged();
        }
        noteView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            String noteText;
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                noteText=s.toString();
                if(noteID==-1 && !noteText.equals("")){
                    MainActivity.array.add("");
                    noteID=MainActivity.array.size()-1;
                    MainActivity.adapter.notifyDataSetChanged();
                }
                MainActivity.array.set(noteID,noteText);
                MainActivity.adapter.notifyDataSetChanged();
                SharedPreferences sharedPreferences=getSharedPreferences("com.example.android.notekeeper", Context.MODE_PRIVATE);
                HashSet<String> hashSet=new HashSet<>(MainActivity.array);
                sharedPreferences.edit().putStringSet("notes",hashSet).apply();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(noteText.equals("")){
                    MainActivity.array.remove(noteID);
                    MainActivity.adapter.notifyDataSetChanged();
                    noteID=-1;
                }

            }
        });
    }
}
