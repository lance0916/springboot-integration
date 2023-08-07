package com.rabbit.jmh;

import java.util.concurrent.TimeUnit;
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
        Options opt = new OptionsBuilder()
            .include(MxBeanDemo.class.getSimpleName())
//            .resultFormat(ResultFormatType.JSON)
            .build();
        new Runner(opt).run();
    }

    @Benchmark
    public void plus() {
        for (int i = 0; i < 1000; i++) {
            String s = "abc";
            s += "def";
            s += "xyz";
            s += "000";
            s += "555";
        }
    }

    @Benchmark
    public void builder() {
        for (int i = 0; i < 1000; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append("abc");
            sb.append("def");
            sb.append("xyz");
            sb.append("000");
            sb.append("555");
        }
    }

}
