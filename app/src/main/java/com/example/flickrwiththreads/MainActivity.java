package com.example.flickrwiththreads;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.widget.NestedScrollView;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
        h = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg){
                System.out.println("HHAANNDDLLEERR " + msg.what);
                ImageView view = (ImageView) msg.obj;
                con.addView(view);
                ConstraintSet set = new ConstraintSet();
                set.clone(con);
                if (con.getChildCount() == 1) {
                    set.connect(view.getId(), ConstraintSet.TOP, con.getId(), ConstraintSet.TOP);
                } else {
                    ImageView v = (ImageView) con.getChildAt(con.getChildCount() - 2);
                    set.connect(view.getId(), ConstraintSet.TOP, v.getId(), ConstraintSet.BOTTOM);
                }
                set.applyTo(con);
            }
        };
        getSupportActionBar().hide();
        View.OnClickListener get = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                       Start();
                    }
                }).start();
            }
        };
        find.setOnClickListener(get);
        /*getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                Start();
            }
        });*/
    }

    public void Text(){
    }

    public void Start(){
        System.out.println(h == null);
        PhotoManager manager = new PhotoManager(this, con, field.getText().toString(), h);
        manager.GetPL();
    }
}