package com.barri.myjisho.views;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.barri.myjisho.R;
import com.barri.myjisho.adapter.TranslationAdapter;
import com.barri.myjisho.model.Capitulo;
import com.barri.myjisho.model.Traduccion;

public class TranslationsView extends AppCompatActivity {

    private long chapterID;
    private int lastExpandedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translations_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        chapterID = getIntent().getLongExtra("chapterID", -1);

        final Capitulo capitulo = Capitulo.findById(Capitulo.class,chapterID);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), JishoActivity.class);
                intent.putExtra("chapterID",chapterID);
                startActivity(intent);
            }
        });

        final ExpandableListView lv = (ExpandableListView) findViewById(R.id.listView);

        TranslationAdapter adapter = new TranslationAdapter(this, capitulo);

        lv.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    lv.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });

        lv.setAdapter(adapter);

        Snackbar.make((CoordinatorLayout)findViewById(R.id.coordinator),capitulo.getName()
                ,Snackbar.LENGTH_INDEFINITE).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        ExpandableListView lv = (ExpandableListView) findViewById(R.id.listView);

        TranslationAdapter adapter = (TranslationAdapter) lv.getExpandableListAdapter();

        adapter.notifyDataSetChanged();
    }
}
