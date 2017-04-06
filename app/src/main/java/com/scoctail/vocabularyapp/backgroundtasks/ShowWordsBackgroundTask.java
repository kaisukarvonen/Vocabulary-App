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
import com.scoctail.vocabularyapp.beans.Language;

import static android.R.attr.id;

/**
 * Created by Kaisu on 30/3/17.
 */

public class ShowWordsBackgroundTask extends AsyncTask<String, Word, String> {

    WordAdapter wa;
    Context ctx;
    Activity ac;
    ListView lv;

    public ShowWordsBackgroundTask(Context ctx) {
        this.ctx = ctx;
        this.ac = (Activity)ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        String method = params[0];
        if(method.equals("showWords")) {
            DatabaseHelper dbhelper = new DatabaseHelper(ctx);
            lv = (ListView) ac.findViewById(R.id.display_listview);
            wa = new WordAdapter(ctx, R.layout.word_row);
            Cursor c = dbhelper.getWordsByLanguage(1);
            String name, translation;

            while (c.moveToNext()) {
                name = c.getString(c.getColumnIndex(VocabularyContract.KEY_NAME));
                translation = c.getString(c.getColumnIndex(VocabularyContract.WordEntry.KEY_TRANSLATION));
                Word word = new Word(name,translation);
                publishProgress(word);
            }
            return "showWords";
        }


        return null;
    }

    @Override
    protected void onProgressUpdate(Word... values) {

        wa.add(values[0]);
    }

    @Override
    protected void onPostExecute(String result) {
        if(result.equals("showWords")) {
            lv.setAdapter(wa);
        } else {
            Toast.makeText(ctx, result, Toast.LENGTH_LONG);
        }
    }
}
