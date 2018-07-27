package com.prince.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by zouy-c on 2018/3/7.
 */
public class ConcurrentTest {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> queue = new ArrayBlockingQueue<String>(20);
        for(int i=0;i<30;i++){
            queue.put("第"+i+"个元素");
            System.out.println("添加当前元素"+i);
            if(i>18){
                System.out.println("取出元素--"+queue.take());
            }
        }
        System.out.println("===========================================");

        BlockingDeque<String> deque = new LinkedBlockingDeque<>(20);
        for(int i=0;i<30;i++){
            deque.putFirst(""+i);
            System.out.println("向阻塞栈中加入了元素"+i);
            if(i>18){
                System.out.println("从阻塞栈中移除元素:"+deque.pollFirst());
            }
        }
    }
}
