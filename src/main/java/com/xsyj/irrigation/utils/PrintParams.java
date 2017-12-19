package com.xsyj.irrigation.utils;



import com.xsyj.irrigation.application.MyApplication;

import org.xutils.http.RequestParams;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2016/4/28.
 */
public class PrintParams {

    public static void  printParam(RequestParams params){


        try {
            if(params!=null && !MyApplication.isRelease) {
                HashMap<String, String> hm = params.getBodyParams();
                Set s = hm.entrySet();
                Iterator i = s.iterator();
                while (i.hasNext()) {
                    Map.Entry o = (Map.Entry) i.next();
                    System.out.println("params:"+o.getKey() + " -- " + o.getValue());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
