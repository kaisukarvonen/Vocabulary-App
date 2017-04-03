package com.scoctail.vocabularyapp.beans;

/**
 * Created by Kaisu on 27/3/17.
 */

public class Language {
    int id;
    String name;

    public Language() {
    }

    public Language(String name) {
        this.name = name;
    }

    public Language(int id, String name) {
        this.id = id;
        this.name = name;
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
}
