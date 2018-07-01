package com.minimall.boilerplate.common;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * money format
 */
public class Utils {
    public final static String moneyFormat = "###,###,##0.00";

    public static String formatMoney(Double money,String strFor){
        if(CheckUtils.isEmpty(money) || money == 0)
            return "0.00";
         NumberFormat nf = new DecimalFormat(strFor);
         String str = nf.format(money);
         return str;
    }
}
