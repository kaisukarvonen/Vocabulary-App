package com.scoctail.vocabularyapp.backgroundtasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.scoctail.vocabularyapp.adapters.ThemeAdapter;
import com.scoctail.vocabularyapp.beans.Theme;
import com.scoctail.vocabularyapp.beans.Word;
import com.scoctail.vocabularyapp.database.DatabaseHelper;

/**
 * Created by Kaisu on 6/4/17.
 */

public class AddThemeBackgroundTask extends AsyncTask<String, Void, Theme> {
    Context ctx;
    ThemeAdapter ta;

    public AddThemeBackgroundTask(Context ctx,ThemeAdapter adapter) {
        this.ctx = ctx;
        this.ta = adapter;
    }


    @Override
    protected Theme doInBackground(String... params) {
        String method = params[0];
        DatabaseHelper helper = new DatabaseHelper(ctx);

        if(method.equals("addTheme")) {
            String name = params[1];
            Log.d("new theme", name);
            helper.addTheme(name);
            return new Theme(name);
        }

        return null;
    }

    protected void onPostExecute(Theme addedTheme) {
        ta.add(addedTheme);
        ta.notifyDataSetChanged();

    }
}
