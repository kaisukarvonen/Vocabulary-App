package com.scoctail.vocabularyapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Kaisu on 23/3/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "vocabularyApp";
    private static final String TABLE_LANGUAGE = "language";
    private static final String TABLE_THEME = "theme";
    private static final String TABLE_WORDCLASS = "wordclass";
    private static final String TABLE_WORD = "word";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";

    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_TRANSLATTION = "translation";
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
            KEY_TRANSLATTION+" TEXT NOT NULL,"+
            KEY_EXAMPLES+" TEXT,"+
            KEY_CONJUGATION+" TEXT,"+
            KEY_LANGUAGE+" INTEGER NOT NULL,"+
            KEY_THEME+" INTEGER,"+
            KEY_WORDCLASS+" INTEGER,"+
            "FOREIGN KEY ("+KEY_LANGUAGE+") REFERENCES "+TABLE_LANGUAGE+"("+KEY_ID+")),"+
            "FOREIGN KEY ("+KEY_THEME+") REFERENCES "+TABLE_THEME+"("+KEY_ID+")),"+
            "FOREIGN KEY ("+KEY_WORDCLASS+") REFERENCES "+TABLE_WORDCLASS+"("+KEY_ID+"));";



    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_LANGUAGE);
        db.execSQL(CREATE_TABLE_THEME);
        db.execSQL(CREATE_TABLE_WORDCLASS);
        db.execSQL(CREATE_TABLE_WORD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
