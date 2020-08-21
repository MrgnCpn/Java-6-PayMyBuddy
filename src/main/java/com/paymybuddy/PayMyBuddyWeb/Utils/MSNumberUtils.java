package com.paymybuddy.PayMyBuddyWeb.Utils;

import java.text.DecimalFormat;

public class MSNumberUtils {

    /**
     * Return casted Double with 2 digits
     * @param value
     * @return
     */
    public static Double getDouble_2_digits(Double value){
        return Double.valueOf(new DecimalFormat("0.00").format(value));
    }
}
