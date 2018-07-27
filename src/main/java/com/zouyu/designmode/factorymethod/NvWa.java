package com.zouyu.designmode.factorymethod;

/**
 * Created by zouy-c on 2017/12/15.
 */
public class NvWa {

    public static void main(String[] args) {
        Human whiteman = HumanFactory.createHuman(WhiteHuman.class);
        whiteman.laugh();

        Human yellowman = HumanFactory.createHuman(YellowHuman.class);
        yellowman.cry();

        Human blackman = HumanFactory.createHuman(BlackHuman.class);
        blackman.cry();

        for(int i= 0;i<10;i++){
            System.out.println("第"+(i+1)+"个人：");
            Human human = HumanFactory.createHuman();
            human.cry();
            human.laugh();
            human.talk();
        }
    }
}
