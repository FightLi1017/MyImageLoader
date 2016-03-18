package com.example.lcx.imageloader.Imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.audiofx.EnvironmentalReverb;
import android.os.Environment;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2016/3/18 0018.
 */
  //SD缓存 这里的sd只是一个初步的策略 后期 会尝试加入DiskLruCache这个东东
public class DiskCache implements ImageCache{
    private String CacheDir= Environment.getExternalStorageDirectory().toString();

    @Override
    public Bitmap get(String url) {
        return BitmapFactory.decodeFile(CacheDir+url);
    }

    @Override
    public void put(String url, Bitmap bit) throws IOException {
        FileOutputStream fos=null;
        try {
            fos=new FileOutputStream(CacheDir+url);
            bit.compress(Bitmap.CompressFormat.PNG,100,fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
             if(fos!=null){
                fos.close();
             }
        }
    }
}
