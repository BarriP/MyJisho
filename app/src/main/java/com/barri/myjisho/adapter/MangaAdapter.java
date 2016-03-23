package com.barri.myjisho.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.barri.myjisho.R;
import com.barri.myjisho.model.Coleccion;
import com.barri.myjisho.views.ChapterView;
import com.squareup.picasso.Picasso;

/**
 * Implementacion Placeholder para pruebas
 */
public class MangaAdapter extends BaseAdapter{

    private final Context context;

    public MangaAdapter(Context context){
        this.context=context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.manga_cell, null);
        }

        ImageView iv = (ImageView) convertView.findViewById(R.id.picture);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, String.valueOf(position), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ChapterView.class);
                intent.putExtra("pos",position);
                context.startActivity(intent);
            }
        });
        iv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(context, "way too long", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        Picasso.with(context).load(R.drawable.manga).into(iv);
        TextView tv = (TextView) convertView.findViewById(R.id.pictureText);
        tv.setText(Coleccion.listAll(Coleccion.class).get(position).getName());

        return convertView;
    }

    @Override
    public int getCount() {
        return (int) Coleccion.count(Coleccion.class);
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
