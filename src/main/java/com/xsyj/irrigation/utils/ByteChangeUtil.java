package com.xsyj.irrigation.utils;

import java.text.DecimalFormat;

/**
 * Created by NewWater on 2015/12/11.
 */
public class ByteChangeUtil {
    public static String change(String size) {
        DecimalFormat format = new DecimalFormat("0.00");
        long sizeLong;
        try {
            sizeLong = Long.valueOf(size);
        } catch (Exception e) {
            e.printStackTrace();
            return "0 B";
        }

        if (sizeLong < 1024) {
            return String.valueOf(sizeLong).concat(" B");
        } else if (sizeLong < (1024 * 1024)) {
            return format.format(sizeLong / 1024d).concat(" KB");
        } else if (sizeLong < (1024 * 1024 * 1024)) {
            return format.format(sizeLong / (1024d * 1024)).concat(" MB");
        } else if (sizeLong < (1024 * 1024 * 1024 * 1024)) {
            return format.format(sizeLong / (1024d * 1024 * 1024)).concat(" G");
        } else {
            return "0 B";
        }
    }
}
