package com.zouyu.designmode.facade;

/**
 * Created by zouy-c on 2017/12/15.
 */
public class LetterProcessImpl implements LetterProcess {
    public void writerContext(String context) {
        System.out.println("填写信的内容:"+context);
    }

    public void fillEnvelope(String address) {
        System.out.println("收件人地址及姓名:"+address);
    }

    public void letterIntoEnvelope() {
        System.out.println("把信放到信封中...");
    }

    public void sendLetter() {
        System.out.println("邮递送信");
    }
}
