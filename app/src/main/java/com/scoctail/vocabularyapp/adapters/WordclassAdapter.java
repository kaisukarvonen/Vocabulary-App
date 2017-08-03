package com.scoctail.vocabularyapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.scoctail.vocabularyapp.R;
import com.scoctail.vocabularyapp.beans.Theme;
import com.scoctail.vocabularyapp.beans.WordClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kaisu on 30/3/17.
 */

public class WordclassAdapter extends ArrayAdapter {
    List list = new ArrayList();
    int resource;
    Context ctx;

    public WordclassAdapter(Context context, int resource) {
        super(context, resource);
        this.resource = resource;
        this.ctx = context;
    }

    public void add(WordClass object) {
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
        WordclassHolder th = null;
        if (row == null) {
            LayoutInflater lainf = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = lainf.inflate(resource, parent, false);
            th = new WordclassHolder();
            th.name = (TextView)row.findViewById(R.id.wordclass_name);
            row.setTag(th);

        } else {
            th = (WordclassHolder)row.getTag();
        }
        WordClass wc = (WordClass)getItem(position);
        th.name.setText(wc.getName().toString());
        return row;
    }

    static class WordclassHolder {
        TextView name;
    }
}
