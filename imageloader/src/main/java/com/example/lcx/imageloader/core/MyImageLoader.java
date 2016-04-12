package com.example.lcx.imageloader.core;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.lcx.imageloader.cache.ImageCache;
import com.example.lcx.imageloader.cache.MemoryCache;
import com.example.lcx.imageloader.config.DisplayConfig;
import com.example.lcx.imageloader.config.ImageLoaderConfig;
import com.example.lcx.imageloader.policy.LoadPolicy;
import com.example.lcx.imageloader.policy.SerialPolicy;
import com.example.lcx.imageloader.request.BitmapRequest;

/**
 *图片加载类 支持url和本地图片的uri形式 根据路径来判断是网络的还是本地的 如果是网络的就让网络来获取 如果是本地的就交给mExecutorService从sd卡中加载
 *
 */
public class MyImageLoader {
    //实例化
    private static MyImageLoader instance;
    //配置对象
    private ImageLoaderConfig mConfig;
    //缓存
    private volatile ImageCache mCahce=new MemoryCache();
    //网路请求队列
    private RequestQueue mRequestQueue;
    private MyImageLoader(){

    }
  public static   MyImageLoader getInstance(){
       if(instance==null){
           synchronized (MyImageLoader.class){
               if (instance==null){
                   instance=new MyImageLoader();
               }
           }
       }
        return instance;
    }

   public void init(ImageLoaderConfig config){
       this.mConfig=config;
       this.mCahce=config.imageCache;
       CheckConfig();
       //请求队列留下 看完书以后 在完善他
       mRequestQueue=new RequestQueue(mConfig.ThreadCount);
       mRequestQueue.start();

   }

    private void CheckConfig() {
        if(mConfig==null){
            throw new RuntimeException("the config is null please call the init method to initialize");

        }
        if(mConfig.loadPolicy==null){
            mConfig.loadPolicy=new SerialPolicy();
        }
        if(mCahce==null){
            mCahce=new MemoryCache();
        }
    }

    public void displayImage(ImageView imageView, String uri) {
        displayImage(imageView, uri, null, null);
    }

    public void displayImage(ImageView imageView, String uri, DisplayConfig config) {
        displayImage(imageView, uri, config, null);
    }

    public void displayImage(ImageView imageView, String uri, MyImageLoader.ImageObserve listener) {
        displayImage(imageView, uri, null, listener);
    }

    public void displayImage(final ImageView imageView, final String uri,
                             final DisplayConfig config, final MyImageLoader.ImageObserve listener) {
        BitmapRequest request = new BitmapRequest(imageView, uri, config, listener);
        // 加载的配置对象,如果没有设置则使用ImageLoader的配置
        request.displayConfig = request.displayConfig != null ? request.displayConfig
                : mConfig.displayConfig;
        // 添加对队列中
        mRequestQueue.addRequest(request);
    }

   public  ImageLoaderConfig getConfig(){
       return mConfig;
   }

    public void stop() {
        mRequestQueue.stop();
    }
    public static interface ImageObserve{
        public void FinishDown(ImageView imageView, Bitmap bitmap, String url);

    }
}
