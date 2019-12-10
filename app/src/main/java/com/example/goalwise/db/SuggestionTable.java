package com.example.goalwise.db;

public class SuggestionTable {
    public static final String TABLE_NAME = "suggestion";

    public static final String ID = "_id";
    public static final String NAME = "name";


    public static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME +
            "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            NAME + " TEXT UNIQUE NOT NULL)";

    public static final String COLUMN_NAME_NULLABLE = null;
}
