package com.imooc.permission.util;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;

/**
 * @author Lesleey
 * @date 2021/5/3-22:48
 * @function
 */
public class LevelUtil {
    public final static String SEPARATOR = ".";

    public static final String ROOT = "-1";

    public static String calculateLevel(String parentLevel, Integer parentid){
        if(StringUtils.isEmpty(parentLevel))
            return ROOT;
        return StringUtils.join(Arrays.asList(parentLevel, parentid), SEPARATOR);
    }
}
