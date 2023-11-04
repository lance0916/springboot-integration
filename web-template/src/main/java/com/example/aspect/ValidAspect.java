package com.example.aspect;

import java.lang.reflect.Method;

import com.example.aspect.annotation.NoValid;
import com.example.aspect.annotation.ValidLogin;
import com.example.aspect.annotation.ValidOpenApi;
import com.example.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * 拦截所有的 controller 进行鉴权
 *
 * @author WuQinglong
 * @since 2022/11/24 18:40
 */
@Slf4j
//@Aspect
//@Component
public class ValidAspect {

    /**
     * token校验
     */
    @Around("execution(* com.example.controller.*.*(..))")
    public Object valid(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Class<?> clazz = methodSignature.getDeclaringType();
        Method method = methodSignature.getMethod();

        // 不需要鉴权
        if (clazz.isAnnotationPresent(NoValid.class) || method.isAnnotationPresent(NoValid.class)) {
            return joinPoint.proceed();
        }

        // 登录鉴权
        if (clazz.isAnnotationPresent(ValidLogin.class) || method.isAnnotationPresent(ValidLogin.class)) {
            JwtUtil.validToken();
            return joinPoint.proceed();
        }

        // TODO OpenApi 的校验方式实现
        if (clazz.isAnnotationPresent(ValidOpenApi.class) || method.isAnnotationPresent(ValidOpenApi.class)) {
            System.out.println("TODO");
        }

        throw new RuntimeException("未标注明确的验签方式");
    }

}
