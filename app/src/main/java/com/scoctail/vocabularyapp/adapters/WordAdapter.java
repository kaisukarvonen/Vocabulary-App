package com.scoctail.vocabularyapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.scoctail.vocabularyapp.beans.Language;
import com.scoctail.vocabularyapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kaisu on 30/3/17.
 */

public class WordAdapter extends ArrayAdapter {
    List list = new ArrayList();

    public WordAdapter( Context context, int resource) {
        super(context, resource);
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
        WordHolder wh;
        if (row == null) {
            LayoutInflater lainf = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = lainf.inflate(R.layout.word_row, parent, false);
            wh = new WordHolder();
            wh.tx_id = (TextView)row.findViewById(R.id.t_id);
            wh.tx_name = (TextView)row.findViewById(R.id.t_name);
            row.setTag(wh);
        } else {
            wh = (WordHolder)row.getTag();
        }

        Language la = (Language)getItem(position);
        wh.tx_id.setText(Integer.toString(la.getId())); //NullPointerException?
        wh.tx_name.setText(la.getName().toString());


        return row;
    }

    static class WordHolder {
        TextView tx_id, tx_name;

        public String toString() {
            return "wordholder";
        }
    }
}
