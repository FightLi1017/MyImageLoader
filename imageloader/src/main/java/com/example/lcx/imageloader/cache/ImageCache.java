package com.example.lcx.imageloader.cache;

import android.graphics.Bitmap;

import com.example.lcx.imageloader.request.BitmapRequest;

import java.io.IOException;

/**
 * Created by lichenxi on 2016/3/18 0018.
 * 图片缓存的抽象类
 * 具有的子类有无缓存（NoCache）.内存缓存（MemoryCache） sd卡缓存（DiskCace）以及双缓存（DoubleCache）
 *
 */

/**
 * 请求缓存接口
 * @author mrsimple
 * @param <K> key的类型
 * @param <V> value类型
 */
public interface ImageCache {
 public Bitmap get(BitmapRequest key);
 public void put(BitmapRequest key,Bitmap value);
 public void remove(BitmapRequest key);


}

