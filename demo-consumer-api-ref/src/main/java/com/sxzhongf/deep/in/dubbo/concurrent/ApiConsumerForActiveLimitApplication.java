package com.sxzhongf.deep.in.dubbo.concurrent;

import com.sxzhongf.deep.in.dubbo.api.service.IGreetingService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;

/**
 * ApiConsumerForActiveLimitApplication for  测试消费端并发数处理
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/3/25
 **/
public class ApiConsumerForActiveLimitApplication {

    public static void main(String[] args) {
        //1.创建config
        ReferenceConfig<IGreetingService> referenceConfig = new ReferenceConfig<>();
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("test-concurrent-limit-consumer");
        referenceConfig.setApplication(applicationConfig);

        referenceConfig.setRegistry(new RegistryConfig("zookeeper://39.106.223.16:2181/"));
        referenceConfig.setInterface(IGreetingService.class);
        referenceConfig.setTimeout(10000);
        referenceConfig.setGroup("dubbo-sxzhongf-group");
        referenceConfig.setVersion("1.0.0");
        //设置并发限制个数
        referenceConfig.setActives(10);
        IGreetingService greetingService = referenceConfig.get();
        System.out.println(greetingService.sayHello("active limit pan"));
    }
}
