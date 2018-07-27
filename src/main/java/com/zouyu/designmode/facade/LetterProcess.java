package com.zouyu.designmode.facade;

/**
 * Created by zouy-c on 2017/12/15.
 */
public interface LetterProcess {
    void writerContext(String context);
    void fillEnvelope(String address);
    void letterIntoEnvelope();
    void sendLetter();
}
