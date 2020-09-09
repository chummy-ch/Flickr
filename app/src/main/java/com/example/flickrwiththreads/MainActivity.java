package com.example.flickrwiththreads;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.widget.NestedScrollView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Vibrator;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.googlecode.flickrjandroid.photos.SearchParameters;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MainActivity extends AppCompatActivity {
    public ConstraintLayout con;
    public ConstraintLayout parent;
    public NestedScrollView scroll;
    public EditText field;
    public Button find;
    public static MyHelper hepler = new MyHelper();
    public Handler h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        parent = findViewById(R.id.parent);
        con = findViewById(R.id.con);
        find = findViewById(R.id.find);
        field = findViewById(R.id.ed);
        scroll = findViewById(R.id.scroll);
        UIOptimization();
        h = new Handler(Looper.getMainLooper()){
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
        getSupportActionBar().hide();
        View.OnClickListener get = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(field.getText().toString().trim().length() > 2){
                    Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vb.vibrate(100);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Start();
                        }
                    }).start();
                }
                else{
                    Toast.makeText(MainActivity.this, "Напишите поисковый запрос", Toast.LENGTH_LONG).show();
                    Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vb.vibrate(400);
                }
            }
        };
        find.setOnClickListener(get);
    }

    public void UIOptimization(){
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        final Display display = wm.getDefaultDisplay();
        scroll.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, display.getHeight() * 9980 / 10000 ));
        ConstraintLayout.LayoutParams params = new  ConstraintLayout.LayoutParams(display.getWidth()/8*4, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(10,0,0,0);
        field.setLayoutParams(params);
        ConstraintSet set = new ConstraintSet();
        set.clone(parent);
        set.connect(field.getId(), ConstraintSet.BOTTOM, parent.getId(), ConstraintSet.BOTTOM);
        set.connect(field.getId(), ConstraintSet.LEFT, parent.getId(), ConstraintSet.LEFT);
        set.connect(field.getId(), ConstraintSet.RIGHT, parent.getId(), ConstraintSet.RIGHT);
        set.applyTo(parent);
    }

    public void Start(){
        PhotoManager manager = new PhotoManager(this, con, field.getText().toString(), h);
        manager.GetPL();
    }
}