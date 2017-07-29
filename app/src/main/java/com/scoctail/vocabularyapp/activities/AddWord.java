package com.scoctail.vocabularyapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.scoctail.vocabularyapp.R;
import com.scoctail.vocabularyapp.backgroundtasks.AddWordBackgroundTask;
import com.scoctail.vocabularyapp.beans.Word;
import com.scoctail.vocabularyapp.beans.WordClass;
import com.scoctail.vocabularyapp.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class AddWord extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {
    private EditText et_word, et_translation, et_conjugation, et_examples;
    private String name,translation,conjugation,examples,spinnerValue;
    private Spinner spinner;
    private WordClass selectedWordClass = new WordClass();
    private List<WordClass> wordclasses = new ArrayList<WordClass>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_word);
        et_word = (EditText)findViewById(R.id.word);
        et_translation = (EditText)findViewById(R.id.translation);
        et_conjugation = (EditText)findViewById(R.id.conjugation);
        et_examples = (EditText)findViewById(R.id.examples);
        spinner = (Spinner) findViewById((R.id.wordclass_spinner));
        spinner.setOnItemSelectedListener(this);
        showSpinnerOptions();
    }

    public void showSpinnerOptions() {

        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        wordclasses = db.getWordclassesString();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.add("Select word class");
        for (int i=0; i<wordclasses.size(); i++) {
            adapter.add(wordclasses.get(i).getName());
        }
        spinner.setAdapter(adapter);
    }

    public void saveNewWord(View v) {
        initialize();

        if(!validateInput()) {
            Toast.makeText(this, "Please fill out all mandatory fields!", Toast.LENGTH_SHORT).show();
        } else {
            //save the word to database
            AddWordBackgroundTask bg = new AddWordBackgroundTask(this);
            bg.execute("addWord", name, translation, conjugation, examples, Integer.toString(selectedWordClass.getId()));

        }


    }

    public void findWordClassIdByName(String name) {
        selectedWordClass.setId(0);
        for(int i=0; i<wordclasses.size(); i++) {
            if (wordclasses.get(i).getName().equals(name)) {
                selectedWordClass.setId(wordclasses.get(i).getId());
                break;
            }
        }
    }

    public void initialize() {
        name = et_word.getText().toString().trim();
        translation = et_translation.getText().toString().trim();
        conjugation= et_conjugation.getText().toString().trim();
        examples = et_examples.getText().toString().trim();
        findWordClassIdByName(spinnerValue);
        Log.d("values", Integer.toString(selectedWordClass.getId()));

    }

    public boolean validateInput() {
        if (name.isEmpty() || translation.isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinnerValue = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
