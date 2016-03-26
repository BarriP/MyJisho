package com.barri.myjisho.views;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import com.barri.myjisho.R;
import com.barri.myjisho.business.JishoScraper;
import com.barri.myjisho.model.Capitulo;
import com.barri.myjisho.model.Traduccion;

public class JishoActivity extends AppCompatActivity {

    private long chapterID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jisho);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        chapterID = getIntent().getLongExtra("chapterID",-1);

        Capitulo capitulo = Capitulo.findById(Capitulo.class,chapterID);

        final SearchView search = (SearchView) findViewById(R.id.searchView);
        final ListView listView = (ListView) findViewById(R.id.tempList);
        final EditText pageText = (EditText) findViewById(R.id.pageBox);

        int lastPage = PreferenceManager.getDefaultSharedPreferences(this).getInt("lastPage",0);

        pageText.setText(String.valueOf(lastPage));

        final JishoScraper scraper = new JishoScraper(listView,this,capitulo);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                scraper.setPage(Integer.parseInt(pageText.getText().toString()));
                scraper.findResults(query);
                search.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Traduccion t = (Traduccion) parent.getItemAtPosition(position);
                t.save();
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                        .edit().putInt("lastPage",t.getPage()).apply();
                JishoActivity.this.finish();
            }
        });
    }

}
