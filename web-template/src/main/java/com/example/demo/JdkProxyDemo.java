package com.example.demo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author WuQinglong
 * @since 2023/2/14 15:06
 */
public class JdkProxyDemo {

    public static void main(String[] args) {
        UserServiceProxy proxy = new UserServiceProxy();
        IUserService userService = (IUserService) proxy.createProxy(new UserService());
        userService.sayName("张三");

    }

    public interface IUserService {
        void sayName(String name);
    }

    public static class UserService implements IUserService {

        @Override
        public void sayName(String name) {
            System.out.println("My name is " + name + ".");
        }

    }

    public static class UserServiceProxy {

        public Object createProxy(Object proxiedObject) {
            DynamicProxyHandler handler = new DynamicProxyHandler(proxiedObject);
            return Proxy.newProxyInstance(
                proxiedObject.getClass().getClassLoader(),
                proxiedObject.getClass().getInterfaces(),
                handler);
        }

        public static class DynamicProxyHandler implements InvocationHandler {

            private final Object proxiedObject;

            public DynamicProxyHandler(Object proxiedObject) {
                this.proxiedObject = proxiedObject;
            }

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("before say ...");
                Object result = null;
                try {
                    result = method.invoke(proxiedObject, args);
                } catch (Exception e) {
                    System.out.println("exception on say");
                    throw new RuntimeException(e);
                }
                System.out.println("after say ...");
                return result;
            }

        }

    }

}
