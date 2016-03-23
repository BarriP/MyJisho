package com.barri.myjisho.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.barri.myjisho.R;
import com.barri.myjisho.model.Coleccion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChapterView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //final int a = getIntent().getIntExtra("pos",-1);

        ListView listView = (ListView) findViewById(R.id.chapterList);

        List<String> list = new ArrayList<>();

        Iterator<Coleccion> iterator = Coleccion.findAll(Coleccion.class);
        
        while (iterator.hasNext()) {
            Coleccion c = iterator.next();
            list.add(c.getName());
        }

        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), TranslationsView.class);
                startActivity(intent);
            }
        });

        //TODO
        listView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
