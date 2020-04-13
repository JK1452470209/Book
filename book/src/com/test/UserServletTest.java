package com.test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @outhor Mr.JK
 * @create 2020-04-05  17:37
 */
public class UserServletTest {
    public void login(){
        System.out.println("这是login方法调用了");
    }
    public void regist(){
        System.out.println("这是regist方法调用了");
    }
    public void updateuser(){
        System.out.println("这是updateuser方法调用了");
    }
    public void updateUserpassword(){
        System.out.println("这是updateUserpassword方法调用了");
    }

    public static void main(String[] args) {
        String action = "login";
        Method method = null;
        try {
            method = UserServletTest.class.getDeclaredMethod(action);

            method.invoke(new UserServletTest());


        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println(method);

    }
}
