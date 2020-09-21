package com.paymybuddy.paymybuddyweb.utils;

import java.text.DecimalFormat;

/**
 * @author MorganCpn
 */
public class MSNumberUtils {

    private MSNumberUtils(){}

    /**
     * Return casted Double with 2 digits
     * @param value
     * @return
     */
    public static Double getDoubleTwoDigits(Double value){
        return Double.valueOf(new DecimalFormat("0.00").format(value));
    }
}
