package com.utils;

import com.pojo.User;
import org.apache.commons.beanutils.BeanUtils;


import java.util.Map;

/**
 * @outhor Mr.JK
 * @create 2020-04-05  19:57
 */
public class WebUtils {
    public static <T> T  copyParmToBean(Map values, T bean){
        try {

            BeanUtils.populate(bean,values);

        }catch (Exception e){
            e.printStackTrace();
        }
        return bean;
    }

    /**
     * 将字符串转换为int类型的
     * @param strInt
     * @param defaultValue
     * @return
     */
    public static int parseInt(String strInt,int defaultValue){
        if (strInt == null){//前台客户端传参一定会是null,null转integer会报错
            return defaultValue;
        }

        try {
            return Integer.parseInt(strInt);
        } catch (Exception e) {
            //e.printStackTrace();

        }

        return defaultValue;
    }

}
