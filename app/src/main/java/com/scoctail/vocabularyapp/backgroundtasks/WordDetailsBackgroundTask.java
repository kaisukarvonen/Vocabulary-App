package com.scoctail.vocabularyapp.backgroundtasks;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.scoctail.vocabularyapp.R;
import com.scoctail.vocabularyapp.beans.Word;
import com.scoctail.vocabularyapp.contract.VocabularyContract;
import com.scoctail.vocabularyapp.database.DatabaseHelper;

import org.w3c.dom.Text;

import static com.scoctail.vocabularyapp.R.id.word;

/**
 * Created by Kaisu on 7/4/17.
 */

public class WordDetailsBackgroundTask extends AsyncTask<String, Void, Word> {
    Context ctx;
    Activity ac;

    public WordDetailsBackgroundTask(Context ctx) {
        this.ctx = ctx;
        this.ac = (Activity)ctx;
    }

    @Override
    protected Word doInBackground(String... params) {
        String method = params[0];
        DatabaseHelper helper = new DatabaseHelper(ctx);

        if (method.equals("showDetails")) {
            Word word = helper.getWord(params[1]);
            return word;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Word word) {
        TextView t_name, t_translation, t_conjugation, t_examples, t_wordclass;
        if(word != null) {
            t_name = (TextView)ac.findViewById(R.id.w_name);
            t_conjugation = (TextView)ac.findViewById(R.id.w_conjugation);
            t_translation = (TextView)ac.findViewById(R.id.w_translation);
            t_examples = (TextView)ac.findViewById(R.id.w_examples);
            t_wordclass = (TextView)ac.findViewById(R.id.w_wordclass);

            if (word.getExamples().isEmpty()) {
                t_examples.setVisibility(TextView.INVISIBLE);
                (ac.findViewById(R.id.w_examples_title)).setVisibility(TextView.INVISIBLE);
            } else {
                t_examples.setText(word.getExamples());
            }

            if (word.getConjugation().isEmpty()) {
                t_conjugation.setVisibility(TextView.INVISIBLE);
                (ac.findViewById(R.id.w_conjugation_title)).setVisibility(TextView.INVISIBLE);
            } else {
                t_conjugation.setText(word.getConjugation());
            }

            if (word.getWordclass().isEmpty()) {
                t_wordclass.setVisibility(TextView.INVISIBLE);
                (ac.findViewById(R.id.w_wordclass_title)).setVisibility(TextView.INVISIBLE);
            } else {
                t_wordclass.setText(word.getWordclass());
            }

            t_name.setText(word.getName());
            t_translation.setText(word.getTranslation());

        } else {
            Toast.makeText(ctx, "Error displaying the word", Toast.LENGTH_SHORT).show();
        }
    }

}
