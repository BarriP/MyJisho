package com.barri.myjisho.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;

import com.barri.myjisho.R;
import com.barri.myjisho.model.Capitulo;
import com.barri.myjisho.model.Coleccion;
import com.barri.myjisho.model.Traduccion;
import com.barri.myjisho.views.TranslationsView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Barri on 23/03/2016.
 */
public class TranslationAdapter extends BaseExpandableListAdapter {

    private final Capitulo capitulo;
    private Context context;
    private final List<Integer> pages = new ArrayList<>();
    private final Map<Integer,List<Traduccion>> childs = new HashMap<>();

    public TranslationAdapter(Context context, Capitulo capitulo){
        super();
        this.context = context;
        this.capitulo = capitulo;
        loadData();
    }

    @Override
    public int getGroupCount() {
        return pages.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childs.get(pages.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return pages.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childs.get(groupPosition).get(childPosition);
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 64);

        TextView textView = new TextView(context);
        textView.setLayoutParams(lp);
        // Center the text vertically
        textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        // Set the text starting position
        textView.setPadding(36, 0, 0, 0);
        textView.setText("Pagina " + pages.get(groupPosition));
        return textView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.translation_cell, null);
        }

        TextView tKanji = (TextView) convertView.findViewById(R.id.kanji);
        TextView tDesc = (TextView) convertView.findViewById(R.id.decripcion);

        final Traduccion t = childs.get(pages.get(groupPosition)).get(childPosition);
        tKanji.setText(t.getName());
        tDesc.setText(t.getDescription());

        return convertView;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public void loadData() {
        pages.clear();
        childs.clear();
        List<Traduccion> traducciones = capitulo.getTraducciones();
        for (Traduccion t : traducciones) {

            List<Traduccion> list;
            if(!pages.contains(t.getPage())) {
                pages.add(t.getPage());
                list = new ArrayList<>();
                list.add(t);
                childs.put(t.getPage(),list);

            } else {
                list = childs.get(t.getPage());
                list.add(t);
            }
        }
        Collections.sort(pages);
    }

    @Override
    public void notifyDataSetChanged() {
        loadData();
        super.notifyDataSetChanged();
    }
}
