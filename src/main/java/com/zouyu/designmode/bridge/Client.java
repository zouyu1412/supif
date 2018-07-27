package com.zouyu.designmode.bridge;

/**
 * Created by zouy-c on 2017/12/18.
 */
public class Client {
    public static void main(String[] args) {
        //创建具体的实现对象
        MessageImplementor messageImplementor = new MessageSMS();
        //创建普通消息对象
        AbstractMessage message = new CommonMessage(messageImplementor);

        message.sendMessage("加班申请","邹总");

        messageImplementor = new MessageEmail();

        message = new UrgencyMessage(messageImplementor);

        message.sendMessage("请假申请","煜总");
    }
}
