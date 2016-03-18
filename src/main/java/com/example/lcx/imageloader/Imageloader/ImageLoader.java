package com.example.lcx.imageloader.Imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2016/3/18 0018.
 */
//专门提供图片下载的类 至于图片的保存 根据单一职责 我们有另外的类去实现

public class ImageLoader {
    private static ImageLoader instance;
  //图片缓存 设置一个默认的细节
  ImageCache mimageCache=new MemoryCache();
  //线程池 线程的数量为cpu的数量
   ExecutorService mExecutorService= Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
   //设置单例模式
    private ImageLoader(){

    }
    public static ImageLoader getInstance(){
        if(instance==null){
            instance=new ImageLoader();
        }
        return instance;
    }
    //设置图片缓存 依赖注入细节 依赖于抽象
  public void setImageCache(ImageCache imageCache){
        mimageCache=imageCache;
    }
 public void displayImage(String url,ImageView imageView){
     Bitmap bitmap=mimageCache.get(url);
     if(bitmap!=null){
         imageView.setImageBitmap(bitmap);
         return;
     }
   //图片没有缓存 提交到线程池中下载图片
      submitloadRequest(url,imageView);
 }

    private void submitloadRequest(final String url, ImageView imageView) {
        imageView.setTag(url);
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = DownloadBitmap(url);
            }
        });
    }
    public Bitmap DownloadBitmap(String imageurl){
        Bitmap bitmap=null;
        try {
            URL url =new URL(imageurl);
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            bitmap= BitmapFactory.decodeStream(conn.getInputStream());
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

}
