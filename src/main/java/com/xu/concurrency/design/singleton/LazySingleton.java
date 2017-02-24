package com.xu.concurrency.design.singleton;


public class LazySingleton {

    private LazySingleton(){

    }

    private static LazySingleton instance=null;

    public static synchronized LazySingleton getInstance(){
        if(instance == null)
            instance=new LazySingleton();
        return instance;
    }
}
