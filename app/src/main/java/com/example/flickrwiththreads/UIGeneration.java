package com.example.flickrwiththreads;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class UIGeneration {
    public Bitmap[] draws;
    public Context context;
    public ConstraintLayout con;
    public Handler h;

    UIGeneration(Bitmap[] draws, Context context, ConstraintLayout con, Handler h){
        this.draws = draws;
        this.context = context;
        this.con = con;
        this.h = h;

    }

    UIGeneration(Context context, ConstraintLayout con, Handler h){
        this.con = con;
        this.h = h;
        this.context = context;
    }

    public void CreateView(Bitmap map) {
        final Bitmap bMap = map;
        ExecutorService ser = Executors.newSingleThreadExecutor();
        ser.submit(new Runnable() {
            @Override
            public void run() {
                WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                final Display display = wm.getDefaultDisplay();
                ImageView view = new ImageView(context);
                view.setId(con.getChildCount());
                view.setImageBitmap(bMap);
                view.setBackgroundResource(R.drawable.round_view);
                view.setAdjustViewBounds(true);
                view.setScaleType(ImageView.ScaleType.CENTER_CROP);
                view.setClipToOutline(true);
                view.setLayoutParams(new ConstraintLayout.LayoutParams(display.getWidth() / 2 - 20, display.getHeight() / 5));
                Message msg = h.obtainMessage(view.getId(), view);
                h.sendMessage(msg);
            }
        });
    }

    /*public void CreateViews() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        final Display display = wm.getDefaultDisplay();
        for(int i = 0; i < draws.length; i++){
            ImageView view = new ImageView(context);
            view.setId(con.getChildCount());
            view.setImageBitmap(draws[i]);
            view.setLayoutParams(new ConstraintLayout.LayoutParams(display.getWidth(), display.getHeight() / 5));
            con.addView(view);
            ConstraintSet set = new ConstraintSet();
            set.clone(con);
            if(con.getChildCount() == 1){
                set.connect(view.getId(), ConstraintSet.TOP, con.getId(), ConstraintSet.TOP);
            }
            else {
                ImageView v = (ImageView) con.getChildAt(con.getChildCount() - 2);
                set.connect(view.getId(), ConstraintSet.TOP, v.getId(), ConstraintSet.BOTTOM);
            }
            set.applyTo(con);
        }
    }*/
}
