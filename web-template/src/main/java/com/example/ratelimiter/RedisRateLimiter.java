package com.example.ratelimiter;

import com.google.common.annotations.VisibleForTesting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.util.Assert;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author WuQinglong
 */
@Slf4j
public class RedisRateLimiter {

    private static final String luaScript;

    static {
        luaScript = "local max=tonumber(ARGV[1]) " + // 单位时间内的最大令牌数
                "local interval=tonumber(ARGV[2]) " + // 生成一个令牌的间隔毫秒数
                "local align=tonumber(ARGV[3]) " + // 当前时间戳（对齐之后的时间戳）
                "align=align-(align%interval) " + // 对齐当前时间戳
                "local latest=redis.call('HGET',KEYS[1],'latest') " + // 获取最后一次成功获取令牌的时间戳
                "if latest then " +
                "   local remain=tonumber(redis.call('HGET',KEYS[1],'remain')) " +
                "   local diff=align-tonumber(latest) " +
                "   if diff>1000 then " +
                "       remain=1 " +
                "   elseif diff>=interval then " +
                "       remain=math.min((math.floor(diff/interval)+remain),max) " +
                "   end " +
                "   if remain<=0 then " +
                "       return 0 " +
                "   else " +
                "       redis.call('HSET',KEYS[1],'remain',remain-1) " +
                "       redis.call('HSET',KEYS[1],'latest',align) " +
                "       return 1 " +
                "   end " +
                "else " + // 如果首次获取，直接返回成功，并设置时间为对齐时间
                "    redis.call('HSET',KEYS[1],'remain',0) " +
                "    redis.call('HSET',KEYS[1],'latest',align) " +
                "    return 1 " +
                "end";
    }

    private final String key;
    private final String maxToken;
    private final String intervalMillis;
    private final RedisConnectionFactory connectionFactory;

    public RedisRateLimiter(String key, int maxToken, RedisConnectionFactory redisConnectionFactory) {
        this(key, maxToken, 1000 / maxToken, redisConnectionFactory);
    }

    public RedisRateLimiter(String key, int maxToken, int intervalMillis,
                            RedisConnectionFactory redisConnectionFactory) {
        Assert.isTrue(maxToken > 0, "令牌桶数要大于0");
        this.key = key;
        this.maxToken = String.valueOf(maxToken);
        this.intervalMillis = String.valueOf(intervalMillis);
        this.connectionFactory = redisConnectionFactory;
        redisConnectionFactory.getConnection().del(key.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 尝试获取令牌
     *
     * @return true:获取成功；false:获取失败
     */
    public boolean tryAcquire() {
        return tryAcquire(System.currentTimeMillis());
    }

    @VisibleForTesting
    protected boolean tryAcquire(long millis) {
        List<String> keysList = Collections.singletonList(key);
        List<String> argsList = Arrays.asList(maxToken, intervalMillis, millis + "");
        try (RedisConnection connection = connectionFactory.getConnection()) {
            byte[][] keysAndArgs = getKeyArgs(keysList, argsList);
            Boolean ret = connection.eval(luaScript.getBytes(StandardCharsets.UTF_8), ReturnType.BOOLEAN,
                    keysList.size(), keysAndArgs);
            return Boolean.TRUE.equals(ret);
        } catch (Exception e) {
            log.error("获取限流token异常", e);
        }
        return false;
    }

    private static byte[][] getKeyArgs(List<String> keyList, List<String> argList) {
        byte[][] keyArgs = new byte[keyList.size() + argList.size()][];
        int i = 0;
        for (String key : keyList) {
            keyArgs[i++] = key.getBytes(StandardCharsets.UTF_8);
        }
        for (String arg : argList) {
            keyArgs[i++] = arg.getBytes(StandardCharsets.UTF_8);
        }
        return keyArgs;
    }

}
