package com.xu.concurrency.lock.threadLocal;


import java.util.Random;
import java.util.concurrent.*;

public class ThreadLocalPerform {

    private static final int GEN_COUNT=10_000_000;
    private static final int THREAD_COUNT=4;
    private static  ExecutorService executorService= Executors.newFixedThreadPool(THREAD_COUNT);
    static Random random=new Random(123);

    static ThreadLocal<Random> tRandom= ThreadLocal.withInitial(() -> new Random(123));

    public static class RndTask implements Callable<Long>{

        private int mode=0;

        RndTask(int mode){
            this.mode=mode;
        }

        Random getRandom(){
            if(mode==0){
                return random;
            }else if(mode==1){
                return tRandom.get();
            }else {
                return null;
            }
        }

        @Override
        public Long call() throws Exception {
            long b=System.currentTimeMillis();
            for (long i=0;i<GEN_COUNT;i++){
                getRandom().nextInt();
            }
            long e=System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName()+"spend"+(e-b)+"ms");
            return e-b;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Future<Long>[] futures=new Future[THREAD_COUNT];
        for (int i=0;i<THREAD_COUNT;i++){
            futures[i]=executorService.submit(new RndTask(0));
        }
        long totalTime=0;
        for(int i=0;i<THREAD_COUNT;i++){
            totalTime+=futures[i].get();
        }
        System.out.println("多线程同实例："+totalTime+"ms");

        for (int i=0;i<THREAD_COUNT;i++){
            futures[i]=executorService.submit(new RndTask(1));
        }
        totalTime=0;
        for(int i=0;i<THREAD_COUNT;i++){
            totalTime+=futures[i].get();
        }
        System.out.println("ThreadLocal包装：Random实例"+totalTime+"ms");
        executorService.shutdown();
    }

}
