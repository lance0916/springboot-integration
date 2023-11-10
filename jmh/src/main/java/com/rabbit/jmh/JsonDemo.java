package com.rabbit.jmh;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import cn.hutool.core.date.DatePattern;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.beans.BeanUtils;

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
public class JsonDemo {

    private ObjectMapper objectMapper = new ObjectMapper()
        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        .setSerializationInclusion(JsonInclude.Include.NON_NULL)
        .setDateFormat(new SimpleDateFormat(DatePattern.NORM_DATETIME_PATTERN));

    private Gson gson = new Gson();

    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
            .include(JsonDemo.class.getSimpleName())
            .build();
        new Runner(opt).run();
    }

    @Benchmark
    public Object hutool() {
        UserDTO userDTO = create();
        String s = JSONUtil.toJsonStr(userDTO);
        return JSONUtil.toBean(s, UserDTO.class);
    }

    @Benchmark
    public Object beanUtil() throws Exception {
        UserDTO userDTO = create();
        UserDTO ret = new UserDTO();
        BeanUtils.copyProperties(userDTO, ret);
        return ret;
    }

    @Benchmark
    public Object jackson() throws Exception {
        UserDTO userDTO = create();

        String s = objectMapper.writeValueAsString(userDTO);
        return objectMapper.readValue(s, UserDTO.class);
    }

    @Benchmark
    public Object gson() throws Exception {
        UserDTO userDTO = create();
        String s = gson.toJson(userDTO);
        return gson.fromJson(s, UserDTO.class);
    }

    @Benchmark
    public Object cloneFun() throws Exception {
        UserDTO userDTO = create();
        return userDTO.clone();
    }

    @Benchmark
    public Object mapstruct() throws Exception {
        UserDTO userDTO = create();
        return UserMapper.INSTANCE.copy(userDTO);
    }

    private UserDTO create() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName1("name1");
        userDTO.setName2("name2");
        userDTO.setName3("name3");
        userDTO.setName4("name4");
        userDTO.setName5("name5");
        userDTO.setName6("name6");
        userDTO.setName7("name7");
        userDTO.setName8("name8");
        userDTO.setName9("name9");
        userDTO.setName11("name11");
        userDTO.setName12("name12");
        userDTO.setName13("name13");
        userDTO.setName14("name14");
        userDTO.setName15("name15");
        userDTO.setName16("name16");
        userDTO.setName17("name17");
        userDTO.setName18("name18");
        userDTO.setName19("name19");
        userDTO.setName20("name20");
        return userDTO;
    }
}
