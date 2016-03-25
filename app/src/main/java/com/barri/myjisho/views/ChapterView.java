package com.barri.myjisho.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.barri.myjisho.R;
import com.barri.myjisho.model.Capitulo;
import com.barri.myjisho.model.Coleccion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChapterView extends AppCompatActivity {

    private long coleccionID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        coleccionID = getIntent().getLongExtra("coleccion",-1);

        ListView listView = (ListView) findViewById(R.id.chapterList);

        final Coleccion coleccion = Coleccion.findById(Coleccion.class,coleccionID);

        final List<Capitulo> list = coleccion.getCapitulos();

        List<String> chapters = new ArrayList<>();
        for (Capitulo c : list) {
            chapters.add(c.getName());
        }

        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, chapters));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), TranslationsView.class);
                intent.putExtra("chapterID",list.get(position).getId());
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
                //Añadir capitulo

                AlertDialog.Builder builder = new AlertDialog.Builder(ChapterView.this);
                builder.setTitle("Añadir Capitulo");

                View layout = getLayoutInflater().inflate(R.layout.manga_dialog,null);

                final EditText chapterName = (EditText) layout.findViewById(R.id.editText);

                builder.setView(layout);

                builder.setCancelable(true);

                builder.setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String chapter = chapterName.getText().toString();
                        if (chapter.length() == 0)
                            Toast.makeText(getApplicationContext(), "Necesitas poner un nombre", Toast.LENGTH_SHORT).show();
                        else {
                            new Capitulo(chapter,coleccion).save();
                            Toast.makeText(getApplicationContext(), "Capitulo [" + chapter + "] añadido", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                builder.setNegativeButton("Cancelar",null);

                Dialog d = builder.create();
                d.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                d.show();
            }
        });

        Snackbar.make(listView,coleccion.getName(),Snackbar.LENGTH_INDEFINITE).show();
    }

}
