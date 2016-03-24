package com.example.lcx.imageloader;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lcx.imageloader.Imageloader.DiskCache;
import com.example.lcx.imageloader.Imageloader.ImageLoader;
import com.example.lcx.imageloader.Imageloader.MemoryCache;


public class MainActivity extends Activity {
   private ImageView imageview;
   private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitView();

    }

    private void InitView() {
        imageview=(ImageView)findViewById(R.id.imageView);
        btn=(Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImageLoader.getInstance().setImageCache(new MemoryCache()).displayImage(RequestConfig.meizi, imageview);
            }
        });



    }


}
