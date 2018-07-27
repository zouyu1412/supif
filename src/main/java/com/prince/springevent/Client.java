package com.prince.springevent;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Created by zouy-c on 2018/1/2.
 */
public class Client {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Client.class, args);
        //把监听器加入到SpringApplication中
        context.addApplicationListener(new MyApplicationListener());
        //发布事件
        context.publishEvent(new MyApplicationEvent(new Object()));
        context.close();
    }

}
