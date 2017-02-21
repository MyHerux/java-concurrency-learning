package com.xu.concurrency.jdk.synchronizeds;


import java.util.concurrent.locks.ReentrantLock;

public class FairLock implements Runnable {
    ReentrantLock fairLock = new ReentrantLock(true);

    @Override
    public void run() {
        while (true) {
            try {
                fairLock.lock();
                System.out.println(Thread.currentThread().getName() + " get lock");
            } finally {
                fairLock.unlock();
            }
        }
    }

    public static void main(String[] args){
        FairLock fairLock=new FairLock();
        Thread t1=new Thread(fairLock,"t1");
        Thread t2=new Thread(fairLock,"t2");
        t1.start();
        t2.start();
    }
}
