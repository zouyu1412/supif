package com.kwai.test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zouyu <zouyu@kuaishou.com>
 * Created on 2020-06-22
 */
public class ForEachTest {

    static class ItemC{
        int x;
        int y;

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        @Override
        public String toString() {
            return "ItemC{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    public static void main(String[] args) {
        List<ItemC> list = new ArrayList<>();
        int testCount = 100000000;
        for(int i=0;i<testCount;i++){
            ItemC tem = new ItemC();
            tem.setX(i);
            tem.setY(i);
            list.add(tem);
        }

        long t1 = System.currentTimeMillis();
        for(ItemC itemC:list){
            if(itemC.getX()%2==0){
                continue;
            }
            int cur = itemC.getX()+itemC.getY();
        }
        long t2 = System.currentTimeMillis();
        System.out.println("originway for cost time:["+(t2-t1)+"]ms with test time:"+testCount);

        long t3 = System.currentTimeMillis();
        list.stream().filter(o->o.getX()%2==0).forEach(itemC->{
            int cur = itemC.getX()+itemC.getY();
        });
        long t4 = System.currentTimeMillis();
        System.out.println("fluent stream cost time:["+(t4-t3)+"]ms with test time:"+testCount);
    }




}
