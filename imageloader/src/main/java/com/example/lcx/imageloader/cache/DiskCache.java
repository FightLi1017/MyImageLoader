package com.example.lcx.imageloader.cache;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.example.lcx.imageloader.DiskLruCache.DiskLruCache;
import com.example.lcx.imageloader.request.BitmapRequest;
import com.example.lcx.imageloader.utils.BitmapUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Administrator on 2016/3/18 0018.
 */

public class DiskCache implements ImageCache{
    private static final int MB=1024*1024;
    private static final String IMAGE_CACHE="bitmap";
    private DiskLruCache mdiskLruCache;
    private static DiskCache instance;

   private DiskCache(Context context){
          initDiskCache(context);
    }

    private void initDiskCache(Context context) {
        try {
              File CacheDir=getCacheDir(context,IMAGE_CACHE);
           if (!CacheDir.exists()){
          CacheDir.mkdirs();
      }
            mdiskLruCache=DiskLruCache.open(CacheDir,getAppVersion(context),1,10*MB);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private int getAppVersion(Context context) {
        try {
            PackageInfo info=context.getPackageManager().getPackageInfo(context.getPackageName(),0);
            return  info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
           return 1;
    }

    private File getCacheDir(Context context, String uniqueName) {
          String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            Log.d("", "### context : " + context + ", dir = " + context.getExternalCacheDir());
            cachePath=context.getExternalCacheDir().getPath();
        }else{
            cachePath=context.getCacheDir().getPath();
        }
    return  new File(cachePath+File.separator+uniqueName);


    }

    public static DiskCache getDiskCache(Context context) {
        if (instance == null) {
            synchronized (DiskCache.class) {
                if (instance == null) {
                    instance = new DiskCache(context);
                }
            }
        }
        return instance;
    }

    @Override
    public void put(BitmapRequest key, Bitmap value) {
    if (key.JustMemCache){
        //本地图片不缓存
        return;
    }
      DiskLruCache.Editor editor=null;
        try {
            editor=mdiskLruCache.edit(key.ImageuriMd5);
            if (editor!=null){
                OutputStream os=editor.newOutputStream(0);
              if(JoinBitmapDisk(value,os)){
                  //将写入图片提交
                        editor.commit();
              }else{
                  //取消这次提交
                    editor.abort();
              }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
  private boolean JoinBitmapDisk(Bitmap bitmap, OutputStream outputStream){
      boolean result=true;
      BufferedOutputStream bos=new BufferedOutputStream(outputStream,8*1024);
      bitmap.compress(Bitmap.CompressFormat.JPEG,100,bos);
      try {
          bos.flush();
      } catch (IOException e) {
          e.printStackTrace();
          return false;
      }finally {
          if(bos!=null){
              try {
                  bos.close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
   return result;
  }
    @Override
    public synchronized Bitmap get(final BitmapRequest key) {
           BitmapUtils bitmapUtils=new BitmapUtils() {
            @Override
            protected Bitmap decodeBitmapOption(BitmapFactory.Options options) {
                InputStream in=getBitmapstream(key.ImageuriMd5);
                Bitmap bitmap=BitmapFactory.decodeStream(in);
                 if(bitmap!=null){
                  try {
                      in.close();
                  } catch (IOException e) {
                      e.printStackTrace();
                  }
              }
                return bitmap;

            }
        };
        return bitmapUtils.decodeBitmap(key.getImageViewHeight(),key.getImageViewWidth());
    }

    private InputStream getBitmapstream(String imageuriMd5) {
        try {
            DiskLruCache.Snapshot  Snapshot=mdiskLruCache.get(imageuriMd5);
            if(Snapshot!=null){
                return Snapshot.getInputStream(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void remove(BitmapRequest key) {
        try {
            mdiskLruCache.remove(key.ImageuriMd5);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
