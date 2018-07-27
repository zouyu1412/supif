package com.zouyu.hystrix.hystrixCommand;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixThreadPoolKey;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by zouy-c on 2017/12/26.
 */
public class HelloWorldCommand extends HystrixCommand<String>{
    private final String name;

    public HelloWorldCommand(String name) {
        //最少配置 指定组名  默认线程池按组名分组
//        super(HystrixCommandGroupKey.Factory.asKey("zouyuGroup"));

        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
                /* HystrixCommandKey工厂定义依赖名称 */
                .andCommandKey(HystrixCommandKey.Factory.asKey("HelloWorld"))
                //CommandKey代表一个依赖抽象,相同的依赖要使用相同的CommandKey名称。依赖隔离的根本就是对相同CommandKey的依赖做隔离
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("HelloWorldPool")));
                // 当对同一业务依赖做隔离时使用CommandGroup做区分,但是对同一依赖的不同远程调用
                // 如(一个是redis 一个是http),可以使用HystrixThreadPoolKey做隔离区分.
                //虽然在业务上都是相同的组，但是需要在资源上做隔离时，可以使用HystrixThreadPoolKey区分.

        this.name = name;
    }

    protected String run() throws Exception {
         return "Hello "+name+"thread:"+Thread.currentThread().getName();
    }

    public static void main(String[] args) throws Exception{
        //每个Command对象只能调用一次,不可以重复调用,
        //重复调用对应异常信息:This instance can only be executed once. Please instantiate a new instance.
        HelloWorldCommand helloWorldCommand = new HelloWorldCommand("Synchronous-hystrix");
        //使用execute()同步调用代码,效果等同于:helloWorldCommand.queue().get();
        String result = helloWorldCommand.execute();
        System.out.println("result=" + result);


        //异步调用使用 command.queue().get(timeout, TimeUnit.MILLISECONDS);
        //同步调用使用 command.execute() 等同于 command.queue().get();
        helloWorldCommand = new HelloWorldCommand("Asynchronous-hystrix");
        //异步调用,可自由控制获取结果时机,
        Future<String> future = helloWorldCommand.queue();
        //get操作不能超过command定义的超时时间,默认:1秒
        result = future.get(100, TimeUnit.MILLISECONDS);
        System.out.println("result=" + result);
        System.out.println("mainThread=" + Thread.currentThread().getName());
    }
}
