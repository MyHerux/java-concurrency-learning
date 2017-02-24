package com.xu.concurrency.lock.threadLocal;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParseDateThreadLocal {

    static ThreadLocal<SimpleDateFormat> threadLocal= new ThreadLocal<>();

    public static class ParseDate implements Runnable{
        int i=0;
        ParseDate(int i){
            this.i=i;
        }
        @Override
        public void run() {
            if(threadLocal.get()==null){
                threadLocal.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            }
            try {
                Date t=threadLocal.get().parse("2017-2-21 14:29:"+i%60);
                System.out.println(t);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService= Executors.newFixedThreadPool(10);
        for (int i=0;i<10;i++){
            executorService.execute(new ParseDate(i));
        }
    }
}
