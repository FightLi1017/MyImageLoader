package com.example.lcx.imageloader.loader;

import android.util.ArrayMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/9.
 */
public class LoaderManager {
    public static final String HTTP="http";
    public static final String HTTPS="https";
    public static final String FILE="file";
   private Map<String,Loader> loaderMap=new HashMap<String,Loader>();
    private static LoaderManager instance;
   private  LoaderManager(){
       register(HTTP,new UrlLoader());
       register(HTTPS,new UrlLoader());
       register(FILE,new LocalLoader());
   }
   public static LoaderManager getinstance(){
       if (instance==null){
             synchronized (LoaderManager.class){
                 if (instance==null){
                     instance=new LoaderManager();
                 }
             }
       }
       return instance;
   }
    public final synchronized void register(String schema,Loader loader){
        loaderMap.put(schema,loader);
    }
    public Loader getLoader(String url){
        if(loaderMap.containsKey(url)){
            return loaderMap.get(url);
        }
        return new NullLoader();

    }
}
