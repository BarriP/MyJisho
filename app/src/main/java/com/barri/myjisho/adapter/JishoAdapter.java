package com.barri.myjisho.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.barri.myjisho.R;
import com.barri.myjisho.model.Traduccion;

import java.util.List;

/**
 * Created by Barri on 23/03/2016.
 */
public class JishoAdapter extends BaseAdapter {

    private Context context;
    private List<Traduccion> list;

    public JishoAdapter(Context context, List<Traduccion> list){
        super();
        this.context = context;
        this.list = list;
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

        Traduccion t = list.get(position);
        tKanji.setText(t.getName());
        tDesc.setText(t.getDescription());

        return convertView;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
