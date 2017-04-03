package com.scoctail.vocabularyapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.scoctail.vocabularyapp.beans.Language;
import com.scoctail.vocabularyapp.beans.Theme;
import com.scoctail.vocabularyapp.beans.Word;
import com.scoctail.vocabularyapp.beans.WordClass;

/**
 * Created by Kaisu on 23/3/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "vocabularyApp";
    private static final String TABLE_LANGUAGE = "language";
    private static final String TABLE_THEME = "theme";
    private static final String TABLE_WORDCLASS = "wordclass";
    private static final String TABLE_WORD = "word";

    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";

    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_TRANSLATION = "translation";
    private static final String KEY_EXAMPLES = "examples";
    private static final String KEY_CONJUGATION = "conjugation";
    private static final String KEY_THEME = "theme_id";
    private static final String KEY_LANGUAGE = "language_id";
    private static final String KEY_WORDCLASS = "wordclass_id";

    private static final String CREATE_TABLE_LANGUAGE = "create table "+TABLE_LANGUAGE+"("+
            KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            KEY_NAME+" TEXT NOT NULL);";

    private static final String CREATE_TABLE_THEME = "create table "+TABLE_THEME+"("+
            KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            KEY_NAME+" TEXT NOT NULL,"+
            KEY_DESCRIPTION+" TEXT);";

    private static final String CREATE_TABLE_WORDCLASS = "create table "+TABLE_WORDCLASS+"("+
            KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            KEY_NAME+" TEXT NOT NULL);";

    private static final String CREATE_TABLE_WORD = "create table "+TABLE_WORD+"("+
            KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            KEY_NAME+" TEXT NOT NULL,"+
            KEY_TRANSLATION+" TEXT NOT NULL,"+
            KEY_EXAMPLES+" TEXT,"+
            KEY_CONJUGATION+" TEXT,"+
            KEY_LANGUAGE+" INTEGER NOT NULL,"+
            KEY_THEME+" INTEGER,"+
            KEY_WORDCLASS+" INTEGER,"+
            "FOREIGN KEY ("+KEY_LANGUAGE+") REFERENCES "+TABLE_LANGUAGE+"("+KEY_ID+"),"+
            "FOREIGN KEY ("+KEY_THEME+") REFERENCES "+TABLE_THEME+"("+KEY_ID+"),"+
            "FOREIGN KEY ("+KEY_WORDCLASS+") REFERENCES "+TABLE_WORDCLASS+"("+KEY_ID+"));";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_LANGUAGE);
        db.execSQL(CREATE_TABLE_THEME);
        db.execSQL(CREATE_TABLE_WORDCLASS);
        db.execSQL(CREATE_TABLE_WORD);

        db.execSQL("insert into "+TABLE_LANGUAGE+" ("+KEY_NAME+") values ('English');");
        db.execSQL("insert into "+TABLE_LANGUAGE+" ("+KEY_NAME+") values ('Deutsch');");
        db.execSQL("insert into "+TABLE_LANGUAGE+" ("+KEY_NAME+") values ('Espanol');");

        db.execSQL("insert into "+TABLE_WORDCLASS+" ("+KEY_NAME+") values ('Verbs');");
        db.execSQL("insert into "+TABLE_WORDCLASS+" ("+KEY_NAME+") values ('Nouns');");
        db.execSQL("insert into "+TABLE_WORDCLASS+" ("+KEY_NAME+") values ('Adjectives');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    public void addLanguage(Language l) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, l.getName());

        db.insert(TABLE_LANGUAGE, null, values);
    }

    public void addWordClass(WordClass wordclass) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, wordclass.getName());

        db.insert(TABLE_WORDCLASS, null, values);
    }

    public void addTheme(Theme theme) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, theme.getName());
        if (theme.getDescription() != "" || theme.getDescription() != null) {
            values.put(KEY_NAME, theme.getDescription());
        }

        db.insert(TABLE_THEME, null, values);
    }

    public void addWord(Word word, int la_id, int theme_id, int wordclass_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_NAME, word.getName());
        values.put(KEY_CONJUGATION, word.getConjugation());
        values.put(KEY_EXAMPLES, word.getExamples());
        values.put(KEY_TRANSLATION, word.getTranslation());
        values.put(KEY_LANGUAGE, la_id);

        if (wordclass_id!= 0) {
            values.put(KEY_WORDCLASS, wordclass_id);
        }
        if (theme_id!= 0) {
            values.put(KEY_THEME, theme_id);
        }

        db.insert(TABLE_WORD, null, values);
    }

    public Cursor getLanguages() {
        Log.d("where?", "here");
        SQLiteDatabase db = this.getWritableDatabase();
        String [] columns = {KEY_ID, KEY_NAME};
        Cursor c = db.query(TABLE_LANGUAGE, columns, null, null, null, null, null);

        return c;

    }

}
