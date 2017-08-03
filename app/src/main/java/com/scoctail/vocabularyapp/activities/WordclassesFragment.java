package com.scoctail.vocabularyapp.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.scoctail.vocabularyapp.R;
import com.scoctail.vocabularyapp.adapters.ThemeAdapter;
import com.scoctail.vocabularyapp.adapters.WordclassAdapter;
import com.scoctail.vocabularyapp.backgroundtasks.AddThemeBackgroundTask;
import com.scoctail.vocabularyapp.backgroundtasks.AddWordclassBackgroundTask;
import com.scoctail.vocabularyapp.backgroundtasks.ShowThemesBackgroundTask;
import com.scoctail.vocabularyapp.backgroundtasks.ShowWordclassesBackgroundTask;

/**
 * Created by Kaisu on 1.8.2017.
 */

public class WordclassesFragment extends Fragment
{
    View view;
    ListView lv;
    WordclassAdapter adapter;
    private EditText et_name;
    private String name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.wordclasses_list, container,false);

        adapter = new WordclassAdapter(getContext(), R.layout.wordclass_row);
        lv = (ListView)view.findViewById(R.id.wordclasses_listview);
        lv.setAdapter(adapter);
        ShowWordclassesBackgroundTask bg = new ShowWordclassesBackgroundTask(this.getContext(), adapter);
        bg.execute("showWordclasses");


        ImageButton btn = (ImageButton)view.findViewById(R.id.add_new_wordclass_btn);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                et_name = (EditText)view.findViewById(R.id.new_wordclass_name);
                saveNewWordclass(v);
                et_name.setText("");
            }
        });

        return view;
    }


    public void saveNewWordclass(View v) {
        name = et_name.getText().toString().trim();

        if(name.isEmpty()) {
            Toast.makeText(this.getContext(), "Please fill out wordclass name!", Toast.LENGTH_SHORT).show();
        } else {
            AddWordclassBackgroundTask bg = new AddWordclassBackgroundTask(this.getContext(),adapter);
            bg.execute("addWordclass", name);

        }


    }

}
