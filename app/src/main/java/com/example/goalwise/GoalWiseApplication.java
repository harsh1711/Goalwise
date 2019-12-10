package com.example.goalwise;

import android.app.Application;

import com.example.goalwise.api.APIUrls;
import com.example.goalwise.db.ApplicationDB;
import com.example.goalwise.net.ApiService;
import com.example.goalwise.utils.TypefaceUtil;

public class GoalWiseApplication extends Application {
    private static final String APP_NAME = "GoalWiseApplication";

    public static final int PRODUCTION = 0;
    public static final int TEST = 1;

    // Change this mode to utilize test or production URLS
    public static final int APP_MODE = TEST;

    private static final String TEST_URL_PREFIX = "https://6cym66wz30.execute-api.ap-southeast-1.amazonaws.com/dev/";
    private static final String PROD_URL_PREFIX = "https://api.example.com/v1";

    @Override
    public void onCreate() {
        // Override default font if required. Uncomment the following to revert to
        // default android behaviour
        TypefaceUtil.overrideFonts(getApplicationContext());

        // Initialize Database object. Use a singleton object to provide access to
        // the database helper
        ApplicationDB.init(getApplicationContext(), APP_NAME);

        // Initialize Volley for network calls
        ApiService.init(getApplicationContext());

        // Initialize API URLs to point to correct server location (prod/test)
        APIUrls.init(getURLPrefix());

        super.onCreate();
    }

    private String getURLPrefix() {
        if(APP_MODE == TEST) {
            return TEST_URL_PREFIX;
        } else {
            return PROD_URL_PREFIX;
        }
    }
}
