package com.example.flickrwiththreads;


import android.os.Handler;
import android.os.Looper;


public class MyHelper{
    private android.os.Handler handler = new android.os.Handler(Looper.getMainLooper());

    public android.os.Handler getHandler(){
        return handler;
    }

    public void setHandler(Handler h){
        handler = h;
    }
}
