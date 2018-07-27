package com.prince.springevent;

import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;

/**
 * Created by zouy-c on 2018/1/2.
 */
public class MyApplicationListener implements ApplicationListener<MyApplicationEvent> {
    //异步
    @Async
    public void onApplicationEvent(MyApplicationEvent event) {
        System.out.println("捕获事件，等待异步处理");
    }
}
