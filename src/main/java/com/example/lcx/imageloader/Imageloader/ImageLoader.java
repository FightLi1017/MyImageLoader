package com.example.lcx.imageloader.Imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2016/3/18 0018.
 */
//ר���ṩͼƬ���ص��� ����ͼƬ�ı��� ���ݵ�һְ�� �������������ȥʵ��

public class ImageLoader {
    private static ImageLoader instance;
  //ͼƬ���� ����һ��Ĭ�ϵ�ϸ��
  ImageCache mimageCache=new MemoryCache();
  //�̳߳� �̵߳�����Ϊcpu������
   ExecutorService mExecutorService= Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
   //���õ���ģʽ
    private ImageLoader(){

    }
    public static ImageLoader getInstance(){
        if(instance==null){
            instance=new ImageLoader();
        }
        return instance;
    }
    //����ͼƬ���� ����ע��ϸ�� �����ڳ���
  public void setImageCache(ImageCache imageCache){
        mimageCache=imageCache;
    }
 public void displayImage(String url,ImageView imageView){
     Bitmap bitmap=mimageCache.get(url);
     if(bitmap!=null){
         imageView.setImageBitmap(bitmap);
         return;
     }
   //ͼƬû�л��� �ύ���̳߳�������ͼƬ
      submitloadRequest(url,imageView);
 }

    private void submitloadRequest(final String url, ImageView imageView) {
        imageView.setTag(url);
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = DownloadBitmap(url);
            }
        });
    }
    public Bitmap DownloadBitmap(String imageurl){
        Bitmap bitmap=null;
        try {
            URL url =new URL(imageurl);
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            bitmap= BitmapFactory.decodeStream(conn.getInputStream());
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

}
