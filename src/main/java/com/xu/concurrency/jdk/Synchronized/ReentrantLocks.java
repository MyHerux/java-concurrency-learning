package com.xu.concurrency.jdk.Synchronized;


import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLocks implements Runnable{
    private ReentrantLock lock=new ReentrantLock();
    private static int i=0;
    @Override
    public void run() {
        for (int j=0;j<1000000;j++){
            lock.lock();
            i++;
            lock.unlock();
        }
    }
    public static void main(String args[]) throws InterruptedException {
        ReentrantLocks reentrantLocks=new ReentrantLocks();
        Thread t1=new Thread(reentrantLocks);
        Thread t2=new Thread(reentrantLocks);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }
}
