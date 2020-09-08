package com.example.flickrwiththreads;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.HandlerThread;
import android.provider.Contacts;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.googlecode.flickrjandroid.Flickr;
import com.googlecode.flickrjandroid.FlickrException;
import com.googlecode.flickrjandroid.REST;
import com.googlecode.flickrjandroid.Transport;
import com.googlecode.flickrjandroid.photos.Photo;
import com.googlecode.flickrjandroid.photos.PhotoList;
import com.googlecode.flickrjandroid.photos.SearchParameters;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.parsers.ParserConfigurationException;

public class PhotoManager {
    private String search;
    private Callable<Bitmap[]> ret;
    public Context context;
    public ConstraintLayout con;

    PhotoManager(Context context, ConstraintLayout con, String search){
        this.search = search;
        this.con = con;
        this.context = context;
    }

    public void GetPL(){
        final Drawable[] draws = new Drawable[21];
        final Bitmap[] pic = new Bitmap[21];
        final Photo[] photo = new Photo[22];
        ExecutorService ser = Executors.newSingleThreadExecutor();
        ser.submit(new Runnable() {
            @Override
            public void run(){
                long start = System.currentTimeMillis();
                PhotoList pl = new PhotoList();
                FlickrToken flickrToken = new FlickrToken();
                Transport t = null;
                try {
                    t = new REST();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                }
                Flickr f = new Flickr(flickrToken.token, flickrToken.secret, t);
                SearchParameters params = new SearchParameters();
                params.setText(search);
                try {
                    pl = f.getPhotosInterface().search(params, 22, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (FlickrException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for(int i = 0; i  < pl.size(); i++){
                    String link = pl.get(i).getLargeUrl();
                    InputStream is = null;
                    try {
                        is = (InputStream) new URL(link).getContent();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    pic[i] = BitmapFactory.decodeStream(is);
                    System.out.println("map #" + i+ " was added");
                    System.out.println(pic[i]);
                }
            }
        });
        int counter = 0;
        UIGeneration ui = new UIGeneration(context, con);
        for(int i = 0; i < pic.length; i++){
            while(pic[i] == null){

            }
            ui.CreateView(pic[i]);
            System.out.println("map was sent");
        }
       /* try {
            uiThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return pic;*/
    }

}
