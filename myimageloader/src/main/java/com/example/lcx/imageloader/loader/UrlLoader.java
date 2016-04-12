package com.example.lcx.imageloader.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.lcx.imageloader.request.BitmapRequest;
import com.example.lcx.imageloader.utils.BitmapUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by LCX on 2016/4/9.
 */
public class UrlLoader extends CommonLoader {

    @Override
    protected Bitmap onLoadImage(BitmapRequest result) {
        String imageuriurl=result.Imageuri;
        InputStream is=null;
        try {
             URL url=new URL(imageuriurl);
             final  HttpURLConnection conn=(HttpURLConnection)url.openConnection();
             is= new BufferedInputStream(conn.getInputStream());
           final byte[] buff=inputStream2ByteArr(is);
            //在此输入流中标记当前的位置。对 reset 方法的后续调用会在最后标记的位置重新定位此流，以便后续读取重新读取相同的字节
            //is.mark(is.available());
            //final InputStream inputStream = is;
            /**
             * 匿名内部类中，可以隐含实现一个接口、或者是一个类，当然包括抽象类。只不过该匿名类一定要实现抽象类中的抽象方法才可以。
             */
             BitmapUtils bitmapUtils=new BitmapUtils() {
                @Override
                protected Bitmap decodeBitmapOption(BitmapFactory.Options options) {
                    //Log.d("LCX",inputStream+"111111");
                    //Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);
                    Bitmap bitmap=BitmapFactory.decodeByteArray(buff,0,buff.length,options);
//                    if (options.inJustDecodeBounds){
//                        try {
//                            //将此流重新定位到最后一次对此输入流调用 mark 方法时的位置。
//                            //inputStream.reset();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }else{
//                         conn.disconnect();
//                    }
                    Log.d("LCX",bitmap!=null?"111bitmap不是空的":"111bitmap是空的");
                    return bitmap;
                }
            };
              return bitmapUtils.decodeBitmap(result.getImageViewHeight(),result.getImageViewWidth());
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private byte[] inputStream2ByteArr(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        int len = 0;
        while ( (len = inputStream.read(buff)) != -1) {
            outputStream.write(buff, 0, len);
        }
        inputStream.close();
        outputStream.close();
        return outputStream.toByteArray();
    }

}
