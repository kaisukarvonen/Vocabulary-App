package com.scoctail.vocabularyapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DisplayWords extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_words_layout);
        BackgroundTask bgtask = new BackgroundTask(this);
        bgtask.execute("getLanguages");

        //crashed if bgtask is used?

    }
}
