package com.barri.myjisho.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.barri.myjisho.R;
import com.barri.myjisho.adapter.MangaAdapter;
import com.barri.myjisho.model.Coleccion;
import com.barri.myjisho.persistence.Backup;


public class MangaView extends AppCompatActivity {

    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manga_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Añadir manga

                AlertDialog.Builder builder = new AlertDialog.Builder(MangaView.this);
                builder.setTitle("Añadir Manga");

                View layout = getLayoutInflater().inflate(R.layout.manga_dialog,null);

                final EditText mangaName = (EditText) layout.findViewById(R.id.editText);

                builder.setView(layout);

                builder.setCancelable(true);

                builder.setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String manga = mangaName.getText().toString();
                        if (manga.length() == 0)
                            Toast.makeText(getApplicationContext(), "Necesitas poner un nombre", Toast.LENGTH_SHORT).show();
                        else {
                            new Coleccion(manga).save();
                            Toast.makeText(getApplicationContext(), "Manga [" + manga + "] añadido", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                builder.setNegativeButton("Cancelar",null);

                Dialog d = builder.create();
                d.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                d.show();
            }
        });

        gridView = (GridView) findViewById(R.id.mangaGrid);

        gridView.setAdapter(new MangaAdapter(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_manga_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_backup) {
            new Backup(this).backup();
        } else if(id == R.id.action_restore) {
            new Backup(this).restore();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        MangaAdapter adapter = (MangaAdapter) gridView.getAdapter();
        adapter.notifyDataSetChanged();
    }

    public void refresh(){
        onRestart();
    }
}
