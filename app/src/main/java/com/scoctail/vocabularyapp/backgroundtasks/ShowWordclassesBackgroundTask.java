package com.scoctail.vocabularyapp.backgroundtasks;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import com.scoctail.vocabularyapp.R;
import com.scoctail.vocabularyapp.adapters.ThemeAdapter;
import com.scoctail.vocabularyapp.adapters.WordclassAdapter;
import com.scoctail.vocabularyapp.beans.Theme;
import com.scoctail.vocabularyapp.beans.WordClass;
import com.scoctail.vocabularyapp.database.DatabaseHelper;

import java.util.List;

/**
 * Created by Kaisu on 30/3/17.
 */

public class ShowWordclassesBackgroundTask extends AsyncTask<String, WordClass, Boolean> {

    WordclassAdapter wa;
    Context ctx;
    Activity ac;

    public ShowWordclassesBackgroundTask(Context ctx, WordclassAdapter adapter) {
        this.ctx = ctx;
        this.ac = (Activity)ctx;
        this.wa = adapter;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        String method = params[0];
        if(method.equals("showWordclasses")) {

            DatabaseHelper dbhelper = new DatabaseHelper(ctx);
            List<WordClass> wordclasses = dbhelper.getWordclasses();

            for (WordClass wc : wordclasses) {
                publishProgress(wc);
            }
            return true;
        }
        return false;
    }

    @Override
    protected void onProgressUpdate(WordClass... values) {

        wa.add(values[0]);
    }


    protected void onPostExecute(Boolean result) {
        if(!result) {
            Toast.makeText(ctx, "Error showing word classes", Toast.LENGTH_LONG).show();
        }
    }
}
