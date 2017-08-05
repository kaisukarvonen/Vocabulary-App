package com.scoctail.vocabularyapp.backgroundtasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.scoctail.vocabularyapp.adapters.LanguageAdapter;
import com.scoctail.vocabularyapp.adapters.ThemeAdapter;
import com.scoctail.vocabularyapp.beans.Language;
import com.scoctail.vocabularyapp.beans.Theme;
import com.scoctail.vocabularyapp.database.DatabaseHelper;

/**
 * Created by Kaisu on 6/4/17.
 */

public class AddLanguageBackgroundTask extends AsyncTask<String, Void, Language> {
    Context ctx;
    LanguageAdapter la;

    public AddLanguageBackgroundTask(Context ctx, LanguageAdapter adapter) {
        this.ctx = ctx;
        this.la = adapter;
    }


    @Override
    protected Language doInBackground(String... params) {
        String method = params[0];
        DatabaseHelper helper = new DatabaseHelper(ctx);

        if(method.equals("addLanguage")) {
            String name = params[1];
            if (helper.addLanguage(name)) {
                return new Language(name);
            } else {
                return null;
            }
        }

        return null;
    }

    protected void onPostExecute(Language addedLanguage) {
        if (addedLanguage == null) {
            Toast.makeText(ctx, "This theme is already added!", Toast.LENGTH_LONG).show();
        } else {
            la.add(addedLanguage);
            la.notifyDataSetChanged();
        }

    }
}
