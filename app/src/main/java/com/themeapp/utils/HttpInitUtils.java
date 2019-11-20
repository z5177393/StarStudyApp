package com.themeapp.utils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class HttpInitUtils {
    private static RequestQueue sRequestQueue;
    public static RequestQueue getRequestQueue(Context context) {
        if (sRequestQueue == null) {
            synchronized (RequestQueue.class) {
                if (sRequestQueue == null) {
                    sRequestQueue = Volley.newRequestQueue(context);
                }
            }
        }
        return sRequestQueue;
    }

}
