package com.paymybuddy.PayMyBuddyWeb.Utils;

import java.io.File;

public class MSFileUtils {

    /**
     * Check is file exist
     * @param relativePath
     * @return
     */
    public static String isFileExist(String relativePath, String fileType){
        String extention = null;
        if (!MSStringUtils.isEmpty(relativePath)) {
            if (fileType.equals("img")) {
                if (new File(relativePath + ".png").exists()) extention = ".png";
                if (new File(relativePath + ".jpg").exists()) extention = ".jpg";
            } else if (new File(relativePath).exists()) {
                extention = "unknown";
            }
        }
        return extention;
    }
}