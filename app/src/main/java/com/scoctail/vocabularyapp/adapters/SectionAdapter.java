package com.scoctail.vocabularyapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.scoctail.vocabularyapp.R;
import com.scoctail.vocabularyapp.beans.Language;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by Kaisu on 30/3/17.
 */

public class SectionAdapter extends ArrayAdapter {
    private List list = new ArrayList();
    private TreeSet<Integer> sectionHeader = new TreeSet<Integer>();
    int resource;
    Context ctx;

    public SectionAdapter(Context context, int resource) {
        super(context, resource);
        this.resource = resource;
        this.ctx = context;
    }

    public void add(Language object) {
        list.add(object);
        super.add(object);
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LanguageHolder lh = null;
        if (row == null) {
            LayoutInflater lainf = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = lainf.inflate(resource, parent, false);
            lh = new LanguageHolder();
            lh.name = (TextView)row.findViewById(R.id.language_name);
            row.setTag(lh);

        } else {
            lh = (LanguageHolder)row.getTag();
        }
        Language language = (Language) getItem(position);
        lh.name.setText(language.getName().toString());
        return row;
    }

    static class LanguageHolder {
        TextView name;
    }
}
