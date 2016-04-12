package com.example.lcx.imageloader.request;

import android.widget.ImageView;

import com.example.lcx.imageloader.config.DisplayConfig;
import com.example.lcx.imageloader.core.MyImageLoader;
import com.example.lcx.imageloader.policy.LoadPolicy;
import com.example.lcx.imageloader.utils.Md5Helper;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2016/4/2.
 */

/**
 * 我们要将一个对象 有序的放到集合中
 */
public class BitmapRequest implements Comparable<BitmapRequest> {
    WeakReference<ImageView> mImageViewRef;
     public DisplayConfig displayConfig;
    public MyImageLoader.ImageObserve imageObserve;
    public String Imageuri="";
    public String ImageuriMd5="";
    public int RequestNum=0;
    /**默认不取消请求
     *
     */
    public boolean isCancal=false;
    /**
      仅仅只是内存缓存
     */
    public boolean JustMemCache=false;
    /**
     * 获取加载策略
     */
    LoadPolicy policy=MyImageLoader.getInstance().getConfig().loadPolicy;

   public BitmapRequest(ImageView imageView, String url, DisplayConfig config, MyImageLoader.ImageObserve imageObserve){
    this.mImageViewRef=new WeakReference<ImageView>(imageView);
       imageView.setTag(url);
       this.displayConfig=config;
    this.imageObserve=imageObserve;
    this.Imageuri=url;
    this.ImageuriMd5= Md5Helper.toMD5(url);

   }
    public void setLoadPolicy(LoadPolicy policy){
        if(policy!=null){
            this.policy=policy;
        }
    }
    public int getImageViewHeight(){
      return   mImageViewRef.get().getHeight();
    }
    public int getImageViewWidth(){
        return mImageViewRef.get().getWidth();
    }
    public ImageView getImageView(){
        return mImageViewRef.get();
    }
    @Override
    public int compareTo(BitmapRequest another) {

        return policy.Compare(this,another);
    }
}
