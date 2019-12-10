package com.example.goalwise.activity;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.goalwise.R;
import com.example.goalwise.adapter.SIPAdapter;
import com.example.goalwise.adapter.SuggestionAdapter;
import com.example.goalwise.api.APIUrls;
import com.example.goalwise.db.ApplicationDB;
import com.example.goalwise.db.SuggestionTable;
import com.example.goalwise.fragment.SuccessDialogFragment;
import com.example.goalwise.model.SipRequest;
import com.example.goalwise.model.SipResponse;
import com.example.goalwise.net.ApiService;
import com.example.goalwise.net.GenericRequest;



public class MainActivity extends AppCompatActivity {

    public static final String FUND_NAME = "FUND_NAME";
    private SearchView searchView;
    private ListView listView;
    private Context context;
    private SIPAdapter sipAdapter;
    private SuggestionAdapter suggestionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpToolbar();
        initIds();
        setSuggestionAdapter();
        handleClickEvent();
        removeFocusFromSearchViewAndHideKeyboard();
    }

    private void setUpToolbar() {
       ActionBar supportActionBar =  getSupportActionBar();
       if(supportActionBar != null){
           getSupportActionBar().setTitle(getString(R.string.fund));
           getSupportActionBar().setDisplayHomeAsUpEnabled(true);
           Drawable backArrow = ContextCompat.getDrawable(this, R.drawable.ic_keyboard_arrow_left_white);
           getSupportActionBar().setHomeAsUpIndicator(backArrow);
       }
    }

    private void removeFocusFromSearchViewAndHideKeyboard() {
        searchView.clearFocus();
        hideKeyboard(this);
    }

    private void handleClickEvent() {
        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                Cursor cursor = (Cursor) suggestionAdapter.getItem(position);
                String txt = cursor.getString(cursor.getColumnIndex(SuggestionTable.NAME));
                searchView.setQuery(txt, true);
                return true;
            }
        });

        int searchCloseButtonId = searchView.getResources().getIdentifier("android:id/search_close_btn", null, null);
        ImageView closeButton = findViewById(searchCloseButtonId);

        closeButton.setOnClickListener(view -> {
            searchView.setQuery("", false);
            clearAdapter();
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                performSearch(newText);
                return true;
            }
        });
    }

    private void setSuggestionAdapter() {
        suggestionAdapter = new SuggestionAdapter(this, ApplicationDB.get().getSuggestion(""));
        searchView.setSuggestionsAdapter(suggestionAdapter);
    }

    private void initIds() {
        searchView = findViewById(R.id.searchView);
        listView = findViewById(R.id.listView);
        context = this;
    }

    private void performSearch(String query) {
        if(query != null && query.length() >= 3){
            suggestionAdapter.swapCursor(ApplicationDB.get().getSuggestion(query));
            callFetchSip(query);
        }else {
            clearAdapter();
        }
    }

    private void hideKeyboard(Activity activity) {
        if (activity != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                if (activity.getCurrentFocus() != null) {
                    if (activity.getCurrentFocus().getWindowToken() != null) {
                        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
                    }
                }
            }
        }
    }


    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        searchView.clearFocus();
        hideKeyboard(this);
    }


    private void clearAdapter() {
        listView.setAdapter(null);
    }


    private void callFetchSip(String query) {
        SipRequest sipRequest = new SipRequest();
        sipRequest.setKeyword(query);
        GenericRequest<SipResponse> logincRequest = new GenericRequest<SipResponse>
                (Request.Method.POST, APIUrls.get().getSip(),
                        SipResponse.class, sipRequest,
                        sipResponse -> {
                            ApplicationDB.get().upsertSuggestionUsingString(query);
                            updateList(sipResponse);
                        },
                        error -> Toast.makeText(context,"Failure",Toast.LENGTH_SHORT).show());

        ApiService.get().addToRequestQueue(logincRequest);
    }

    private void updateList(SipResponse sipResponse) {
        sipAdapter = new SIPAdapter(context,R.layout.sip_list,sipResponse.getData(), new ShowDialogBoxCallBack() {
            @Override
            public void showDialogBox(String fundName) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString(FUND_NAME,fundName);
                androidx.fragment.app.DialogFragment dialogFragment = new SuccessDialogFragment();
                dialogFragment.setArguments(bundle);
                dialogFragment.show(ft, null);
            }
        });
        listView.setAdapter(sipAdapter);
    }

    public interface ShowDialogBoxCallBack {
        void showDialogBox(String fundName);
    }
}
