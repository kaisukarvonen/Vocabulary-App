package com.scoctail.vocabularyapp.backgroundtasks;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.scoctail.vocabularyapp.adapters.LanguageAdapter;
import com.scoctail.vocabularyapp.adapters.ThemeAdapter;
import com.scoctail.vocabularyapp.beans.Language;
import com.scoctail.vocabularyapp.beans.Theme;
import com.scoctail.vocabularyapp.database.DatabaseHelper;

import java.util.List;

/**
 * Created by Kaisu on 30/3/17.
 */

public class ShowLanguagesBackgroundTask extends AsyncTask<String, Language, Boolean> {

    LanguageAdapter la;
    Context ctx;
    Activity ac;

    public ShowLanguagesBackgroundTask(Context ctx, LanguageAdapter adapter) {
        this.ctx = ctx;
        this.ac = (Activity)ctx;
        this.la = adapter;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        String method = params[0];
        if(method.equals("showLanguages")) {

            DatabaseHelper dbhelper = new DatabaseHelper(ctx);
            List<Language> languages = dbhelper.getLanguages();

            for (Language l : languages) {
                publishProgress(l);
            }
            return true;
        }
        return false;
    }

    @Override
    protected void onProgressUpdate(Language... values) {

        la.add(values[0]);
    }


    protected void onPostExecute(Boolean result) {
        if(!result) {
            Toast.makeText(ctx, "Error showing languages", Toast.LENGTH_LONG).show();
        }
    }
}
