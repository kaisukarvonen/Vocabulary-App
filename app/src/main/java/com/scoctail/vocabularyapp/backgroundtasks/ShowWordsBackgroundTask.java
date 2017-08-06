package com.scoctail.vocabularyapp.backgroundtasks;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import com.scoctail.vocabularyapp.beans.Word;
import com.scoctail.vocabularyapp.contract.VocabularyContract;
import com.scoctail.vocabularyapp.database.DatabaseHelper;
import com.scoctail.vocabularyapp.R;
import com.scoctail.vocabularyapp.adapters.WordAdapter;

/**
 * Created by Kaisu on 30/3/17.
 */

public class ShowWordsBackgroundTask extends AsyncTask<String, Word, Boolean> {

    WordAdapter wa;
    Context ctx;
    ListView lv;

    public ShowWordsBackgroundTask(Context ctx, WordAdapter wa) {
        this.ctx = ctx;
        this.wa = wa;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        String method = params[0];
        if(method.equals("showWords")) {
            DatabaseHelper dbhelper = new DatabaseHelper(ctx);
            Cursor c = dbhelper.getWordsByLanguage(1);
            String name, translation;

            while (c.moveToNext()) {
                name = c.getString(c.getColumnIndex(VocabularyContract.KEY_NAME));
                translation = c.getString(c.getColumnIndex(VocabularyContract.WordEntry.KEY_TRANSLATION));
                Word word = new Word(name,translation);
                publishProgress(word);
            }
            return true;
        }


        return false;
    }

    @Override
    protected void onProgressUpdate(Word... values) {

        wa.add(values[0]);
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if(!result) {
            Toast.makeText(ctx, "Error displaying words", Toast.LENGTH_LONG).show();
        }
    }
}
