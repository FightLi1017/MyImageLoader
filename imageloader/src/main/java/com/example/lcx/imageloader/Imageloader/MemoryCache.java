package com.example.lcx.imageloader.Imageloader;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by Administrator on 2016/3/18 0018.
 */
//ϸ�������ڳ��� �������ͼƬ�����ϸ�� �ڴ滺��
public class MemoryCache implements ImageCache {
    public LruCache<String,Bitmap> MemoryCache;
    public MemoryCache(){
        InitCache();
    }

    //�������ǿ�����һ���Ż� ���Ǵ洢�����������ص�ͼƬ�����ں�̨ȥ���̼߳��ظ����ص�ͼƬ����ӵ���Ч
    private void InitCache() {
        //����Ӧ�ÿ���ʹ�õ�����ڴ�
        int MaxMemory=(int)(Runtime.getRuntime().maxMemory()/1024);
        //ȥ�ڴ��8��֮һ��Ϊ����
        int MaxSize=MaxMemory/8;
        MemoryCache=new LruCache<String,Bitmap>(MaxSize){
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
            }
        };
    }

    @Override
    public Bitmap get(String url) {
        return MemoryCache.get(url);
    }

    @Override
    public void put(String url, Bitmap bit) {
        MemoryCache.put(url,bit);
    }
}
