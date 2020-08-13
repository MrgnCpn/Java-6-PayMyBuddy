package com.paymybuddy.PayMyBuddyWeb.Utils;

import org.springframework.util.StringUtils;

public class MSStringUtils {
    public static boolean isEmpty(String str) {
        return (str != null) && StringUtils.isEmpty(str);
    }
}
