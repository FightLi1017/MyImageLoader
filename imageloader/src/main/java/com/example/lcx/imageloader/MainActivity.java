package com.example.lcx.imageloader;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.lcx.imageloader.cache.DiskCache;
import com.example.lcx.imageloader.cache.MemoryCache;
import com.example.lcx.imageloader.config.ImageLoaderConfig;
import com.example.lcx.imageloader.core.MyImageLoader;
import com.example.lcx.imageloader.policy.SerialPolicy;


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
        ImageLoaderConfig config=new ImageLoaderConfig()
                .setThreadCount(9)
                .setImageCache(DiskCache.getDiskCache(this))
                //设置加载策略
                .setLoadPolicy(new SerialPolicy())
                .setLoadid(R.mipmap.ic_launcher)
                .setFailid(R.mipmap.jiazaishiei);
                MyImageLoader.getInstance().init(config);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyImageLoader.getInstance().displayImage(imageview,RequestConfig.meizi);
    }
}
