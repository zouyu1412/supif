package com.zouyu.designmode.bridge;

/**
 * 普通消息类
 * Created by zouy-c on 2017/12/18.
 */
public class CommonMessage extends AbstractMessage {
    public CommonMessage(MessageImplementor impl) {
        super(impl);
    }

    @Override
    public void sendMessage(String message,String toUser){
        super.sendMessage(message,toUser);
    }
}
