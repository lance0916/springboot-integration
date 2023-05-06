package com.rabbit.jmh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author WuQinglong
 * @since 2023/2/28 19:20
 */
@BenchmarkMode(Mode.AverageTime)
@State(Scope.Benchmark)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 3, time = 1)
@Threads(3)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class MxBeanDemo {

    public static void main(String[] args) throws Exception {
//        Options opt = new OptionsBuilder()
//                .include(MxBeanDemo.class.getSimpleName())
//                .result("result.json")
//                .resultFormat(ResultFormatType.JSON)
//                .build();
//        new Runner(opt).run();
    }

    @Benchmark
    public void f() {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        Map<Thread.State, Integer> map = new HashMap<>();

        long[] allThreadIds = threadMXBean.getAllThreadIds();
        for (long threadId : allThreadIds) {
            threadMXBean.findDeadlockedThreads();
            ThreadInfo threadInfo = threadMXBean.getThreadInfo(threadId);
            Thread.State threadState = threadInfo.getThreadState();
            Integer count = map.getOrDefault(threadState, 0);
            map.put(threadState, count + 1);
        }
    }

}
