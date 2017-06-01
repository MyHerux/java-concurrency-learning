# 并发实践

## 目的

> 优秀的并发能够提高程序的效率以及减少错误

## 一些实践

- 线程复用：线程池

    1.线程池工厂方法

    `newFixedThreadPool`: 返回固定线程数量的线程池。当有一个新的任务提交时，线程池若有空闲线程，则立即执行。若没有，则新的任务会被暂存到一个任务队列中，待线程空闲时，便处理在任务队列中的任务。

    `newSingleThreadExecutor`：返回只有一个线程的线程池。若多余一个任务被提及，则保存到任务队列中，线程空闲时按照先入先出的顺序执行任务。

    `newCachedThreadPool`：返回一个可根据实际情况调整线程数量的线程池。线程池的线程数量不确定，但若有空闲线程可以复用，则会有限使用可复用的线程。若所有线程均在工作，又有新任务提交，则创建新的线程处理任务。当所有线程在任务执行完之后，将返回线程池进行复用。

    `newSingleThreadScheduledExecutor`：返回一个`ScheduledExecutorService`对象,线程池大小为1。`ScheduledExecutorService`接口在`ExecutorService`接口之上扩展了给定时间执行某任务的功能。

    `newScheduledThreadPool`：返回指定线程数量的`ScheduledExecutorService`对象。

    2.线程池的内部实现

    ```
        public ThreadPoolExecutor(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue,
                              ThreadFactory threadFactory,
                              RejectedExecutionHandler handler)
    ```
    `corePoolSize`:指定线程池中的线程数量。

    `maximumPoolSize`：指定线程池中的最大线程数量。

    `keepAliveTime`：超过corePoolSize的空闲线程，在多长时间内，会被销毁。

    `unit`：keepAliveTime的单位。

    `workQueue`：任务队列。

    `threadFactory`：线程工厂，用于创建线程，一般默认。

    `handler`：拒绝策略。当任务太多时如何拒绝任务。

    3.线程池的数量

    Ncpu=CPU数量

    Ucup=CPU的使用率

    W/C=等待时间与计算时间的比率

    最优池： `Nthreads=Ncpu*Ncpu*（1+W/C）`

- JDK的并发容器

    `ConcurrentHashMap`：一个高效的并发 HashMap（线程安全）。

    `CopyOnWriteArrayList`：在读多写少的场合，这个List的性能非常好，远远好于Vector。

    `ConcurrentLinkedQueue`：高效的并发队列，使用链表实现。可以看做一个线程安全的LinkedList。

    `BlockingQueue`：这是一个接口，JDK内部通过链表、数组等方式实现了这个接口。表示阻塞队列，非常适用于数据共享的通道。

    `ConcurrentSkipListMap`：调表的实现。

    除了并发包中的数据结构外，Vector也是线程安全的（性能低一些），另外Collections工具类可以帮助我们将任意集合包装成线程安全的集合。Like：`Collections.synchronizedMap(new HashMap())`。

- ThreadLocal

    ```
    public class ParseDateThreadLocal {

        static ThreadLocal<SimpleDateFormat> threadLocal= new ThreadLocal<>();

        public static class ParseDate implements Runnable{
            int i=0;
            ParseDate(int i){
                this.i=i;
            }
            @Override
            public void run() {
                if(threadLocal.get()==null){
                    threadLocal.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
                }
                try {
                    Date t=threadLocal.get().parse("2017-2-21 14:29:"+i%60);
                    System.out.println(t);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        public static void main(String[] args) throws InterruptedException {
            ExecutorService executorService= Executors.newFixedThreadPool(10);
            for (int i=0;i<10;i++){
                executorService.execute(new ParseDate(i));
            }
        }
    }
    ```

- 无锁

    1.比较交换（CAS）

        算法过程：它包含三个参数`CAS（V,E,N）`。`V`表示要更新的变量，`E`表示预期值，`N`表示新值。
        仅当V值等于E值时，才会将V的值设为N，如果V值与E值不同，则说明已经有其他线程做了更新，则当前线程什么都不做。
        最后，CAS返回当前V的真实值。当多个线程同时使用CAS操作一个变量时，只有一个会胜出，并成功更新，其余均会失败。
        失败的线程不会挂起而是允许再次尝试。
    2.无锁的线程安全整数：`AtomicInteger`

    3.无锁的对象引用：`AtomicReference`

    4.带有时间戳的引用对象：`AtomicStampedReference`

    5.无锁数组：`AtomicIntegerArray`

    6.原子操作普通变量：`AtomicIntegerFieldUpdater`

- NIO与AIO

    NIO：准备好了再通知我。

    AIO：读完了再通知我

## 最后

> 同样，面临多线程共享资源的问题的时候，加个锁就能快速简单的达到目的更。但是，这显然不是最优方法。希望大家在处理并发的时候也能够灵机一动，想起曾经看到过的好的并发的实现，自己来实现一个优秀的并发情况。

