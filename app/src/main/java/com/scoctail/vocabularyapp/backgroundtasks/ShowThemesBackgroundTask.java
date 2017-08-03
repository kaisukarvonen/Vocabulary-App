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

public class ShowThemesBackgroundTask extends AsyncTask<String, Theme, Boolean> {

    ThemeAdapter ta;
    Context ctx;
    Activity ac;

    public ShowThemesBackgroundTask(Context ctx, ThemeAdapter adapter) {
        this.ctx = ctx;
        this.ac = (Activity)ctx;
        this.ta = adapter;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        String method = params[0];
        if(method.equals("showThemes")) {

            DatabaseHelper dbhelper = new DatabaseHelper(ctx);
            List<Theme> themes = dbhelper.getThemes();

            for (Theme t : themes) {
                publishProgress(t);
            }
            return true;
        }
        return false;
    }

    @Override
    protected void onProgressUpdate(Theme... values) {

        ta.add(values[0]);
    }


    protected void onPostExecute(Boolean result) {
        if(!result) {
            Toast.makeText(ctx, "Error showing themes", Toast.LENGTH_LONG).show();
        }
    }
}
