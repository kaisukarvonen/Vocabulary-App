package com.scoctail.vocabularyapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.scoctail.vocabularyapp.R;
import com.scoctail.vocabularyapp.background.BackgroundTask;

public class DisplayWords extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        //BackgroundTask bgtask = new BackgroundTask(this);
        //bgtask.execute("getLanguages");

        //crashed if bgtask is used?

    }

    public void goToAddWordPage() {

    }
}
