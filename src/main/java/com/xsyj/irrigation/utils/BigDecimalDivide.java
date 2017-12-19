package com.xsyj.irrigation.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by Administrator on 2016/9/12.
 */
public class BigDecimalDivide {
    static DecimalFormat df = new DecimalFormat("#.00");
    /**
     * 除法 ,四舍五入，保留两位小数
     * @param devided  被除数
     * @param devider  除数
     * @param scale 保留N位小数
     *@param round_model 保留小数模式： 四舍五入，直接去掉，向上取整
     */
    public static double devide(Double devided,Double devider,int scale,int round_model){
        return new BigDecimal(devided).divide(new BigDecimal(devider), scale,round_model).doubleValue();
    }

    public static String dateFun(double devided){
        double time;
        if (devided < 60){
            return (int)devided + "分钟";
        }else if (devided > 60 && devided < 1440){
            time = Double.valueOf(df.format(devided / 60));
            return time + "小时";
        }else{
            time = Double.valueOf(df.format(devided / 1440));
            return time + "天" ;
        }
    }

    public static double format(double d){
         return Double.valueOf(df.format(d));
    }
}
