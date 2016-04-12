package com.example.lcx.imageloader.loader;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.example.lcx.imageloader.cache.ImageCache;
import com.example.lcx.imageloader.config.DisplayConfig;
import com.example.lcx.imageloader.core.MyImageLoader;
import com.example.lcx.imageloader.request.BitmapRequest;

/**
 * Created by Administrator on 2016/4/8
 * 加载图片的公共步骤
 *    1 判断缓存中是否有图片
 *    2如果有就将图片直接放到Ui线程里面 并且更新Ui
 *    3如果没有图片 就利用网络加载图片 然后再放入Cache中 在放到Ui线程里面.
 */

public abstract class CommonLoader implements Loader {
    //在类初始化的时候 获取图片的缓存 缓存独享应该是共享的
    private static ImageCache cache= MyImageLoader.getInstance().getConfig().imageCache;
    @Override
    public void loadImage(BitmapRequest request) {
      Bitmap bitmap=cache.get(request);
        if(bitmap==null){
            showloading(request);
            //没有缓存 就需要加载图片 让子类来实现
            bitmap=onLoadImage(request);
            //将图片放入缓存中
            Log.d("LCX",bitmap==null?"bitmap是空的":"bitmap不是空的");
            cacheBitmap(request,bitmap);
        }else{
            request.JustMemCache = true;
          //好像也可以不加
        }
          ToUIThread(request, bitmap);

    }

    private void ToUIThread(final BitmapRequest request, final Bitmap bitmap) {
        ImageView Imageview=request.getImageView();
        if(Imageview==null){
            return ;
        }
        Imageview.post(new Runnable() {
        @Override
        public void run() {
            Log.d("LCX","放到主线程里面去");
             updateview(request,bitmap);
        }
    });

    }

    /**
     *更新Imageview的显示
     * @param request
     * @param bitmap
     */
    private  void updateview(BitmapRequest request, Bitmap bitmap){
     ImageView ImageView=request.getImageView();
      String uri=request.Imageuri;
        if(bitmap!=null&&ImageView.getTag().equals(uri)){
            ImageView.setImageBitmap(bitmap);
        }
   //failed
   if (bitmap==null&&ifHaveFailedRessourseId(request.displayConfig)){
       Log.d("LCX","显示失败加载的图片");
       ImageView.setImageResource(request.displayConfig.failedResId);
   }
          //接口回调 让ImageLoader可以
        if(request.imageObserve!=null){
             request.imageObserve.FinishDown(ImageView,bitmap,request.Imageuri);
        }
    }
    private void cacheBitmap(BitmapRequest request, Bitmap bitmap) {
        Log.d("LCX","放到缓存去");
              if(bitmap!=null && cache!=null){
                  cache.put(request,bitmap);
              }
    }

    //只是将下载图片的部分 交给子类去完成 因为我们无法确定是从哪里去下载（网上，还是本地）
    protected abstract Bitmap onLoadImage(BitmapRequest result);
    /**
     *显示正在加载的图像
     * @param request
     */
    private void showloading( final BitmapRequest request) {
     final ImageView imageView=request.getImageView();
        if(IfHaveResourseId(request.displayConfig)) {
            imageView.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageResource(request.displayConfig.loadingResId);
                }
            });
        }

    }

    /**
     * 用户是否设置了正在加载的图片ID
     * @param displayConfig
     * @return
     */
    private  boolean IfHaveResourseId(DisplayConfig displayConfig){
        return displayConfig!=null && displayConfig.loadingResId>0;
    }

    /**
     * 用户是否设置了加载失败时的图片Id
     * @param displayConfig
     * @return
     */
    private boolean ifHaveFailedRessourseId(DisplayConfig displayConfig) {
        return displayConfig!=null&&displayConfig.failedResId>0;
    }
}
