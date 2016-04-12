package com.example.lcx.imageloader.core;

import android.util.Log;

import com.example.lcx.imageloader.loader.Loader;
import com.example.lcx.imageloader.loader.LoaderManager;
import com.example.lcx.imageloader.request.BitmapRequest;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Administrator on 2016/4/9.
 */
public class RequestDispatcher extends Thread {
    /**
     * 网络请求队列 依赖于抽象 用于进行图片的获取
     */
    private BlockingQueue<BitmapRequest> bitmapRequestRequestQueue;
    public RequestDispatcher(BlockingQueue<BitmapRequest> queue){
        this.bitmapRequestRequestQueue=queue;
    }

    @Override
    public void run() {
            try {
                  while(!this.isInterrupted()) {
                      BitmapRequest request=bitmapRequestRequestQueue.take();
                      if (request.isCancal) {
                          continue;
                      }
                     //解析地址的头格式
                      String schema=parseSchema(request.Imageuri);
                      Loader imageLoader= LoaderManager.getinstance().getLoader(schema);
                      imageLoader.loadImage(request);
                 }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    private String parseSchema(String imageuri) {
        if(imageuri.contains("://")){
            return imageuri.split("://")[0];
        }else{
            Log.e(getName(),"imageuri is incorrect");
            throw new RuntimeException("这位帅哥 怎么你的下载地址格式不对啊 你还是在检查一下吧");

        }
    }


}
