package com.liuxinwen.rocket;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author lxw
 * @date 2018/12/1718:23
 */
public class BootStrap {
    public static void main(String[] args) {
        try {
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring/*Beans.xml");
            context.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        synchronized (BootStrap.class) {
            while (true) {
                try {
                    BootStrap.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
