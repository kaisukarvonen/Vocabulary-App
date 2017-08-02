package com.scoctail.vocabularyapp.activities;

import android.content.Intent;
import android.media.Image;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.scoctail.vocabularyapp.R;
import com.scoctail.vocabularyapp.adapters.ThemeAdapter;
import com.scoctail.vocabularyapp.backgroundtasks.AddThemeBackgroundTask;
import com.scoctail.vocabularyapp.backgroundtasks.AddWordBackgroundTask;
import com.scoctail.vocabularyapp.backgroundtasks.ShowThemesBackgroundTask;
import com.scoctail.vocabularyapp.backgroundtasks.ShowWordsBackgroundTask;
import com.scoctail.vocabularyapp.beans.Theme;

/**
 * Created by Kaisu on 1.8.2017.
 */

public class ThemesFragment extends Fragment
{
    View view;
    ListView lv;
    ThemeAdapter adapter;
    private EditText et_name;
    private String name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.themes_list, container,false);

        ShowThemesBackgroundTask bg = new ShowThemesBackgroundTask(this.getContext());
        bg.execute("showThemes");
        lv = (ListView)view.findViewById(R.id.themes_listview);
        lv.setAdapter(adapter);


        ImageButton btn = (ImageButton)view.findViewById(R.id.add_new_theme_btn);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                et_name = (EditText)view.findViewById(R.id.new_theme_name);
                saveNewTheme(v);
            }
        });

        return view;
    }


    public void saveNewTheme(View v) {
        name = et_name.getText().toString().trim();

        if(name.isEmpty()) {
            Toast.makeText(this.getContext(), "Please fill out theme name!", Toast.LENGTH_SHORT).show();
        } else {
            AddThemeBackgroundTask bg = new AddThemeBackgroundTask(this.getContext());
            bg.execute("addTheme", name);

        }


    }


}
