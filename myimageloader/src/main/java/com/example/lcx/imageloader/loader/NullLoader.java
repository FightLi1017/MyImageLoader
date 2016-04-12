package com.example.lcx.imageloader.loader;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.lcx.imageloader.request.BitmapRequest;

/**
 * Created by LCX on 2016/4/9.
 */
public class NullLoader extends CommonLoader {
    @Override
    protected Bitmap onLoadImage(BitmapRequest result) {
        Log.e(NullLoader.class.getSimpleName(), "your schema is wrong, your image uri is : "
                + result.Imageuri);
        return null;
    }
}
