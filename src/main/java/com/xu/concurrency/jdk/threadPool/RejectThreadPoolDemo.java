package com.xu.concurrency.jdk.threadPool;

import java.util.concurrent.*;

/**
 * Created by xu on 2017/2/17.
 */
public class RejectThreadPoolDemo {

    public static class MyTask implements Runnable {

        @Override
        public void run() {
            System.out.println("Thread ID:>" + Thread.currentThread().getId());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]) throws InterruptedException {
        MyTask task = new MyTask();
        //自定义拒绝策略
        ExecutorService es = new ThreadPoolExecutor(5, 5, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(10),
                Executors.defaultThreadFactory(),
                (r, executor) -> System.out.println( r + " is discard"));

//        for (int i = 0; i < 100; i++) {
//            es.submit(task);
//            Thread.sleep(10);
//        }

        //自定义线程创建
        ExecutorService es2 = new ThreadPoolExecutor(5, 5, 0L,
                TimeUnit.MILLISECONDS, new SynchronousQueue<>(),
                r -> {
                    Thread t = new Thread(r);
                    t.setDaemon(true);
                    System.out.println("create "+t);
                    return t;
                });
        for (int i=0;i<5;i++){
            es2.submit(task);
        }
        Thread.sleep(1000);
    }
}
