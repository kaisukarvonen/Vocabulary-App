package com.scoctail.vocabularyapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.scoctail.vocabularyapp.R;
import com.scoctail.vocabularyapp.beans.Theme;
import com.scoctail.vocabularyapp.database.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(getApplicationContext());

        db.addTheme(new Theme("sports", null));
    }
}
