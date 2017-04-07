package com.scoctail.vocabularyapp.backgroundtasks;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by Kaisu on 7/4/17.
 */

public class WordDetailsBackgroundTask extends AsyncTask<String, Void, String> {
    Context ctx;

    public WordDetailsBackgroundTask(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        String method = params[0];

        if (method.equals("showDetails")) {


            return "showDetails";
        }
        return null;
    }
}
