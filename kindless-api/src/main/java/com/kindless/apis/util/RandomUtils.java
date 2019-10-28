package com.kindless.apis.util;

/**
 * @author wuxii
 */
public class RandomUtils {

    public static Long randomId() {
        return org.apache.commons.lang3.RandomUtils.nextLong(100000, 999999);
    }

}
