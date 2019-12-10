package com.example.goalwise.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;


import com.example.goalwise.R;
import com.example.goalwise.db.SuggestionTable;

public class SuggestionAdapter extends CursorAdapter {

    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public SuggestionAdapter(Context context, Cursor cursor) {
        super(context, cursor, false);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
       return mLayoutInflater.inflate(R.layout.text_view, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView headerTv =  view.findViewById(R.id.suggestionTv);
        headerTv.setText(cursor.getString(cursor.getColumnIndex(SuggestionTable.NAME)));
    }

    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getCount() {
        if (getCursor() == null) {
            return 0;
        }
        return getCursor().getCount();
    }
}
