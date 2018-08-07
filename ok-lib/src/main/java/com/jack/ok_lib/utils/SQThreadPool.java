package com.jack.ok_lib.utils;

import android.support.annotation.NonNull;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by dell on 2018/6/30.
 */

public class SQThreadPool extends ThreadPoolExecutor {

    private static AtomicBoolean lock = new AtomicBoolean();
    private static Map<String, ThreadPoolExecutor> poolManager = new ConcurrentHashMap<>();
    private static ThreadFactory defauleThreadFactory = new DefaultThreadFactory();

    private SQThreadPool(String poolName,
                         int corePoolSize,
                         int maximumPoolSize,
                         long keepAliveTime,
                         TimeUnit unit,
                         BlockingQueue<Runnable> workQueue,
                         ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);

        if (!poolManager.containsKey(poolName))
            poolManager.put(poolName, this);
    }

    /**
     * get instance from map directly, this may cause NullPointerException
     *
     * @param poolName
     * @return
     */
    @NonNull
    public static ThreadPoolExecutor getInstance(String poolName) {

        if (poolManager.get(poolName) == null)
            getInstance(poolName, 3, 3 * 2, 5, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());

        return poolManager.get(poolName);
    }

    /**
     * @param poolName
     * @param corePoolSize
     * @param maximumPoolSize
     * @param keepAliveTime
     * @param timeUnit
     * @param workQueue
     * @return
     */
    @NonNull
    public static ThreadPoolExecutor getInstance(@NonNull String poolName,
                                                 int corePoolSize,
                                                 int maximumPoolSize,
                                                 long keepAliveTime,
                                                 TimeUnit timeUnit,
                                                 BlockingQueue<Runnable> workQueue) {

        while (!lock.compareAndSet(false, true)) ;

        ThreadPoolExecutor pool = poolManager.get(poolName);
        try {
            if (pool == null) {
                pool = new SQThreadPool(poolName, corePoolSize,
                        maximumPoolSize, keepAliveTime, timeUnit, workQueue, defauleThreadFactory);
                poolManager.put(poolName, pool);
                return pool;
            } else {
                return poolManager.get(poolName);
            }
        } finally {
            lock.compareAndSet(true, false);
        }
    }

    static class DefaultThreadFactory implements ThreadFactory {

        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;
        private int stackSize = 0;

        public DefaultThreadFactory() {
            SecurityManager securityManager = System.getSecurityManager();
            group = (securityManager != null) ? securityManager.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = "sq-pool-" + poolNumber.getAndIncrement() + "-thread-";
        }

        @Override
        public Thread newThread(@NonNull Runnable r) {
            Thread thread = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), stackSize);
            if (thread.isDaemon()) {
                thread.setDaemon(false);
            }

            if (thread.getPriority() != Thread.NORM_PRIORITY) {
                thread.setPriority(Thread.NORM_PRIORITY);
            }
            return thread;
        }
    }

}
