package com.zouyu.designmode.bridge;

/**
 * 系统内短消息的实现类
 * Created by zouy-c on 2017/12/18.
 */
public class MessageSMS implements MessageImplementor {
    public void send(String message, String toUser) {
        System.out.println("使用系统内的短消息的方法发送消息："+message+"给："+toUser);
    }
}
