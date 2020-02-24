package com.sxzhongf.deep.in.dubbo.provider.service.impl;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * ProviderApplication for provider 启动类
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/1/21
 **/
public class ProviderApplication {

    public static void main(String[] args) {
        try {
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                    new String[]{"META-INF/spring/dubbo-demo-provider.xml"}
            );
            context.start();
            System.out.println("Sxzhongf Provider started.");
            System.in.read(); // press any key to exit
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }
}
