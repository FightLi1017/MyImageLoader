package com.example.lcx.imageloader.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.example.lcx.imageloader.request.BitmapRequest;

/**
 * Created by Administrator on 2016/3/18 0018.
 */

public class MemoryCache implements ImageCache {
    public LruCache<String,Bitmap> MemoryCache;
    public MemoryCache(){
        InitCache();
    }


    private void InitCache() {
        int MaxMemory=(int)(Runtime.getRuntime().maxMemory()/1024);
        int MaxSize=MaxMemory/8;
        MemoryCache=new LruCache<String,Bitmap>(MaxSize){
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
            }
        };
    }

    @Override
    public void remove(BitmapRequest key) {
        MemoryCache.remove(key.Imageuri);
    }

    @Override
    public void put(BitmapRequest key, Bitmap value) {
       MemoryCache.put(key.Imageuri,value);
    }

    @Override
    public Bitmap get(BitmapRequest key) {
        return MemoryCache.get(key.Imageuri);
    }
}
