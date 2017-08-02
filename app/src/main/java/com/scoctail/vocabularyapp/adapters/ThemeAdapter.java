package com.scoctail.vocabularyapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.scoctail.vocabularyapp.R;
import com.scoctail.vocabularyapp.beans.Theme;
import com.scoctail.vocabularyapp.beans.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kaisu on 30/3/17.
 */

public class ThemeAdapter extends ArrayAdapter {
    List list = new ArrayList();


    public ThemeAdapter(Context context, int resource) {
        super(context, resource);
    }

    public void add(Theme object) {
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
        TextView name;
        View row = convertView;
        if (row == null) {
            LayoutInflater lainf = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = lainf.inflate(R.layout.theme_row, parent, false);
            name = (TextView)row.findViewById(R.id.theme_name);
            Theme theme = (Theme)getItem(position);
            name.setText(theme.getName().toString());

        }

        return row;
    }
}
