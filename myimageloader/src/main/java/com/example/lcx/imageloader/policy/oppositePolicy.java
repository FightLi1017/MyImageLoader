package com.example.lcx.imageloader.policy;

import com.example.lcx.imageloader.request.BitmapRequest;

/**
 * Created by Administrator on 2016/4/2.
 */
//逆序加载 从后往前进行 最后加入队列的最先加载
public class oppositePolicy implements LoadPolicy{

    @Override
    public int Compare(BitmapRequest request1, BitmapRequest request2) {
       //如果前者大于后者，返回1，等于返回0，小于返回-1
        return request2.RequestNum-request1.RequestNum;
    }
}
