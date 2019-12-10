package com.example.goalwise.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.example.goalwise.R;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

public class AppUtils {

    public static String getVolleyError(Context context, VolleyError error) {
        if (context != null) {
            if (error instanceof TimeoutError) {
                return context.getString(R.string.time_our_error);
            } else if (error instanceof NoConnectionError) {
                return context.getString(R.string.can_not_connect);
            } else if (error instanceof AuthFailureError) {
                if (error.networkResponse.statusCode == 401) {
                    String responseBody = null;
                    String srt = null;
                    try {
                        responseBody = new String(error.networkResponse.data,
                                "utf-8");
                        JSONObject jsonObject = new JSONObject(responseBody);
                        srt = jsonObject.get("message").toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return srt;
                }
                return context.getString(R.string.auth_fail);
            } else if (error instanceof ServerError) {
                if (error.networkResponse.statusCode == 400 ||
                        error.networkResponse.statusCode == 401 ||
                        error.networkResponse.statusCode == 404 ||
                        error.networkResponse.statusCode == 422) {
                    String responseBody = null;
                    String srt = null;
                    try {
                        responseBody = new String(error.networkResponse.data,
                                "utf-8");
                        JSONObject jsonObject = new JSONObject(responseBody);
                        srt = jsonObject.get("message").toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return srt;
                }
                return context.getString(R.string.server_error);
            } else if (error instanceof NetworkError) {
                return context.getString(R.string.netwotk_error);
            } else if (error instanceof ParseError) {
                return context.getString(R.string.parser_error);
            }

            String msg = error.getMessage();

            if (msg != null && msg.trim().length() > 0) {
                return msg;
            }

            return context.getString(R.string.unknow_error_txt);
        } else {
            return "Unknown Error";
        }
    }

    public static void openSnackBar(LinearLayout view, String msg) {
        try {
            if (view != null && msg != null) {
                Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
                View view1 = snackbar.getView();
                TextView tv = view1.findViewById(R.id.snackbar_text);
                tv.setTextColor(Color.WHITE);
                snackbar.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
