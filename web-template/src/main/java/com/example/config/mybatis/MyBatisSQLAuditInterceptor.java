package com.example.config.mybatis;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

/**
 * @author WuQinglong
 */
@SuppressWarnings("rawtypes")
@Intercepts({
    @Signature(type = Executor.class, method = MyBatisSQLAuditInterceptor.QUERY_METHOD_NAME,
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
    ),
    @Signature(type = Executor.class, method = MyBatisSQLAuditInterceptor.QUERY_METHOD_NAME,
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class,
            CacheKey.class, BoundSql.class}),
    @Signature(type = Executor.class, method = MyBatisSQLAuditInterceptor.UPDATE_METHOD_NAME,
        args = {MappedStatement.class, Object.class}),
})
public class MyBatisSQLAuditInterceptor implements Interceptor {

    private static final Logger logger = LoggerFactory.getLogger(MyBatisSQLAuditInterceptor.class);

    public static final String QUERY_METHOD_NAME = "query";
    public static final String UPDATE_METHOD_NAME = "update";
    public static final String NORMAL_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 被代理对象
        Executor executor = (Executor) invocation.getTarget();
        // 代理方法
        Method method = invocation.getMethod();
        // 方法参数
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object parameter = args[1];

        Object ret;
        String realSql;
        long startTime = System.currentTimeMillis();
        StopWatch stopWatch = new StopWatch();
        if (UPDATE_METHOD_NAME.equals(method.getName())) {
            BoundSql boundSql = ms.getBoundSql(parameter);
            realSql = fillParam(boundSql, ms, stopWatch);
            ret = executor.update(ms, parameter);
        } else {
            // 获取方法的每个参数
            RowBounds rowBounds = (RowBounds) args[2];
            ResultHandler resultHandler = (ResultHandler) args[3];
            CacheKey cacheKey;
            BoundSql boundSql;
            if (args.length == 4) {
                boundSql = ms.getBoundSql(parameter);
                cacheKey = executor.createCacheKey(ms, parameter, rowBounds, boundSql);
            } else {
                cacheKey = (CacheKey) args[4];
                boundSql = (BoundSql) args[5];
            }
            realSql = fillParam(boundSql, ms, stopWatch);
            ret = executor.query(ms, parameter, rowBounds, resultHandler, cacheKey, boundSql);
        }
        long endTime = System.currentTimeMillis();
        long fillParamTime = stopWatch.getTotalTimeMillis();
        long execSqlTime = endTime - startTime - fillParamTime;

        logger.info("[SqlAuditLog]times:{}ms,{}ms,sql:{}", execSqlTime, fillParamTime, realSql);
        return ret;
    }

    /**
     * 填充参数
     */
    private String fillParam(BoundSql boundSql, MappedStatement ms, StopWatch stopWatch) {
        stopWatch.start();
        // 去掉换行和多于的空格
        String sql = boundSql.getSql();
        try {
            sql = sql.replaceAll("[\\s\n ]+", " ");

            Configuration configuration = ms.getConfiguration();
            Object parameter = boundSql.getParameterObject();
            MetaObject metaObject = configuration.newMetaObject(parameter);

            List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
            for (ParameterMapping parameterMapping : parameterMappings) {
                if (parameterMapping.getMode() == ParameterMode.OUT) {
                    continue;
                }
                String propertyName = parameterMapping.getProperty();
                if (isPrimitiveOrPrimitiveWrapper(parameter.getClass())) {
                    // parameter 是基本数据类型，直接替换
                    sql = sql.replaceFirst("\\?", convertParamToString(parameter));
                } else if (boundSql.hasAdditionalParameter(propertyName)) {
                    // 动态SQL
                    Object val = boundSql.getAdditionalParameter(propertyName);
                    sql = sql.replaceFirst("\\?", convertParamToString(val));
                } else if (metaObject.hasGetter(propertyName)) {
                    // 对象参数
                    Object val = metaObject.getValue(propertyName);
                    sql = sql.replaceFirst("\\?", convertParamToString(val));
                } else {
                    sql = sql.replaceFirst("\\?", "'!unknown!'");
                    logger.warn("未知的参数类型，填充为!unknown!");
                }
            }
        } catch (Exception e) {
            logger.error("填充SQL参数异常, errMsg:{}", e.getMessage());
        }
        stopWatch.stop();
        return sql;
    }

    /**
     * 转换参数为String类型的值，便于打印
     */
    private static String convertParamToString(Object param) {
        if (param instanceof String) {
            return "'" + param + "'";
        } else if (param instanceof Date) {
            SimpleDateFormat format = new SimpleDateFormat(NORMAL_DATE_TIME_PATTERN);
            return "'" + format.format((Date) param) + "'";
        } else {
            return param.toString();
        }
    }

    /**
     * 是否是基本数据类型
     */
    private static boolean isPrimitiveOrPrimitiveWrapper(Class<?> clazz) {
        return clazz.isPrimitive() ||
            clazz.isAssignableFrom(Byte.class) || clazz.isAssignableFrom(Short.class) ||
            clazz.isAssignableFrom(Integer.class) || clazz.isAssignableFrom(Long.class) ||
            clazz.isAssignableFrom(Double.class) || clazz.isAssignableFrom(Float.class) ||
            clazz.isAssignableFrom(Character.class) || clazz.isAssignableFrom(Boolean.class);
    }

}
