package com.zouyu.designmode.bridge;

/**
 * Created by zouy-c on 2017/12/18.
 */
public class UrgencyMessage extends AbstractMessage {
    public UrgencyMessage(MessageImplementor impl) {
        super(impl);
    }

    @Override
    public void sendMessage(String message,String toUser){
        message += "加急消息";
        super.sendMessage(message,toUser);
    }

    public Object watch(String messageId){
        //根据消息id获取消息的状态，组织实时监控成数据对象，然后返回
        return null;
    }
}
