package com.scoctail.vocabularyapp.beans;

/**
 * Created by Kaisu on 27/3/17.
 */

public class Word {
    private int id;
    private Theme theme;
    private Language language;
    private WordClass wordclass;
    private String name,translation, examples,conjugation;

    public Word() {
    }

    public Word(String name, String translation) {
        this.name = name;
        this.translation = translation;
    }

    public Word(String name, String translation, String examples, String conjugation, WordClass wordclass, Theme theme, Language language) {
        this.name = name;
        this.translation = translation;
        this.examples = examples;
        this.conjugation = conjugation;
        this.wordclass = wordclass;
        this.theme = theme;
        this.language = language;
    }

    public Word(String name, String translation, String examples, String conjugation) {
        this.name = name;
        this.translation = translation;
        this.examples = examples;
        this.conjugation = conjugation;
    }

    public void setWordclass(WordClass wordclass) {
        this.wordclass = wordclass;
    }

    public WordClass getWordclass() {
        return this.wordclass;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getExamples() {
        return examples;
    }

    public void setExamples(String examples) {
        this.examples = examples;
    }

    public String getConjugation() {
        return conjugation;
    }

    public void setConjugation(String conjugation) {
        this.conjugation = conjugation;
    }
}
