package com.paymybuddy.PayMyBuddyWeb.Utils;

import org.springframework.util.StringUtils;

public class MSStringUtils {
    /**
     * Check if String is null and empty
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return (str != null) && StringUtils.isEmpty(str);
    }

    /**
     * Change first string letter to uppercase
     * @param str
     * @return
     */
    public static String firstUpperCase(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
