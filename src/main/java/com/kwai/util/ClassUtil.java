package com.kwai.util;

import org.apache.lucene.util.RamUsageEstimator;

/**
 * @author zouyu <zouyu@kuaishou.com>
 * Created on 2020-06-23
 */
public class ClassUtil {

    public static long getObjectSize(Object o) {
        return RamUsageEstimator.shallowSizeOf(o);
    }

}
