package com.scoctail.vocabularyapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.scoctail.vocabularyapp.R;
import com.scoctail.vocabularyapp.adapters.WordAdapter;
import com.scoctail.vocabularyapp.backgroundtasks.ShowWordsBackgroundTask;
import com.scoctail.vocabularyapp.beans.Word;
import com.scoctail.vocabularyapp.database.DatabaseHelper;

import java.util.List;

public class NavigationDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private int length;
    List<Word> words;
    ListView lv;
    WordAdapter adapter;
    private EditText searchText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lv = (ListView) findViewById(R.id.words_listview);
        initWordList();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Word word = (Word)adapter.getItemAtPosition(position);
                Intent i = new Intent(view.getContext(), WordDetails.class);
                i.putExtra("name", word.getName());
                startActivity(i);

            }
        });

        searchText = (EditText)findViewById(R.id.search_word_name);
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                length = s.toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                  initWordList();
                } else {
                    searchWord(s.toString().toLowerCase());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() < length) {
                    initWordList();
                    searchWord(s.toString().toLowerCase());
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        View header = navigationView.getHeaderView(0);
        TextView selectedLanguage = (TextView) header.findViewById(R.id.selected_language_name);
        selectedLanguage.setText(db.getSelectedLanguage(getApplicationContext()));
    }

    public void initWordList() {
        DatabaseHelper dbhelper = new DatabaseHelper(this);
        words = dbhelper.getWordsByLanguage(1);
        adapter = new WordAdapter(this, R.layout.word_row);

        for(Word w : words) {
            adapter.add(w);
        }
        lv.setAdapter(adapter);
    }

    public void searchWord(String str) {
        for (Word w : words) {
            if (str.length()==1) {
                char nameFirst = w.getName().toLowerCase().charAt(0);
                if(nameFirst != str.charAt(0)) {
                    adapter.remove(w);
                }
            } else {
                if(!w.getName().toLowerCase().contains(str)) {
                    adapter.remove(w);
                }
            }
        }
        //search now only by name, how to add translation to search?
        adapter.notifyDataSetChanged();
    }

    public void goToAddWordPage(View view) {
        Log.d("tag", "here");
        Intent i = new Intent(this,AddWord.class);
        this.startActivity(i);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            findViewById(R.id.words_listview).setVisibility(View.VISIBLE);
            findViewById(R.id.addButton).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FrameLayout framelayout = (FrameLayout) findViewById(R.id.content_frame);
        FragmentManager fm = getSupportFragmentManager();
        //framelayout.removeAllViewsInLayout();
        findViewById(R.id.words_listview).setVisibility(View.INVISIBLE);
        findViewById(R.id.addButton).setVisibility(View.INVISIBLE);

        if (id == R.id.nav_languages) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.addToBackStack(null);
            ft.replace(R.id.content_frame, new LanguagesFragment()).commit();
        } else if (id == R.id.nav_themes) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.addToBackStack(null);
            ft.replace(R.id.content_frame, new ThemesFragment()).commit();

        } else if (id == R.id.nav_wordclasses) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.addToBackStack(null);
            ft.replace(R.id.content_frame, new WordclassesFragment()).commit();
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
