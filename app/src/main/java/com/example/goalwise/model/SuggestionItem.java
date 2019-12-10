package com.example.goalwise.model;

import android.content.ContentValues;

import com.example.goalwise.db.SuggestionTable;

public class SuggestionItem {
    private String name;

    public SuggestionItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public ContentValues toCV() {
        ContentValues cv = new ContentValues();
        cv.put(SuggestionTable.NAME, name);
        return cv;
    }
}
