package com.zouyu.designmode.bridge;

/**
 * Created by zouy-c on 2017/12/18.
 *
 * 业务抽象角色引用业务实现角色
 */
public abstract class AbstractMessage {
    //抽象消息持有一个实现部分的对象
    MessageImplementor impl;

    public AbstractMessage(MessageImplementor impl){
        this.impl = impl;
    }

    //发送消息委派给实现部分
    public void sendMessage(String message,String toUser){
        this.impl.send(message,toUser);
    }
}
