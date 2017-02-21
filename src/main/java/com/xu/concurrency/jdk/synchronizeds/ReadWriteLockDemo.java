package com.xu.concurrency.jdk.synchronizeds;


import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {
    Lock lock=new ReentrantLock();
    private static ReentrantReadWriteLock readWriteLock=new ReentrantReadWriteLock();
    private static Lock readLock=readWriteLock.readLock();
    static Lock writeLock=readWriteLock.writeLock();
    private int value;

    private Object handleRead(Lock lock) throws InterruptedException {
        lock.lock();
        try {
            Thread.sleep(1000);
            System.out.println(value);
            return value;
        }finally {
            lock.unlock();
        }
    }

    private void handleWrite(Lock lock, int index) throws InterruptedException {
        try {
            lock.lock();
            Thread.sleep(1000);
            System.out.println("write a value");
            value=index;
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args){
        ReadWriteLockDemo demo=new ReadWriteLockDemo();

        Runnable readRunnable= () -> {
            try {
                demo.handleRead(readLock);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Runnable writeRunnable=()->{
            try {
                demo.handleWrite(writeLock,new Random().nextInt());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        for (int i=0;i<10;i++){
            for (int j=0;j<5;j++){
                new Thread(readRunnable).start();
            }
            new Thread(writeRunnable).start();
            for (int j=0;j<5;j++){
                new Thread(readRunnable).start();
            }
        }

    }
}
