package com.xu.concurrency.jdk.threadPool;

public class DivTask implements Runnable {
    int a, b;

    DivTask(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public void run() {
        double re = a / b;
        System.out.println(re);
    }
}