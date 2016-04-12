package com.example.lcx.imageloader.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * Created by Administrator on 2016/4/9.
 */
public abstract class BitmapUtils {

    //根据图片的宽高 来获取缩放比 从而达到图片的压缩
    public Bitmap decodeBitmap(int height,int width){
        BitmapFactory.Options options=getinJustDecodeBounds();
        //加载bitmap 第一次为true 所以不会讲bitmap加载出来 但是会有bitmap的属性
        decodeBitmapOption(options);
        calculateInSmall(options, width, height);
        return decodeBitmapOption(options);
    }

    private void calculateInSmall(BitmapFactory.Options options, int width, int height) {
        // 设置缩放比例
        options.inSampleSize = computeInSmallSize(options, width, height);

        Log.d("LCX", "$## inSampleSize = " + options.inSampleSize
                + ", width = " + width + ", height= " + height);
        // 图片质量
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        // 设置为false,解析Bitmap对象加入到内存中
        options.inJustDecodeBounds = false;
        options.inPurgeable = true;
        options.inInputShareable = true;
    }

    protected abstract Bitmap decodeBitmapOption(BitmapFactory.Options options);

    private int computeInSmallSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value,
            // this will guarantee a final image
            // with both dimensions larger than or equal to the requested
            // height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;

            // This offers some additional logic in case the image has a strange
            // aspect ratio. For example, a panorama may have a much larger
            // width than height. In these cases the total pixels might still
            // end up being too large to fit comfortably in memory, so we should
            // be more aggressive with sample down the image (=larger
            // inSampleSize).

            final float totalPixels = width * height;

            // Anything more than 2x the requested pixels we'll sample down
            // further
            final float totalReqPixelsCap = reqWidth * reqHeight * 2;

            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++;
            }
        }
        return inSampleSize;
    }

    private BitmapFactory.Options getinJustDecodeBounds() {
         BitmapFactory.Options options=new BitmapFactory.Options();
         options.inJustDecodeBounds=true;
        return options;
    }
}
