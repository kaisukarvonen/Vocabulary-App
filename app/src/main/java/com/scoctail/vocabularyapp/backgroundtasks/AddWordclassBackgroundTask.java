package com.scoctail.vocabularyapp.backgroundtasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

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
            helper.addWordclass(name);
            return new WordClass(name);
        }

        return null;
    }

    protected void onPostExecute(WordClass addedWordclass) {
        ta.add(addedWordclass);
        ta.notifyDataSetChanged();

    }
}
