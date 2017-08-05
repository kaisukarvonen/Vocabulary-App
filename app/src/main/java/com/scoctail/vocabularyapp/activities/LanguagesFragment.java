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
import com.scoctail.vocabularyapp.adapters.LanguageAdapter;
import com.scoctail.vocabularyapp.backgroundtasks.AddLanguageBackgroundTask;
import com.scoctail.vocabularyapp.backgroundtasks.AddThemeBackgroundTask;
import com.scoctail.vocabularyapp.backgroundtasks.ShowLanguagesBackgroundTask;
import com.scoctail.vocabularyapp.backgroundtasks.ShowThemesBackgroundTask;

/**
 * Created by Kaisu on 1.8.2017.
 */

public class LanguagesFragment extends Fragment
{
    View view;
    ListView lv;
    LanguageAdapter adapter;
    private EditText et_name;
    private String name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.languages_list, container,false);

        adapter = new LanguageAdapter(getContext(), R.layout.language_row);
        lv = (ListView)view.findViewById(R.id.languages_listview);
        lv.setAdapter(adapter);
        ShowLanguagesBackgroundTask bg = new ShowLanguagesBackgroundTask(this.getContext(), adapter);
        bg.execute("showLanguages");


        ImageButton btn = (ImageButton)view.findViewById(R.id.add_new_language_btn);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                et_name = (EditText)view.findViewById(R.id.new_language_name);
                saveNewLanguage(v);
                et_name.setText("");
            }
        });

        return view;
    }


    public void saveNewLanguage(View v) {
        name = et_name.getText().toString().trim();

        if(name.isEmpty()) {
            Toast.makeText(this.getContext(), "Please fill out language name!", Toast.LENGTH_SHORT).show();
        } else {
            AddLanguageBackgroundTask bg = new AddLanguageBackgroundTask(this.getContext(),adapter);
            bg.execute("addLanguage", name);

        }


    }

}
