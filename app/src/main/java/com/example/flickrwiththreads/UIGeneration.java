package com.example.flickrwiththreads;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.provider.ContactsContract;
import android.util.Size;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.constraintlayout.solver.state.State;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import java.time.chrono.ThaiBuddhistEra;

public class UIGeneration{
    public Bitmap[] draws;
    public Context context;
    public ConstraintLayout con;

    UIGeneration(Bitmap[] draws, Context context, ConstraintLayout con){
        this.draws = draws;
        this.context = context;
        this.con = con;
    }

    UIGeneration(Context context, ConstraintLayout con){
        this.con = con;
        this.context = context;
    }

    public void CreateView(Bitmap map){
        if(Looper.myLooper() == Looper.getMainLooper()) System.out.println("Main");
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        final Display display = wm.getDefaultDisplay();
            ImageView view = new ImageView(context);
            view.setId(con.getChildCount());
            view.setImageBitmap(map);
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
            set.applyTo(con);
            System.out.println("view was created");
        }
    }

    public void CreateViews() {
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
    }
}
