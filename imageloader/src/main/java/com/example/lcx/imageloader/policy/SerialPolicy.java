package com.example.lcx.imageloader.policy;

import com.example.lcx.imageloader.request.BitmapRequest;

/**
 * Created by Administrator on 2016/4/2.
 */
public class SerialPolicy implements LoadPolicy {
    @Override
    public int Compare(BitmapRequest request1, BitmapRequest request2) {
        //如果前者大于后者，返回1，等于返回0，小于返回-1
        return request1.RequestNum-request2.RequestNum;
    }
}
