package com.JVM;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zouy-c on 2018/4/16.
 */
public class RuntimeConstantPoolOOM {
    public static void main(String[] args) {
        //使用List保持着常量池的引用，避免Full GC回收常量池的行为
        List<String> list = new ArrayList<>();
        //10MB的PerSize在Integer范围内足够产生OOM
        int i=0;
        while(true){
            list.add(String.valueOf(i++).intern());
        }
    }
}

//  jvm Args:   -XX:MetaspaceSize=10M -XX:MaxMetaspaceSize=10M
// 1.6以前可以抛出异常 1.7以后一直循环下去
