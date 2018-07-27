package com.zouyu.hystrix.hystrixCommand;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

import java.util.concurrent.TimeUnit;

/**
 * Created by zouy-c on 2017/12/26.
 */
//重载HystrixCommand 的getFallback方法实现逻辑
public class FallBackCommand extends HystrixCommand<String> {
    private final String name;
    public FallBackCommand(String name) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("JavaGroup"))
                    /* 配置依赖超时时间,500毫秒*/
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(500)));
        this.name = name;
    }
    @Override
    protected String getFallback() {
        return "exeucute Falled";
    }
    @Override
    protected String run() throws Exception {
        //sleep 1 秒,调用会超时
        TimeUnit.MILLISECONDS.sleep(3000);
        return "Hello " + name +" thread:" + Thread.currentThread().getName();
    }
    public static void main(String[] args) throws Exception{
        FallBackCommand command = new FallBackCommand("test-Fallback");
        String result = command.execute();
        System.out.println(result);
    }
}
/* 运行结果:getFallback() 调用运行
 * getFallback executed
 */
