package com.example.lcx.imageloader.policy;

import com.example.lcx.imageloader.request.BitmapRequest;

/**
 * Created by Administrator on 2016/4/2.
 */
public interface LoadPolicy {
    public int Compare(BitmapRequest request1,BitmapRequest request2);
}
