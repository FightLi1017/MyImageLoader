package com.example.lcx.imageloader.core;

import com.example.lcx.imageloader.request.BitmapRequest;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on 2016/4/9.
 */
public class RequestQueue {
    /**
     * 使用优先级的请求队列
     */
    private BlockingQueue<BitmapRequest> bitmapRequests= new PriorityBlockingQueue<BitmapRequest>();
    /**
     * 采用原子性的属性 保证Thread safe 为每次个请求 分配独有的序列号
     */
    private AtomicInteger atomicInteger=new AtomicInteger(0);
    //默认的线程数量
    public static int DEFAULT_NUMS = Runtime.getRuntime().availableProcessors() + 1;
    private int dispatchNums=DEFAULT_NUMS;
    //请求分发线程数组
    private RequestDispatcher[] mDispatchers=null;
    public RequestQueue(){
        this(DEFAULT_NUMS);
    }
    public RequestQueue(int Nums){
        this.dispatchNums=Nums;
    }

    public void start(){
        //stop();
        startDiapatch();
    }

    private void startDiapatch() {
      mDispatchers=new RequestDispatcher[dispatchNums];
        for (int i=0;i<mDispatchers.length;i++){
            mDispatchers[i]=new RequestDispatcher(bitmapRequests);
            mDispatchers[i].start();
        }
    }

    public void stop() {
        if (mDispatchers != null && mDispatchers.length > 0) {
            for (int i = 0; i < mDispatchers.length; i++) {
                mDispatchers[i].interrupt();
            }
        }
    }

    /**
     * 一个请求只能添加一次
     * @param request
     */
    public void addRequest(BitmapRequest request){
         if( !bitmapRequests.contains(request)){
             request.RequestNum=this.getSerialNum();
             bitmapRequests.add(request);
         }
    }
    public void  clear(){
        bitmapRequests.clear();
    }
    public BlockingQueue<BitmapRequest> getAllRequests() {
        return bitmapRequests;
    }

    /**
     * 为每次个请求 分配独有的序列号
     * @return
     */
    private int getSerialNum(){
        return  atomicInteger.incrementAndGet();
    }
}
