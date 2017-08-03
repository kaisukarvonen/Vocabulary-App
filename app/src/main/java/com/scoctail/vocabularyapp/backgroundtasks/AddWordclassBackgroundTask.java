package com.scoctail.vocabularyapp.backgroundtasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.scoctail.vocabularyapp.activities.WordclassesFragment;
import com.scoctail.vocabularyapp.adapters.WordclassAdapter;
import com.scoctail.vocabularyapp.beans.Theme;
import com.scoctail.vocabularyapp.beans.WordClass;
import com.scoctail.vocabularyapp.database.DatabaseHelper;

/**
 * Created by Kaisu on 6/4/17.
 */

public class AddWordclassBackgroundTask extends AsyncTask<String, Void, WordClass> {
    Context ctx;
    WordclassAdapter ta;

    public AddWordclassBackgroundTask(Context ctx, WordclassAdapter adapter) {
        this.ctx = ctx;
        this.ta = adapter;
    }


    @Override
    protected WordClass doInBackground(String... params) {
        String method = params[0];
        DatabaseHelper helper = new DatabaseHelper(ctx);

        if(method.equals("addWordclass")) {
            String name = params[1];
            if (helper.addWordclass(name)) {
                return new WordClass(name);
            } else {
                return null;
            }
        }

        return null;
    }

    protected void onPostExecute(WordClass addedWordclass) {
        if (addedWordclass == null) {
            Toast.makeText(ctx, "This theme is already added!", Toast.LENGTH_LONG).show();
        } else {
            ta.add(addedWordclass);
            ta.notifyDataSetChanged();
        }

    }
}
