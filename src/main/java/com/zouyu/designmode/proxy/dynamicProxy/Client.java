package com.zouyu.designmode.proxy.dynamicProxy;

import com.zouyu.designmode.proxy.RealSubject;
import com.zouyu.designmode.proxy.Subject;

import java.lang.reflect.Proxy;

/**
 * Created by zouy-c on 2017/12/15.
 */
public class Client {
    public static void main(String[] args) {
        Subject realSubject = new RealSubject();
        DynamicProxy proxy = new DynamicProxy(realSubject);
        ClassLoader classLoader = realSubject.getClass().getClassLoader();
        Subject subject = (Subject) Proxy.newProxyInstance(classLoader, new  Class[]{Subject.class}, proxy);
        subject.visit();
//       Proxy.newProxyInstance三个参数：
//        ClassLoader loader表示当前使用到的appClassloader。
//        Class<?>[] interfaces表示目标对象实现的一组接口。
//        InvocationHandler h表示当前的InvocationHandler实现实例对象。

    }
}
