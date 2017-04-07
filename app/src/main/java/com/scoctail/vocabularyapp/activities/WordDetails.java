package com.scoctail.vocabularyapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.scoctail.vocabularyapp.R;
import com.scoctail.vocabularyapp.backgroundtasks.WordDetailsBackgroundTask;

/**
 * Created by Kaisu on 7/4/17.
 */

public class WordDetails extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_details);
        Intent intent = getIntent();
        String name = intent.getExtras().getString("name");
        WordDetailsBackgroundTask bg = new WordDetailsBackgroundTask(this);
        bg.execute("showDetails", name);

        Log.d("name", name);
    }
}
