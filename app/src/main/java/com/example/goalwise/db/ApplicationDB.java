package com.example.goalwise.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.goalwise.model.SuggestionItem;

public class ApplicationDB {
    private String appName;

    private static ApplicationDB ourInstance = null;
    private ApplicationDBHelper dbHelper;

    public static ApplicationDB init(Context context, String appName) {
        if(ourInstance == null) {
            ourInstance = new ApplicationDB(context, appName);
        }
        return ourInstance;
    }

    private ApplicationDB(Context context, String appName) {
        dbHelper = new ApplicationDBHelper(context, appName);
    }

    public static ApplicationDB get() {
        if(ourInstance == null) {
            throw new IllegalStateException("Call init to initialize the database before using it.");
        }
        return ourInstance;
    }

    /////////////////////////////////////////////////////////////////////////////////////
    // Add higher level DB functions below this. Access ApplicationDB as singleton from
    // any service, activity or fragment
    /////////////////////////////////////////////////////////////////////////////////////

    public void upsertSuggestion(SuggestionItem ci) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = ci.toCV();

        db.insertWithOnConflict(
                SuggestionTable.TABLE_NAME,
                SuggestionTable.COLUMN_NAME_NULLABLE, values, SQLiteDatabase.CONFLICT_REPLACE);

        db.close();
    }



    public Cursor getSuggestion(String suggestionKey) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql =  "SELECT * FROM " +  SuggestionTable.TABLE_NAME + " WHERE " +
                SuggestionTable.NAME + " LIKE '" + suggestionKey + "%'" + " ORDER BY " + " length('" + SuggestionTable.NAME  + "')" + " desc ";
        return db.rawQuery(sql,null);
    }

    public void upsertSuggestionUsingString(String query) {
        upsertSuggestion(new SuggestionItem(query));
    }
}
