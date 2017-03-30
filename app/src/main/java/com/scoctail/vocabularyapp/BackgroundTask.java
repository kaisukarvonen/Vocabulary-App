package com.scoctail.vocabularyapp;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Kaisu on 30/3/17.
 */

public class BackgroundTask extends AsyncTask<String, Language, String> {
    DatabaseHelper db;
    WordAdapter wa;
    Context ctx;
    Activity ac;
    ListView lv;

    public BackgroundTask(Context ctx) {
        this.ctx = ctx;
        this.ac = (Activity)ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        String method = params[0];
        if(method == "getLanguages") {
            lv = (ListView) ac.findViewById(R.id.display_listview);
            wa = new WordAdapter(ctx, R.layout.display_words);
            Cursor c = db.getLanguages();
            int id;
            String name;
            while (c.moveToNext()) {
                id = c.getInt(c.getColumnIndex(DatabaseHelper.KEY_ID));
                name = c.getString(c.getColumnIndex(DatabaseHelper.KEY_NAME));
                Language l = new Language(id, name);
                publishProgress(l);
            }
            return "getLanguages";
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Language... values) {
        wa.add(values[0]);
    }

    @Override
    protected void onPostExecute(String result) {
        if(result == "getLanguages") {
            lv.setAdapter(wa);
        } else {
            Toast.makeText(ctx, result, Toast.LENGTH_LONG);
        }
    }
}
