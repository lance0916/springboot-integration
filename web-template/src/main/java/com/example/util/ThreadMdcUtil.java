package com.example.util;

import com.example.bean.constants.TraceConstant;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.slf4j.MDC;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * 对线程进行包装，使traceId可以传入线程池。
 * @author WuQinglong
 */
public class ThreadMdcUtil {

    public static void setTraceIdIfAbsent() {
        if (MDC.get(TraceConstant.TRACE_ID) == null) {
            MDC.put(TraceConstant.TRACE_ID, TraceUtil.generateTraceId());
        }
    }

    public static void setTraceId() {
        MDC.put(TraceConstant.TRACE_ID, TraceUtil.generateTraceId());
    }

    public static void setTraceId(String traceId) {
        MDC.put(TraceConstant.TRACE_ID, traceId);
    }

    public static <T> Callable<T> wrap(final Callable<T> callable, final Map<String, String> context) {
        return () -> {
            if (context == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }
            setTraceIdIfAbsent();
            try {
                return callable.call();
            } finally {
                MDC.clear();
            }
        };
    }

    public static Runnable wrap(final Runnable runnable, final Map<String, String> context) {
        return () -> {
            if (context == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }
            setTraceIdIfAbsent();
            try {
                runnable.run();
            } finally {
                MDC.clear();
            }
        };
    }

    /**
     * Spring定时任务线程池的封装
     */
    public static class ThreadPoolTaskExecutorMdcWrapper extends ThreadPoolTaskExecutor {

        @Override
        public void execute(Runnable task) {
            super.execute(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));
        }

        @Override
        public <T> Future<T> submit(Callable<T> task) {
            return super.submit(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));
        }

        @Override
        public Future<?> submit(Runnable task) {
            return super.submit(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));
        }

        @Override
        public ListenableFuture<?> submitListenable(Runnable task) {
            return super.submitListenable(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));
        }

        @Override
        public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
            return super.submitListenable(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));
        }
    }

    /**
     * 普通线程池的封装
     */
    public static class ThreadPoolExecutorMdcWrapper extends ThreadPoolExecutor {

        public ThreadPoolExecutorMdcWrapper(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
            BlockingQueue<Runnable> workQueue) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        }

        public ThreadPoolExecutorMdcWrapper(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
            BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
        }

        public ThreadPoolExecutorMdcWrapper(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
            BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
        }

        public ThreadPoolExecutorMdcWrapper(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
            BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        }

        @Override
        public void execute(Runnable task) {
            super.execute(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));
        }

        @Override
        public <T> Future<T> submit(Runnable task, T result) {
            return super.submit(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()), result);
        }

        @Override
        public <T> Future<T> submit(Callable<T> task) {
            return super.submit(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));
        }

        @Override
        public Future<?> submit(Runnable task) {
            return super.submit(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));
        }
    }

    public static class ForkJoinPoolMdcWrapper extends ForkJoinPool {

        public ForkJoinPoolMdcWrapper() {
            super();
        }

        public ForkJoinPoolMdcWrapper(int parallelism) {
            super(parallelism);
        }

        public ForkJoinPoolMdcWrapper(int parallelism, ForkJoinWorkerThreadFactory factory,
            Thread.UncaughtExceptionHandler handler, boolean asyncMode) {
            super(parallelism, factory, handler, asyncMode);
        }

        @Override
        public void execute(Runnable task) {
            super.execute(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));
        }

        @Override
        public <T> ForkJoinTask<T> submit(Runnable task, T result) {
            return super.submit(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()), result);
        }

        @Override
        public <T> ForkJoinTask<T> submit(Callable<T> task) {
            return super.submit(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));
        }
    }

    /**
     * 定时调度线程池的封装
     */
    public static class ScheduledThreadPoolExecutorMdcWrapper extends ScheduledThreadPoolExecutor {

        public ScheduledThreadPoolExecutorMdcWrapper(int corePoolSize) {
            super(corePoolSize);
        }

        @Override
        public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
            return super.schedule(ThreadMdcUtil.wrap(command, MDC.getCopyOfContextMap()), delay, unit);
        }

        @Override
        public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
            return super.schedule(ThreadMdcUtil.wrap(callable, MDC.getCopyOfContextMap()), delay, unit);
        }

        @Override
        public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period,
            TimeUnit unit) {
            return super.scheduleAtFixedRate(ThreadMdcUtil.wrap(command, MDC.getCopyOfContextMap()), initialDelay,
                period, unit);
        }

        @Override
        public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay,
            TimeUnit unit) {
            return super.scheduleWithFixedDelay(ThreadMdcUtil.wrap(command, MDC.getCopyOfContextMap()), initialDelay,
                delay, unit);
        }

        @Override
        public void execute(Runnable command) {
            super.execute(ThreadMdcUtil.wrap(command, MDC.getCopyOfContextMap()));
        }

        @Override
        public Future<?> submit(Runnable task) {
            return super.submit(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));
        }

        @Override
        public <T> Future<T> submit(Runnable task, T result) {
            return super.submit(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()), result);
        }

        @Override
        public <T> Future<T> submit(Callable<T> task) {
            return super.submit(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));
        }
    }

}
