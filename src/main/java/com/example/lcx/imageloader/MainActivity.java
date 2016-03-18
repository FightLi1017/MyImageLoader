package com.example.lcx.imageloader;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.lcx.imageloader.Imageloader.ImageLoader;


public class MainActivity extends Activity {
   private ImageView imageview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitView();
    }

    private void InitView() {
        imageview=(ImageView)findViewById(R.id.imageView);
        ImageLoader.getInstance().displayImage(RequestConfig.meizi,imageview);
    }


}
