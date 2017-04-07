package com.scoctail.vocabularyapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.scoctail.vocabularyapp.beans.Word;
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

    public void add(Word object) {
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
            wh.tx_translation = (TextView)row.findViewById(R.id.t_translation);
            wh.tx_name = (TextView)row.findViewById(R.id.t_name);
            row.setTag(wh);
        } else {
            wh = (WordHolder)row.getTag();
        }

        Word word = (Word)getItem(position);
        wh.tx_translation.setText(word.getTranslation().toString());
        wh.tx_name.setText(word.getName().toString());


        return row;
    }

    static class WordHolder {
        TextView tx_translation, tx_name;

        public String toString() {
            return "wordholder";
        }
    }
}
