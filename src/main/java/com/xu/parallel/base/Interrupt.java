package com.xu.parallel.base;


public class Interrupt {

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            while (true){
                if (Thread.currentThread().isInterrupted()){
                    System.out.println("Interrupted");
                    break;
                }
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();//sleep()方法由于中断而抛出异常，会清除中断标记，所以重新设置中断标记位。
            }
            Thread.yield();
        });
        t1.start();
        Thread.sleep(3000);
        t1.interrupt();
    }
}
