package com.example.lcx.imageloader.Imageloader;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.audiofx.EnvironmentalReverb;
import android.os.Environment;
import android.util.Log;

import com.example.lcx.imageloader.DiskLruCache.DiskLruCache;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
    public Bitmap get(String url) {
        //return BitmapFactory.decodeFile(CacheDir+url);
        return null;
    }

    @Override
    public void put(String url, Bitmap bit) throws IOException {
//        FileOutputStream fos=null;
//        try {
//            fos=new FileOutputStream(CacheDir+url);
//            Log.d("LCX",CacheDir+url);
//            bit.compress(Bitmap.CompressFormat.PNG,100,fos);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } finally {
//             if(fos!=null){
//                fos.close();
//             }
//        }
    }
}
