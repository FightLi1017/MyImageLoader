package com.example.lcx.imageloader.Imageloader;

import android.graphics.Bitmap;

import java.io.IOException;

/**
 * Created by Administrator on 2016/3/18 0018.
 */
public interface ImageCache {
 public Bitmap get(String url);
 public void put(String url,Bitmap bit) throws IOException;

}

