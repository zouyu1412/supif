package com.JVM;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by zouy-c on 2018/4/20.
 */
public class JavaMethodAreaOOM {
    public static void main(String[] args) {
        while(true){
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(OOMObject.class);
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {
                @Override
                public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                    return methodProxy.invokeSuper(o,objects);
                }
            });
            enhancer.create();
        }
    }

    static class OOMObject{

    }
}
//   -XX:MetaspaceSize=10M -XX:MaxMetaspaceSize=10M

//       Caused by: java.lang.OutOfMemoryError: Metaspace
//       at java.lang.ClassLoader.defineClass1(Native Method)
//       at java.lang.ClassLoader.defineClass(ClassLoader.java:763)


