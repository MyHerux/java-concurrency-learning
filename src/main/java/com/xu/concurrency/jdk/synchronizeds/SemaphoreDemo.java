package com.xu.concurrency.jdk.synchronizeds;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreDemo implements Runnable{
    Semaphore semaphore=new Semaphore(5);
    @Override
    public void run() {
        try {
            semaphore.acquire();
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getId()+":done!");
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
            semaphore.release();
        }
    }

    public static void main(String[] args){
        ExecutorService service= Executors.newFixedThreadPool(20);
        SemaphoreDemo demo=new SemaphoreDemo();
        for (int i=0;i<20;i++){
            service.submit(demo);
        }
    }
}
