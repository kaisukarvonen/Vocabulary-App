package com.scoctail.vocabularyapp.backgroundtasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.scoctail.vocabularyapp.activities.NavigationDrawer;
import com.scoctail.vocabularyapp.adapters.WordAdapter;
import com.scoctail.vocabularyapp.beans.Language;
import com.scoctail.vocabularyapp.beans.Theme;
import com.scoctail.vocabularyapp.beans.Word;
import com.scoctail.vocabularyapp.beans.WordClass;
import com.scoctail.vocabularyapp.database.DatabaseHelper;

/**
 * Created by Kaisu on 6/4/17.
 */

public class AddWordBackgroundTask extends AsyncTask<String, Void, Word> {
    Context ctx;

    public AddWordBackgroundTask(Context ctx ) {
        this.ctx = ctx;
    }

    @Override
    protected Word doInBackground(String... params) {
        String method = params[0];
        DatabaseHelper helper = new DatabaseHelper(ctx);

        if(method.equals("addWord")) {
            String name = params[1];
            String translation = params[2];
            String conjugation = params[3];
            String examples = params[4];
            String wordClass = params[5];
            String theme = params[6];
            Log.d("wordclass, theme", wordClass+", "+theme);
            String language = helper.readFromInternalStorage(ctx, "language_id");
            Log.d("language_id", language);
            Word word = new Word(name,translation,examples,conjugation,new WordClass(Integer.parseInt(wordClass)), new Theme(Integer.parseInt(theme)), new Language(Integer.parseInt(language)));

            if (helper.addWord(word)) {
                return word;
            }
        }
        return null;
    }

    protected void onPostExecute(Word word) {
        if(word == null) {
            Toast.makeText(ctx, "You have already added this word!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ctx, "Word added to vocabulary!", Toast.LENGTH_SHORT).show();
            final Intent i = new Intent(this.ctx, NavigationDrawer.class);
            Thread thread = new Thread() {
                public void run() {
                    try {
                        Thread.sleep(2000);
                        ctx.startActivity(i);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
        }
    }

}
