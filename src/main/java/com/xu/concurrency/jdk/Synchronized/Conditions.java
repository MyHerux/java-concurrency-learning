package com.xu.concurrency.jdk.Synchronized;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Conditions implements Runnable{
    static ReentrantLock lock=new ReentrantLock();
    static Condition condition=lock.newCondition();
    @Override
    public void run() {
        try {
            System.out.println("Thread is wait");
            lock.lock();
            condition.await();
            System.out.println("Thread is going on");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        Conditions conditions=new Conditions();
        Thread t1=new Thread(conditions);
        t1.start();
        System.out.println("sleep 2000");
        Thread.sleep(2000);
        lock.lock();
        System.out.println("sleep 2000");
        Thread.sleep(2000);
        condition.signal();
        lock.unlock();
    }

}
