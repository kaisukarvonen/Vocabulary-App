package com.scoctail.vocabularyapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.scoctail.vocabularyapp.R;
import com.scoctail.vocabularyapp.beans.Language;
import com.scoctail.vocabularyapp.beans.Word;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by Kaisu on 30/3/17.
 */

public class SectionAdapter extends ArrayAdapter {
    private List<Word> list = new ArrayList();
    private TreeSet<Integer> sectionHeader = new TreeSet<Integer>();
    int resource;
    Context ctx;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;
    LayoutInflater lainf;

    public SectionAdapter(Context context, int resource) {
        super(context, resource);
        this.resource = resource;
        this.ctx = context;
        lainf = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void add(Word object) {
        list.add(object);
        super.add(object);
    }

    public void addSectionHeader(Word title) {
        list.add(title);
        sectionHeader.add(list.size()-1);
    }

    public void removeSectionHeaders() {

        int position=0;
        Iterator<Word> iter = list.iterator();
        while(iter.hasNext()) {
            iter.next();
            if (getItemViewType(position) == 1) {
                iter.remove();
            }
            position++;
        }
    }

    public int getItemViewType(int position) {
        return sectionHeader.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
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
        WordHolder wh;
        int rowType = getItemViewType(position);
        if (convertView == null) {
            wh = new WordHolder();
            switch (rowType) {
                case TYPE_SEPARATOR:
                    convertView = lainf.inflate(R.layout.wordlist_title, null);
                    wh.tx_name = (TextView)convertView.findViewById(R.id.section_header);
                    break;
                case TYPE_ITEM:
                    convertView = lainf.inflate(resource, parent, false);
                    wh.tx_name = (TextView)convertView.findViewById(R.id.t_name);
                    wh.tx_translation = (TextView)convertView.findViewById(R.id.t_translation);
            }

            convertView.setTag(wh);

        } else {
            wh = (WordHolder)convertView.getTag();
        }
        Word word = (Word) getItem(position);
        if (word.getName().toString().isEmpty()) {
            word.setName("Others");
        }
        wh.tx_name.setText(word.getName().toString());
        if (wh.tx_translation != null) {
            wh.tx_translation.setText(word.getTranslation().toString());
        }
        return convertView;
    }

    static class WordHolder {
        TextView tx_translation, tx_name;
    }
}
