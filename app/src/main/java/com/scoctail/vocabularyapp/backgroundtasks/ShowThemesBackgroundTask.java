package com.scoctail.vocabularyapp.backgroundtasks;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.scoctail.vocabularyapp.R;
import com.scoctail.vocabularyapp.adapters.ThemeAdapter;
import com.scoctail.vocabularyapp.adapters.WordAdapter;
import com.scoctail.vocabularyapp.beans.Theme;
import com.scoctail.vocabularyapp.beans.Word;
import com.scoctail.vocabularyapp.contract.VocabularyContract;
import com.scoctail.vocabularyapp.database.DatabaseHelper;

import java.util.List;

/**
 * Created by Kaisu on 30/3/17.
 */

public class ShowThemesBackgroundTask extends AsyncTask<String, Theme, String> {

    ThemeAdapter ta;
    Context ctx;
    Activity ac;
    ListView lv;

    public ShowThemesBackgroundTask(Context ctx) {
        this.ctx = ctx;
        this.ac = (Activity)ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        String method = params[0];
        if(method.equals("showThemes")) {

            DatabaseHelper dbhelper = new DatabaseHelper(ctx);
            lv = (ListView) ac.findViewById(R.id.themes_listview);
            ta = new ThemeAdapter(ctx, R.layout.theme_row);
            List<Theme> themes = dbhelper.getThemes();

            for (Theme t : themes) {
                publishProgress(t);
                Log.d("theme", t.getName());
            }
            Log.d("themes bg", "doInBackground"+themes.size()+"--");
            return "showThemes";
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Theme... values) {

        ta.add(values[0]);
    }

    @Override
    protected void onPostExecute(String result) {
        if(result.equals("showThemes")) {
            lv.setAdapter(ta);
        } else {
            Toast.makeText(ctx, result, Toast.LENGTH_LONG);
        }
    }
}
