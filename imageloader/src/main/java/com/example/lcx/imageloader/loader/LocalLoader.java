package com.example.lcx.imageloader.loader;

import android.app.DownloadManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.example.lcx.imageloader.request.BitmapRequest;
import com.example.lcx.imageloader.utils.BitmapUtils;

import java.io.File;

/**
 * Created by LCX on 2016/4/9.
 */
public class LocalLoader extends CommonLoader {
    @Override
    protected Bitmap onLoadImage(BitmapRequest result) {
        final String imagepath= Uri.parse(result.Imageuri).getPath();
        File file=new File(imagepath);
        if(!file.exists()){
            return null;
        }
        result.JustMemCache=true;
        BitmapUtils bitmapUtils=new BitmapUtils() {
            @Override
            protected Bitmap decodeBitmapOption(BitmapFactory.Options options) {
                return BitmapFactory.decodeFile(imagepath,options);
            }
        };
        return bitmapUtils.decodeBitmap(result.getImageViewHeight(),result.getImageViewWidth());
    }
}
