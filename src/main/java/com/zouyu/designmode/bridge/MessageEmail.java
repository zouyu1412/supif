package com.zouyu.designmode.bridge;

/**
 * Created by zouy-c on 2017/12/18.
 */
public class MessageEmail implements MessageImplementor {
    public void send(String message, String toUser) {
        System.out.println("使用邮件短消息的方法发送消息："+message+"给："+toUser);
    }
}
