package com.JVM;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zouy-c on 2018/4/16.
 */
public class HeapOOM {
    static class OOMObject{}

    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<>();
        while(true){
            list.add(new OOMObject());
        }
    }
}

//       JVM args:     -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError

//        java.lang.OutOfMemoryError: Java heap space
//        Dumping heap to java_pid9436.hprof ...
//        Heap dump file created [28469488 bytes in 0.070 secs]