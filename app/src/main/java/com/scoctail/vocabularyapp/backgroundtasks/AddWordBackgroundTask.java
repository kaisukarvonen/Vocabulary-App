package com.scoctail.vocabularyapp.backgroundtasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.scoctail.vocabularyapp.beans.Word;
import com.scoctail.vocabularyapp.database.DatabaseHelper;

/**
 * Created by Kaisu on 6/4/17.
 */

public class AddWordBackgroundTask extends AsyncTask<String, Void, String> {
    Context ctx;

    public AddWordBackgroundTask(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        String method = params[0];
        DatabaseHelper helper = new DatabaseHelper(ctx);

        if(method.equals("addWord")) {
            String name = params[1];
            String translation = params[2];
            String conjugation = params[3];
            String examples = params[4];
            String wordClass = params[5];
            Log.d("wordclass", wordClass);
            Word word = new Word(name,translation,examples,conjugation);
            helper.addWord(word, 1, 0, Integer.parseInt(wordClass)); //wordclass id?
            return "New word added to vocabulary!";
        }

        return null;
    }

    protected void onPostExecute(String result) {
        Toast.makeText(ctx, result, Toast.LENGTH_SHORT).show();
    }
}
