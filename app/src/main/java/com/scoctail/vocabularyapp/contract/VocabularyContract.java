package com.scoctail.vocabularyapp.contract;

import android.provider.BaseColumns;

/**
 * Created by Kaisu on 6/4/17.
 */

public class VocabularyContract {

    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";

    public static final String TABLE_LANGUAGE = "language";
    public static final String TABLE_WORDCLASS = "wordclass";
    public static final String TABLE_THEME = "theme";
    public static final String TABLE_WORD = "word";

    private VocabularyContract() {
    }

    public static class LanguageEntry implements BaseColumns {
        public static final String CREATE_TABLE_LANGUAGE = "create table "+TABLE_LANGUAGE+"("+
                KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                KEY_NAME+" TEXT NOT NULL);";
    }

    public static class WordClassEntry implements BaseColumns {
        public static final String CREATE_TABLE_WORDCLASS = "create table "+TABLE_WORDCLASS+"("+
                KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                KEY_NAME+" TEXT NOT NULL);";
    }

    public static class ThemeEntry implements BaseColumns {
        public static final String KEY_DESCRIPTION = "description";

        public static final String CREATE_TABLE_THEME = "create table "+TABLE_THEME+"("+
                KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                KEY_NAME+" TEXT NOT NULL,"+
                KEY_DESCRIPTION+" TEXT);";
    }

    public static class WordEntry implements BaseColumns {
        public static final String KEY_TRANSLATION = "translation";
        public static final String KEY_EXAMPLES = "examples";
        public static final String KEY_CONJUGATION = "conjugation";

        public static final String KEY_THEME = "theme_id";
        public static final String KEY_LANGUAGE = "language_id";
        public static final String KEY_WORDCLASS = "wordclass_id";

        public static final String CREATE_TABLE_WORD = "create table "+TABLE_WORD+"("+
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
    }



}
