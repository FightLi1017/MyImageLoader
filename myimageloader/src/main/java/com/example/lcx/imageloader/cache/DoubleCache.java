package com.example.lcx.imageloader.cache;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.lcx.imageloader.request.BitmapRequest;

import java.io.IOException;

/**
 * Created by Administrator on 2016/3/18 0018.
 */
 //双缓存 ��
public class DoubleCache implements  ImageCache {
    MemoryCache memoryCache=new MemoryCache();
    DiskCache diskCache=null;
    //先从内存中获取 如果没有 再去找sd


    public DoubleCache(Context context) {
        super();
        diskCache=DiskCache.getDiskCache(context);
    }

    @Override
    public Bitmap get(BitmapRequest key) {
        return null;
    }

    @Override
    public void put(BitmapRequest key, Bitmap value) {

    }

    @Override
    public void remove(BitmapRequest key) {

    }
}
