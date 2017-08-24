package com.scoctail.vocabularyapp.activities;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
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
import com.scoctail.vocabularyapp.adapters.SectionAdapter;
import com.scoctail.vocabularyapp.adapters.WordAdapter;
import com.scoctail.vocabularyapp.beans.Language;
import com.scoctail.vocabularyapp.beans.Theme;
import com.scoctail.vocabularyapp.beans.Word;
import com.scoctail.vocabularyapp.database.DatabaseHelper;
import com.scoctail.vocabularyapp.dialogs.ChooseLanguageDialog;
import com.scoctail.vocabularyapp.dialogs.OnDialogConfirmClickListener;
import com.scoctail.vocabularyapp.dialogs.SortByDialog;

import java.util.List;

public class NavigationDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnDialogConfirmClickListener {

    private int length;
    List<Word> words;
    ListView lv;
    WordAdapter wadapter;
    SectionAdapter sadapter;
    private EditText searchText;
    private DatabaseHelper db;
    View header;
    private int counter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = new DatabaseHelper(getApplicationContext());
        counter = 0;

        lv = (ListView) findViewById(R.id.words_listview);
        initWordList(Integer.parseInt(db.readFromInternalStorage(getApplication(), "sort_by_selection")));


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Word word = (Word)adapter.getItemAtPosition(position);
                if (word.getTranslation() != null) {
                    Intent i = new Intent(view.getContext(), WordDetails.class);
                    i.putExtra("name", word.getName());
                    startActivity(i);
                }

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
                    initWordList(Integer.parseInt(db.readFromInternalStorage(getApplication(), "sort_by_selection")));
                } else {
                    searchWord(s.toString().toLowerCase());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() < length) {
                    initWordList(Integer.parseInt(db.readFromInternalStorage(getApplication(), "sort_by_selection")));
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
        header = navigationView.getHeaderView(0);
        setTextViewContent();



    }

    public void setTextViewContent() {
        TextView selectedLanguage = (TextView) header.findViewById(R.id.selected_language_name);
        selectedLanguage.setText(db.getSelectedLanguage(this).getName());

        TextView languageChoiceBtn = (TextView) findViewById(R.id.language_choice_btn);
        languageChoiceBtn.setPaintFlags(languageChoiceBtn.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        languageChoiceBtn.setText(db.getSelectedLanguage(this).getName().substring(0,3));
    }



    public void initWordList(int rule) {
        words = db.getWordsByLanguage(db.getSelectedLanguage(this).getId(), rule);

        if(rule == 0) {
            wadapter = new WordAdapter(this, R.layout.word_row);
            for(Word w : words) {
                wadapter.add(w);
            }
            lv.setAdapter(wadapter);
        } else if (rule==1){
            sadapter = new SectionAdapter(this, R.layout.word_row);
            sadapter.addSectionHeader(new Word(words.get(0).getTheme().getName()));
            for(int i=0; i<words.size()-1; i++) {
                sadapter.add(words.get(i));
                if (!words.get(i+1).getTheme().getName().equals(words.get(i).getTheme().getName())) {
                    sadapter.addSectionHeader(new Word(words.get(i+1).getTheme().getName()));
                }
            }
            lv.setAdapter(sadapter);
        } else {
            sadapter = new SectionAdapter(this, R.layout.word_row);
            sadapter.addSectionHeader(new Word(words.get(0).getWordclass().getName()));
            for(int i=0; i<words.size()-1; i++) {
                sadapter.add(words.get(i));
                if (!words.get(i+1).getWordclass().getName().equals(words.get(i).getWordclass().getName())) {
                    sadapter.addSectionHeader(new Word(words.get(i+1).getWordclass().getName()));
                }
            }
            lv.setAdapter(sadapter);
        }



        //categorized by word class or theme

    }

    public void searchWord(String str) {
        for (Word w : words) {
            if (str.length()==1) {
                char nameFirst = w.getName().toLowerCase().charAt(0);
                if(nameFirst != str.charAt(0)) {
                    sadapter.remove(w);
                }
            } else {
                if(!w.getName().toLowerCase().contains(str)) {
                    sadapter.remove(w);
                }
            }
        }
        //search now only by name, how to add translation to search?
        sadapter.notifyDataSetChanged();
    }

    public void goToAddWordPage(View view) {
        Log.d("tag", "here");
        Intent i = new Intent(this,AddWord.class);
        this.startActivity(i);
    }

    public void chooseLanguage(View v) {
        ChooseLanguageDialog chooser = new ChooseLanguageDialog();

        chooser.show(getSupportFragmentManager(), "chooser");
    }

    public void sortWords(View v) {
        SortByDialog sorter = new SortByDialog();
        sorter.show(getSupportFragmentManager(), "sorter");
    }

    @Override
    public void onSortByDialogConfirmClick(CharSequence[] options, int which) {
        String currentSelection = db.readFromInternalStorage(this, "sort_by_selection");
        Log.d("current sortby", currentSelection);
        Log.d("new", Integer.toString(which));

        if (which != Integer.parseInt(currentSelection)) {
            db.writeToInternalStorage(this, Integer.toString(which), "sort_by_selection");
            if (which == 0) { //alphabetical
                lv.setAdapter(null);
                initWordList(0);
            } else if (which == 1) { //Themes
                lv.setAdapter(null);
                initWordList(1);
            } else { //Word classes
                lv.setAdapter(null);
                initWordList(2);
            }

        }
    }

    @Override
    public void onChooseLanguageDialogConfirmClick() {
        lv.setAdapter(null);
        initWordList(Integer.parseInt(db.readFromInternalStorage(getApplication(), "sort_by_selection")));
        setTextViewContent();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            counter--;
            if (counter == 0) {
                setElementsVisibility(View.VISIBLE);
            }
            super.onBackPressed();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setElementsVisibility(int visibility) {
        findViewById(R.id.enter_search_word_name).setVisibility(visibility);
        findViewById(R.id.language_choice_btn).setVisibility(visibility);
        findViewById(R.id.sort_by_btn).setVisibility(visibility);
        searchText.setVisibility(visibility);
        lv.setVisibility(visibility);
        findViewById(R.id.addButton).setVisibility(visibility);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fm = getSupportFragmentManager();
        setElementsVisibility(View.INVISIBLE);

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
        }

        counter++;

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
