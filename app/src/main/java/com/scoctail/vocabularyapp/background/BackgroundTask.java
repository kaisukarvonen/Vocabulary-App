package com.scoctail.vocabularyapp.background;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import com.scoctail.vocabularyapp.database.DatabaseHelper;
import com.scoctail.vocabularyapp.R;
import com.scoctail.vocabularyapp.adapters.WordAdapter;
import com.scoctail.vocabularyapp.beans.Language;

/**
 * Created by Kaisu on 30/3/17.
 */

public class BackgroundTask extends AsyncTask<String, Language, String> {

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

            DatabaseHelper dbhelper = new DatabaseHelper(ctx);
            lv = (ListView) ac.findViewById(R.id.display_listview);
            wa = new WordAdapter(ctx, R.layout.word_row);
            Cursor c = dbhelper.getLanguages();
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

    @Override
    protected void onProgressUpdate(Language... values) {

        wa.add(values[0]);
    }

    @Override
    protected void onPostExecute(String result) {
        if(result.equals("getLanguages")) {
            //Log.d("onPostExecute?", "true");
            lv.setAdapter(wa);
        } else {
            Toast.makeText(ctx, result, Toast.LENGTH_LONG);
        }
    }
}
