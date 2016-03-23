package com.barri.myjisho.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.barri.myjisho.R;
import com.barri.myjisho.adapter.TranslationAdapter;
import com.barri.myjisho.model.Traduccion;

public class TranslationsView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translations_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), JishoActivity.class);
                startActivity(intent);
            }
        });

        ListView lv = (ListView) findViewById(R.id.listView);

        lv.setAdapter(new TranslationAdapter(this));

    }

}
