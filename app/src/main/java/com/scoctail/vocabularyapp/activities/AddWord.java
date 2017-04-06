package com.scoctail.vocabularyapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.scoctail.vocabularyapp.R;
import com.scoctail.vocabularyapp.backgroundtasks.AddWordBackgroundTask;
import com.scoctail.vocabularyapp.beans.Word;

public class AddWord extends AppCompatActivity {
    private EditText et_word, et_translation, et_conjugation, et_examples;
    private String name,translation,conjugation,examples;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_word);
        et_word = (EditText)findViewById(R.id.word);
        et_translation = (EditText)findViewById(R.id.translation);
        et_conjugation = (EditText)findViewById(R.id.conjugation);
        et_examples = (EditText)findViewById(R.id.examples);
    }

    public void saveNewWord(View v) {
        initialize();

        if(!validateInput()) {
            Toast.makeText(this, "Please fill out all mandatory fields!", Toast.LENGTH_SHORT).show();
        } else {
            //save the word to database
            AddWordBackgroundTask bg = new AddWordBackgroundTask(this);
            bg.execute("addWord", name, translation, examples, conjugation);

        }


    }

    public void initialize() {
        name = et_word.getText().toString().trim();
        translation = et_translation.getText().toString().trim();
        conjugation= et_conjugation.getText().toString().trim();
        examples = et_examples.getText().toString().trim();
    }

    public boolean validateInput() {
        if (name.isEmpty() || translation.isEmpty()) {
            return false;
        }
        return true;
    }
}
