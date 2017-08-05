package com.scoctail.vocabularyapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.scoctail.vocabularyapp.R;
import com.scoctail.vocabularyapp.backgroundtasks.AddWordBackgroundTask;
import com.scoctail.vocabularyapp.beans.Theme;
import com.scoctail.vocabularyapp.beans.WordClass;
import com.scoctail.vocabularyapp.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class AddWord extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {
    private EditText et_word, et_translation, et_conjugation, et_examples;
    private String name,translation,conjugation,examples,wordclassesSpinnerValue, themesSpinnerValue;
    private Spinner wordclassesSpinner, themesSpinner;
    private WordClass selectedWordClass = new WordClass();
    private Theme selectedTheme = new Theme();
    private List<WordClass> wordclasses = new ArrayList<WordClass>();
    private List<Theme> themes = new ArrayList<Theme>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_word);
        et_word = (EditText)findViewById(R.id.word);
        et_translation = (EditText)findViewById(R.id.translation);
        et_conjugation = (EditText)findViewById(R.id.conjugation);
        et_examples = (EditText)findViewById(R.id.examples);
        wordclassesSpinner = (Spinner) findViewById((R.id.wordclass_spinner));
        wordclassesSpinner.setOnItemSelectedListener(this);
        themesSpinner = (Spinner) findViewById((R.id.theme_spinner));
        themesSpinner.setOnItemSelectedListener(this);
        showSpinnerOptions();
    }

    public void showSpinnerOptions() {

        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        wordclasses = db.getWordclasses();
        ArrayAdapter<String> wordclassAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        wordclassAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        wordclassAdapter.add("Select word class");
        for (int i=0; i<wordclasses.size(); i++) {
            wordclassAdapter.add(wordclasses.get(i).getName());
        }
        wordclassesSpinner.setAdapter(wordclassAdapter);


        themes = db.getThemes();
        ArrayAdapter<String> themeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        themeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        themeAdapter.add("Select theme");
        for (int i=0; i<themes.size(); i++) {
            themeAdapter.add(themes.get(i).getName());
        }
        themesSpinner.setAdapter(themeAdapter);

    }

    public void saveNewWord(View v) {
        initialize();

        if(!validateInput()) {
            Toast.makeText(this, "Please fill out all mandatory fields!", Toast.LENGTH_SHORT).show();
        } else {
            /*
            AddWordBackgroundTask bg = new AddWordBackgroundTask(this);
            bg.execute("addWord", name, translation, conjugation, examples, Integer.toString(selectedWordClass.getId()));
*/
        }


    }

    public void findSpinnerIdsByName(String wordclassName, String themeName) {
        selectedWordClass.setId(0);
        for(int i=0; i<wordclasses.size(); i++) {
            if (wordclasses.get(i).getName().equals(wordclassName)) {
                selectedWordClass.setId(wordclasses.get(i).getId());
                break;
            }
        }
        selectedTheme.setId(0);
        for(int i=0; i<themes.size(); i++) {
            if (themes.get(i).getName().equals(themeName)) {
                selectedTheme.setId(themes.get(i).getId());
                break;
            }
        }
    }


    public void initialize() {
        name = et_word.getText().toString().trim();
        translation = et_translation.getText().toString().trim();
        conjugation= et_conjugation.getText().toString().trim();
        examples = et_examples.getText().toString().trim();
        wordclassesSpinnerValue = wordclassesSpinner.getSelectedItem().toString();
        themesSpinnerValue = themesSpinner.getSelectedItem().toString();
        findSpinnerIdsByName(wordclassesSpinnerValue, themesSpinnerValue);
        Log.d("names", themesSpinnerValue+ ", "+wordclassesSpinnerValue);
        Log.d("values", Integer.toString(selectedWordClass.getId())+ "theme: "+Integer.toString(selectedTheme.getId()));

    }

    public boolean validateInput() {
        if (name.isEmpty() || translation.isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
