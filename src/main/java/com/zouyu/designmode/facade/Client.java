package com.zouyu.designmode.facade;

/**
 * Created by zouy-c on 2017/12/15.
 */
public class Client {
    public static void main(String[] args) {
        ModenPostOffice modenPostOffice = new ModenPostOffice();

        String address = "Happy Road No.666";
        String context = "hello xuan small";

        modenPostOffice.sendLetter(context,address);
    }
}
