package com.sxzhongf.deep.in.dubbo.consumer;

import com.sxzhongf.deep.in.dubbo.api.service.IDemoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * ConsumerApplication for 消费端demo
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/1/21
 **/
public class ConsumerApplication {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[]{"META-INF/spring/dubbo-demo-consumer.xml"}
        );
        context.start();
        // Obtaining a remote service proxy
        IDemoService demoService = (IDemoService) context.getBean("demoService");
        // Executing remote methods
        String hello = demoService.sayHello("Isaac");
        // Display the call result
        System.out.println(hello);
    }
}
