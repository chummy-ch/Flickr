package com.example.flickrwiththreads;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

public class UIThreadHelper {
    public ConstraintLayout con;
    public Handler createImageView ;

    UIThreadHelper(ConstraintLayout con){
        this.con = con;
    }

    public Handler getHandler(){
        createImageView = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg){
                ImageView view = (ImageView) msg.obj;
                con.addView(view);
                ConstraintSet set = new ConstraintSet();
                set.clone(con);
                if (view.getId() == 0) {
                    set.connect(view.getId(), ConstraintSet.TOP, con.getId(), ConstraintSet.TOP, 10);
                    set.connect(view.getId(), ConstraintSet.LEFT, con.getId(), ConstraintSet.LEFT, 10);
                } else {
                    if((int) view.getId() % 2 == 0){
                        ImageView v = (ImageView) con.getChildAt((int)view.getId() - 2);
                        int id = (int) view.getId() - 2;
                        set.connect(view.getId(), ConstraintSet.TOP, id, ConstraintSet.BOTTOM, 10);
                        set.connect(view.getId(), ConstraintSet.LEFT, con.getId(), ConstraintSet.LEFT, 10);
                    }
                    else if((int) view.getId() % 2 != 0){
                        ImageView v = (ImageView) con.getChildAt((int) view.getId() - 1);
                        set.connect(view.getId(), ConstraintSet.LEFT, v.getId(), ConstraintSet.RIGHT, 10);
                        if((int) view.getId() != 1){
                            int id = (int) view.getId() - 2;
                            set.connect(view.getId(), ConstraintSet.TOP, id, ConstraintSet.BOTTOM, 10);
                        }
                        else set.connect(view.getId(), ConstraintSet.TOP, con.getId(), ConstraintSet.TOP,10);
                    }
                }
                set.applyTo(con);
            }
        };
        return createImageView;
    }
}
