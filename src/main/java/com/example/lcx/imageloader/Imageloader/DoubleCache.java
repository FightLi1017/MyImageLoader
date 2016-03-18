package com.example.lcx.imageloader.Imageloader;

import android.graphics.Bitmap;

import java.io.IOException;

/**
 * Created by Administrator on 2016/3/18 0018.
 */
 //双缓存 ��
public class DoubleCache implements  ImageCache {
    MemoryCache memoryCache=new MemoryCache();
    DiskCache diskCache=new DiskCache();
    //先从内存中获取 如果没有 再去找sd
    @Override
    public Bitmap get(String url) {
        Bitmap bitmap=memoryCache.get(url);
        if (bitmap==null){
            bitmap=diskCache.get(url);
        }
        return bitmap;
    }

    @Override
    public void put(String url, Bitmap bit) {
         memoryCache.put(url, bit);
        try {
            diskCache.put(url, bit);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
