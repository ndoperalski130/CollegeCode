package com.example.congestion;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Map extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
    }

    // getMap function
    public void getMap(View v)
    {
        WebView webView = (WebView) findViewById(R.id.theImage);        // find the hidden WebView
        WebSettings webSettings = webView.getSettings();                // get WebView settings
        webSettings.setJavaScriptEnabled(true);                         // enable Javascript on the WebView
        webSettings.setDomStorageEnabled(true);                         // allows sending of data between phone/backend

        webView.loadUrl("http://compsci04.snc.edu/cs460/2021/dopend/PHP%20&%20JS/index.php");   // load the web page into the WebView
    }
}
