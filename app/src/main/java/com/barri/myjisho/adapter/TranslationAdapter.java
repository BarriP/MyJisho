package com.barri.myjisho.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.barri.myjisho.R;
import com.barri.myjisho.model.Coleccion;
import com.barri.myjisho.model.Traduccion;

/**
 * Created by Barri on 23/03/2016.
 */
public class TranslationAdapter extends BaseAdapter{

    private Context context;

    public TranslationAdapter(Context context){
        super();
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.translation_cell, null);
        }

        TextView tKanji = (TextView) convertView.findViewById(R.id.kanji);
        TextView tDesc = (TextView) convertView.findViewById(R.id.decripcion);

        final Traduccion t= Traduccion.listAll(Traduccion.class).get(position);
        tKanji.setText(t.getName());
        tDesc.setText(t.getDescription());

        return convertView;
    }

    @Override
    public int getCount() {
        return (int) Traduccion.count(Traduccion.class);
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
