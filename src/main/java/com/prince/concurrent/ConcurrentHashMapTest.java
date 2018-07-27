package com.prince.concurrent;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zouy-c on 2018/3/8.
 */
public class ConcurrentHashMapTest {

    public static void main(String[] args) {

        ConcurrentHashMap<Integer,String> map = new ConcurrentHashMap();
        map.put(1,"ss");
        map.put(2,"dd");
        map.put(1,"zouyu");
        map.putIfAbsent(2,"zouyu");
        map.putIfAbsent(3,"zouyu");
        map.keySet().forEach(o-> System.out.println(map.get(o)));
    }
}
