package com.example.android.notekeeper;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    ListView list;
    static ArrayList<String> array;
    static ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list=findViewById(R.id.list);

        HashSet<String> hashSet=(HashSet<String>)getSharedPreferences("com.example.android.notekeeper",Context.MODE_PRIVATE).getStringSet("notes",null);
        if(hashSet!=null){
            array=new ArrayList<>(hashSet);
        }
        else{
            array=new ArrayList<>();
            array.add("Enter a new Note");
        }

        adapter=new CustomAdapter(this,array);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(MainActivity.this,EditorActivity.class);
                intent.putExtra("noteID",position);
                startActivity(intent);
            }
        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(MainActivity.this).setIcon(R.drawable.ic_launcher_foreground).setTitle("Are you sure?")
                    .setMessage("you want to delete it")
                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        array.remove(position);
                        adapter.notifyDataSetChanged();
                        SharedPreferences sharedPreferences=getSharedPreferences("com.example.android.notekeeper", Context.MODE_PRIVATE);
                        HashSet<String> hashSet=new HashSet<>(MainActivity.array);
                        sharedPreferences.edit().putStringSet("notes",hashSet).apply();
                    }
                }).setNegativeButton("No",null).show();
                return true;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.mymenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId()==0){
            Intent intent=new Intent(MainActivity.this,EditorActivity.class);
            startActivity(intent);
        }
        return true;
    }
    public void makeNote(View v){
        Intent intent=new Intent(MainActivity.this,EditorActivity.class);
        startActivity(intent);
    }
}
