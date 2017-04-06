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
import com.scoctail.vocabularyapp.contract.VocabularyContract;

/**
 * Created by Kaisu on 23/3/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "vocabularyApp";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(VocabularyContract.LanguageEntry.CREATE_TABLE_LANGUAGE);
        db.execSQL(VocabularyContract.ThemeEntry.CREATE_TABLE_THEME);
        db.execSQL(VocabularyContract.WordClassEntry.CREATE_TABLE_WORDCLASS);
        db.execSQL(VocabularyContract.WordEntry.CREATE_TABLE_WORD);

        db.execSQL("insert into "+VocabularyContract.TABLE_LANGUAGE+" ("+VocabularyContract.KEY_NAME+") values ('English');");
        db.execSQL("insert into "+VocabularyContract.TABLE_LANGUAGE+" ("+VocabularyContract.KEY_NAME+") values ('Deutsch');");
        db.execSQL("insert into "+VocabularyContract.TABLE_LANGUAGE+" ("+VocabularyContract.KEY_NAME+") values ('Espanol');");

        db.execSQL("insert into "+VocabularyContract.TABLE_WORDCLASS+" ("+VocabularyContract.KEY_NAME+") values ('Verbs');");
        db.execSQL("insert into "+VocabularyContract.TABLE_WORDCLASS+" ("+VocabularyContract.KEY_NAME+") values ('Nouns');");
        db.execSQL("insert into "+VocabularyContract.TABLE_WORDCLASS+" ("+VocabularyContract.KEY_NAME+") values ('Adjectives');");
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
        values.put(VocabularyContract.KEY_NAME, l.getName());

        db.insert(VocabularyContract.TABLE_LANGUAGE, null, values);
    }

    public void addWordClass(WordClass wordclass) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(VocabularyContract.KEY_NAME, wordclass.getName());

        db.insert(VocabularyContract.TABLE_WORDCLASS, null, values);
    }

    public void addTheme(Theme theme) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(VocabularyContract.KEY_NAME, theme.getName());
        if (theme.getDescription() != "" || theme.getDescription() != null) {
            values.put(VocabularyContract.KEY_NAME, theme.getDescription());
        }

        db.insert(VocabularyContract.TABLE_THEME, null, values);
    }

    public void addWord(Word word, int la_id, int theme_id, int wordclass_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(VocabularyContract.KEY_NAME, word.getName());
        values.put(VocabularyContract.WordEntry.KEY_CONJUGATION, word.getConjugation());
        values.put(VocabularyContract.WordEntry.KEY_EXAMPLES, word.getExamples());
        values.put(VocabularyContract.WordEntry.KEY_TRANSLATION, word.getTranslation());
        values.put(VocabularyContract.WordEntry.KEY_LANGUAGE, la_id);

        if (wordclass_id!= 0) {
            values.put(VocabularyContract.WordEntry.KEY_WORDCLASS, wordclass_id);
        }
        if (theme_id!= 0) {
            values.put(VocabularyContract.WordEntry.KEY_THEME, theme_id);
        }

        db.insert(VocabularyContract.TABLE_WORD, null, values);
    }

    public Cursor getLanguages() {
        Log.d("where?", "here");
        SQLiteDatabase db = this.getWritableDatabase();
        String [] columns = {VocabularyContract.KEY_ID, VocabularyContract.KEY_NAME};
        Cursor c = db.query(VocabularyContract.TABLE_LANGUAGE, columns, null, null, null, null, null);

        return c;

    }

    public Cursor getWordsByLanguage(int la_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String [] columns = {VocabularyContract.KEY_NAME, VocabularyContract.WordEntry.KEY_TRANSLATION};
        Cursor c = db.query(VocabularyContract.TABLE_WORD, columns, VocabularyContract.WordEntry.KEY_LANGUAGE+" = "+la_id, null, null, null, null);

        return c;

    }



}
