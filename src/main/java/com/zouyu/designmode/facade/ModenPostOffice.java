package com.zouyu.designmode.facade;

/**
 * Created by zouy-c on 2017/12/15.
 */
public class ModenPostOffice {
    private LetterProcess letterProcess = new LetterProcessImpl();

    public void sendLetter(String context,String address){
        letterProcess.writerContext(context);

        letterProcess.fillEnvelope(address);

        letterProcess.letterIntoEnvelope();

        letterProcess.sendLetter();
    }
}
