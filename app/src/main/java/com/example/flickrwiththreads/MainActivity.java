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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.googlecode.flickrjandroid.photos.SearchParameters;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    public ConstraintLayout con;
    public ConstraintLayout parent;
    public NestedScrollView scroll;
    public int[] color = new int[]{Color.GREEN, Color.YELLOW};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        parent = findViewById(R.id.parent);
        con = findViewById(R.id.con);
        scroll = findViewById(R.id.scroll);



        getSupportActionBar().hide();
        Toast.makeText(this, "Its ok", Toast.LENGTH_LONG);
        Text();
    }

    public void Text(){
        System.out.println("In the TExt method");
        Callable<String> ser = new Callable<String>(){
            @Override
            public String call() throws Exception {
                System.out.println("In the thread");
                Thread.sleep(300);
                Random rn = new Random();
                if(rn.nextInt(2) == 1)
                return "NULL";
                else return "NONE";
            }
        };
        System.out.println(ser);
        for(int i = 0; i > 10; i++){
            System.out.println(1);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void Start(){
        PhotoManager manager = new PhotoManager(this);
        Bitmap[] drawable =  manager.GetPL();
        System.out.println("draws are ready");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        UIGeneration ui = new UIGeneration(drawable, this, con);
        ui.CreateViews();
    }
}