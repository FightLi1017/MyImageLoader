package com.example.lcx.imageloader.Imageloader;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by Administrator on 2016/3/18 0018.
 */
//细节依赖于抽象 这里就是图片缓存的细节 内存缓存
public class MemoryCache implements ImageCache {
    public LruCache<String,Bitmap> MemoryCache;
    public MemoryCache(){
        InitCache();
    }

    //这里我们可以做一个优化 就是存储缓存多个低像素的图片，而在后台去开线程加载高像素的图片会更加的有效
    private void InitCache() {
        //计算应用可以使用的最大内存
        int MaxMemory=(int)(Runtime.getRuntime().maxMemory()/1024);
        //去内存的8分之一作为缓存
        int MaxSize=MaxMemory/8;
        MemoryCache=new LruCache<String,Bitmap>(MaxSize){
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
            }
        };
    }

    @Override
    public Bitmap get(String url) {
        return MemoryCache.get(url);
    }

    @Override
    public void put(String url, Bitmap bit) {
        MemoryCache.put(url,bit);
    }
}
