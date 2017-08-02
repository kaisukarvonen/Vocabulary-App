package com.scoctail.vocabularyapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.scoctail.vocabularyapp.R;
import com.scoctail.vocabularyapp.adapters.WordAdapter;
import com.scoctail.vocabularyapp.backgroundtasks.ShowWordsBackgroundTask;
import android.widget.AdapterView.OnItemClickListener;
import com.scoctail.vocabularyapp.beans.Word;

public class DisplayWords extends AppCompatActivity {
    ListView lv;
    WordAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        ShowWordsBackgroundTask bg = new ShowWordsBackgroundTask(this);
        bg.execute("showWords");
        lv = (ListView) findViewById(R.id.words_listview);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Word word = (Word)adapter.getItemAtPosition(position);
                //Log.d("word", word.getName());
                Intent i = new Intent(view.getContext(), WordDetails.class);
                i.putExtra("name", word.getName());
                startActivity(i);

            }
        });

    }

    public void goToAddWordPage(View view) {
        Log.d("tag", "here");
        Intent i = new Intent(this,AddWord.class);
        this.startActivity(i);
    }


}
