package com.scoctail.vocabularyapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.telecom.InCallService;
import android.util.Log;

import com.scoctail.vocabularyapp.beans.Language;
import com.scoctail.vocabularyapp.beans.Theme;
import com.scoctail.vocabularyapp.beans.Word;
import com.scoctail.vocabularyapp.beans.WordClass;
import com.scoctail.vocabularyapp.contract.VocabularyContract;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Kaisu on 23/3/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "vocabularyApp";
    Context ctx;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.ctx = context;
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


        writeToInternalStorage(ctx, Integer.toString(1), "language_id");
    }

    public static void writeToInternalStorage(Context ctx, String message, String file) {
        try {
            FileOutputStream fos = ctx.openFileOutput(file, ctx.MODE_PRIVATE);
            fos.write(message.getBytes());
            Log.d("writeToInternalStorage", "wrote "+message+" to file "+file);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String readFromInternalStorage(Context ctx, String file) {
        String content ="";
        try {
            FileInputStream fis = ctx.openFileInput(file);
            int txt = fis.read();
            //Log.d("file content", Character.toString((char)txt));
            content = Character.toString((char)txt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


    public Boolean addWord(Word word) {
        if (!rowExists(VocabularyContract.KEY_NAME, word.getName(),VocabularyContract.TABLE_WORD)) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(VocabularyContract.KEY_NAME, word.getName());
            values.put(VocabularyContract.WordEntry.KEY_CONJUGATION, word.getConjugation());
            values.put(VocabularyContract.WordEntry.KEY_EXAMPLES, word.getExamples());
            values.put(VocabularyContract.WordEntry.KEY_TRANSLATION, word.getTranslation());
            values.put(VocabularyContract.WordEntry.KEY_LANGUAGE, word.getLanguage().getId());

            if (word.getWordclass().getId() != 0) {
                values.put(VocabularyContract.WordEntry.KEY_WORDCLASS, word.getWordclass().getId());
            }
            if (word.getTheme().getId() != 0) {
                values.put(VocabularyContract.WordEntry.KEY_THEME, word.getTheme().getId());
            }
            db.insert(VocabularyContract.TABLE_WORD, null, values);
            db.close();
            return true;
        }
        return false;
    }

    public Boolean addTheme(String name) {
        if (!rowExists(VocabularyContract.KEY_NAME, name, VocabularyContract.TABLE_THEME)) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(VocabularyContract.KEY_NAME, name);
            db.insert(VocabularyContract.TABLE_THEME,null,values);
            db.close();
            return true;
        }
        return false;
    }

    public Boolean addWordclass(String name) {
        if (!rowExists(VocabularyContract.KEY_NAME, name, VocabularyContract.TABLE_WORDCLASS)) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(VocabularyContract.KEY_NAME, name);
            db.insert(VocabularyContract.TABLE_WORDCLASS, null, values);
            db.close();
            return true;
        }
        return false;
    }

    public Boolean addLanguage(String name) {
        if (!rowExists(VocabularyContract.KEY_NAME, name, VocabularyContract.TABLE_LANGUAGE)) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(VocabularyContract.KEY_NAME, name);
            db.insert(VocabularyContract.TABLE_LANGUAGE,null,values);
            db.close();
            return true;
        }
        return false;
    }

    public List<Language> getLanguages() {
        List<Language> languages = new ArrayList<Language>();
        SQLiteDatabase db = this.getReadableDatabase();
        String [] columns = {VocabularyContract.KEY_ID, VocabularyContract.KEY_NAME};
        Cursor c = db.query(VocabularyContract.TABLE_LANGUAGE, columns, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                languages.add(new Language(c.getInt(c.getColumnIndex("id")), c.getString(c.getColumnIndex("name"))));
            } while (c.moveToNext());
        }
        db.close();
        return languages;
    }

    public String getSelectedLanguage(Context ctx) {
        String content = readFromInternalStorage(ctx, "language_id");
        int id = Integer.parseInt(content);
        Log.d("language id", Integer.toString(id));
        SQLiteDatabase db = this.getReadableDatabase();
        String [] columns = {VocabularyContract.KEY_NAME};
        Cursor c = db.query(VocabularyContract.TABLE_LANGUAGE, columns, VocabularyContract.KEY_ID+" = "+id, null ,null, null,null);
        c.moveToFirst();
        Log.d("selected language", c.getString(c.getColumnIndex(VocabularyContract.KEY_NAME)));
        db.close();
        return c.getString(c.getColumnIndex(VocabularyContract.KEY_NAME));

    }

    public List<WordClass> getWordclasses() {
        List<WordClass> wordclasses = new ArrayList<WordClass>();
        String selectwordclasses = "SELECT " + VocabularyContract.KEY_NAME + "," + VocabularyContract.KEY_ID + " FROM " + VocabularyContract.TABLE_WORDCLASS + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectwordclasses, null);
        if (c.moveToFirst()) {
            do {
                wordclasses.add(new WordClass(c.getInt(c.getColumnIndex("id")), c.getString(c.getColumnIndex("name"))));
            } while (c.moveToNext());
        }
        db.close();
        return wordclasses;
    }

    public List<Theme> getThemes() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT "+VocabularyContract.KEY_NAME+ ","+VocabularyContract.KEY_ID+" FROM "+VocabularyContract.TABLE_THEME+";",null);
        List<Theme> themes = new ArrayList<Theme>();
        if (c.moveToFirst()) {
            do {
                themes.add(new Theme(c.getInt(c.getColumnIndex(VocabularyContract.KEY_ID)),c.getString(c.getColumnIndex(VocabularyContract.KEY_NAME))));
            } while (c.moveToNext());
        }
        db.close();
        return themes;
    }

    public List<Word> getWordsByLanguage(int la_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String [] columns = {VocabularyContract.KEY_ID, VocabularyContract.KEY_NAME, VocabularyContract.WordEntry.KEY_TRANSLATION};
        Cursor c = db.query(VocabularyContract.TABLE_WORD, columns, VocabularyContract.WordEntry.KEY_LANGUAGE+" = "+la_id, null, null, null, VocabularyContract.KEY_NAME+" COLLATE NOCASE ASC");
        List<Word> words = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                words.add(new Word(c.getInt(c.getColumnIndex(VocabularyContract.KEY_ID)), c.getString(c.getColumnIndex(VocabularyContract.KEY_NAME)), c.getString(c.getColumnIndex(VocabularyContract.WordEntry.KEY_TRANSLATION))));
            } while (c.moveToNext());
        }
        db.close();
        return words;
    }

    public Word getWord(String name) {
        name = "'"+name+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        String [] columns = {VocabularyContract.KEY_NAME, VocabularyContract.WordEntry.KEY_TRANSLATION, VocabularyContract.WordEntry.KEY_CONJUGATION, VocabularyContract.WordEntry.KEY_EXAMPLES, VocabularyContract.WordEntry.KEY_WORDCLASS};
        Cursor c = db.query(VocabularyContract.TABLE_WORD, columns, VocabularyContract.KEY_NAME+" = "+name, null, null, null, null);

        Word word = new Word();
        c.moveToFirst();
        word.setName(c.getString(c.getColumnIndex(VocabularyContract.KEY_NAME)));
        word.setTranslation(c.getString(c.getColumnIndex(VocabularyContract.WordEntry.KEY_TRANSLATION)));
        word.setConjugation(c.getString(c.getColumnIndex(VocabularyContract.WordEntry.KEY_CONJUGATION)));
        word.setExamples(c.getString(c.getColumnIndex(VocabularyContract.WordEntry.KEY_EXAMPLES)));
        int wordclassId = c.getInt(c.getColumnIndex(VocabularyContract.WordEntry.KEY_WORDCLASS));
        if (wordclassId == 0) {
            word.setWordclass(new WordClass(""));
        } else {
            word.setWordclass(new WordClass(getWordClassName(wordclassId)));
        }
        db.close();
        return word;
    }

    public String getWordClassName(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String [] columns = {VocabularyContract.KEY_NAME};
        Cursor c = db.query(VocabularyContract.TABLE_WORDCLASS, columns, VocabularyContract.KEY_ID+" = "+id, null, null, null, null);
        c.moveToFirst();
        return c.getString(c.getColumnIndex(VocabularyContract.KEY_NAME));
    }

    public Boolean rowExists(String fieldName, String value, String table) {
        SQLiteDatabase db = this.getReadableDatabase();
        String rawQuery = "SELECT id FROM "+table+ " WHERE "+fieldName+"='"+value+"' COLLATE NOCASE;";
        Cursor c = db.rawQuery(rawQuery,null);
        if (c.moveToFirst()) {
            return true;
        }
        c.close();
        db.close();
        return false;
    }

}
