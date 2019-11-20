package com.themeapp.utils;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.themeapp.application.App;

public class VolleyUtils {

    private VolleyUtils(){}

    private RequestQueue requestQueue = App.mRequestQueue;
    private static VolleyUtils instance;

    //单例获取工具类
    public static VolleyUtils getInstance() {
        if (instance == null) {
            synchronized (VolleyUtils.class) {
                if (instance == null) {
                    instance = new VolleyUtils();
                }
            }
        }
        return instance;
    }

    public void getMessage( String url,Response.Listener<String> response,  Response.ErrorListener errlistener) {
        StringRequest request = new StringRequest(Request.Method.GET, url,response,errlistener );
        requestQueue.add(request);
    }

    public void setImageUrl(String url ,final ImageView imageView){
        ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                imageView.setImageBitmap(response);
            }
        }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                imageView.setImageResource(R.drawable.default_image);
            }
        });

        requestQueue.add(imageRequest);
    }
}
