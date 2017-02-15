package com.xu.parallel.base;


public class WaitNotify {
    static final Object object=new Object();
    public static class TWait extends Thread{
        @Override
        public void run(){
            synchronized (object){
                System.out.println(System.currentTimeMillis()+" TWait start!");
                System.out.println(System.currentTimeMillis()+" TWait wait for object");
                try {
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(System.currentTimeMillis()+" TWait end");
            }
        }
    }

    public static class TNotify extends Thread{
        @Override
        public void run(){
            synchronized (object){
                System.out.println(System.currentTimeMillis()+" TNotify start! Notify one thread");
                object.notify();
                System.out.println(System.currentTimeMillis()+" TNotify sleep!");
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(System.currentTimeMillis()+" TNotify end");//TWait只有当TNotify释放了object的锁，并重新获得锁后，才能继续执行。
            }
        }
    }
    public static void main(String[] args){
        Thread tWait=new TWait();//wait()方法会释放目标的锁，而sleep()方法不会释放任何资源。
        Thread tNotify=new TNotify();
        tWait.start();
        tNotify.start();
    }
}
