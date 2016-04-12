package com.example.lcx.imageloader.config;

import android.util.Log;

import com.example.lcx.imageloader.cache.ImageCache;
import com.example.lcx.imageloader.cache.MemoryCache;
import com.example.lcx.imageloader.policy.LoadPolicy;
import com.example.lcx.imageloader.policy.SerialPolicy;

/**
 * Created by Administrator on 2016/4/2.
 * 图片加载的配置类 根据依赖注入 然后返回this对象 方便用户的链式调用
 */
public class ImageLoaderConfig {
    /**
     * 图片缓存对象
     */
   public ImageCache imageCache= new MemoryCache();
    /**
     * 图片加载的配置信息
     */
    public DisplayConfig displayConfig=new DisplayConfig();
    /**
     * 加载策略
     */
    public LoadPolicy loadPolicy=new SerialPolicy();

   public int ThreadCount=Runtime.getRuntime().availableProcessors()+1;

   public ImageLoaderConfig setThreadCount(int count){
       Log.d("LCX","c线程数为"+ThreadCount);
       ThreadCount=Math.max(1,count);
       return this;
   }
    public ImageLoaderConfig setImageCache(ImageCache imageCache){
        this.imageCache=imageCache;
        return this;
    }
    public ImageLoaderConfig setLoadPolicy(LoadPolicy loadPolicy){
        this.loadPolicy=loadPolicy;
        return this;
    }
    public ImageLoaderConfig setLoadid(int resid){
        displayConfig.loadingResId=resid;
        return this;
    }
    public ImageLoaderConfig setFailid(int resid){
        displayConfig.failedResId=resid;
        return this;
    }
}
