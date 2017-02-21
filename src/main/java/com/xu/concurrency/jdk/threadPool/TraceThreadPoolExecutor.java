package com.xu.concurrency.jdk.threadPool;


import java.util.concurrent.*;

public class TraceThreadPoolExecutor extends ThreadPoolExecutor {
    private TraceThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    public void execute(Runnable command) {
        super.execute(wrap(command, clientTrace(), Thread.currentThread().getName()));
    }

    @Override
    public Future<?> submit(Runnable task) {
        return super.submit(wrap(task, clientTrace(), Thread.currentThread().getName()));
    }

    private Exception clientTrace() {
        return new Exception("Client stack trace");
    }

    private Runnable wrap(final Runnable task, final Exception clientTask, String clientThreadName) {
        return () -> {
            try {
                task.run();
            } catch (Exception e) {
                System.out.println(clientThreadName + ":");
                clientTask.printStackTrace();
                throw e;
            }
        };
    }



    public static void main(String[] args) {
        ThreadPoolExecutor pools = new TraceThreadPoolExecutor(0, Integer.MAX_VALUE, 0L,
                TimeUnit.MILLISECONDS, new SynchronousQueue<>());

        for (int i = 0; i < 5; i++) {
            pools.execute(new DivTask(100,i));
        }
    }
}
