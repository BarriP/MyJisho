package com.barri.myjisho.business;

import android.app.Activity;
import android.content.Context;
import android.widget.ListView;

import com.barri.myjisho.adapter.JishoAdapter;
import com.barri.myjisho.model.Traduccion;
import com.barri.myjisho.views.JishoActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Barri on 23/03/2016.
 */
public class JishoScraper {
    private final String BASE_URL = "http://classic.jisho.org/words?jap=";

    private final ListView listView;

    private JishoActivity context;

    public JishoScraper(ListView listView, JishoActivity context){
        this.listView = listView;
        this.context = context;
    }

    public void findResults(String query){

        final String mQuery = query;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Document page;
                final List<Traduccion> trads = new ArrayList<>();

                try {
                    page = Jsoup.parse(new URL(BASE_URL + mQuery).openStream(), "UTF-8", BASE_URL + mQuery);
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }

                //primero recogemos los elementos del tbody
                Element tableBody = page.getElementsByTag("tbody").get(0);

                //navemgamos por los elementos pares (0 - n)
                Elements trs = tableBody.select("tr:nth-child(odd)");

                for ( Element elem: trs) {
                    Element kanjiBlock = elem.getElementsByClass("kanji").get(0);
                    Element readingBlock = elem.getElementsByClass("match").get(0);

                    Elements meanings = elem.getElementsByClass("meanings_column");
                    StringBuffer sb = new StringBuffer();
                    for (Element meaning : meanings) {
                        sb.append(meaning.text());
                        sb.append("\n");
                    }

                    trads.add(new Traduccion(kanjiBlock.text() + " - " + readingBlock.text(),sb.toString(),0));
                }

                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listView.setAdapter(new JishoAdapter(context, trads));
                    }
                });
            }
        });

        thread.start();

        return;
    }
}
