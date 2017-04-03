package com.scoctail.vocabularyapp.beans;

/**
 * Created by Kaisu on 27/3/17.
 */

public class WordClass {
    int id;
    String name;

    public WordClass(String name) {
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
