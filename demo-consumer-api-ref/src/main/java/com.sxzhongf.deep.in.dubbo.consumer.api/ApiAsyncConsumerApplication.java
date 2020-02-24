package com.sxzhongf.deep.in.dubbo.consumer.api;

import com.sxzhongf.deep.in.dubbo.api.service.IGreetingService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.RpcContext;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * ApiAsyncConsumerApplication for dubbo 2.6.* 提供的异步调用
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/2/24
 **/
public class ApiAsyncConsumerApplication {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // 1. 创建引用实例，初始化
        ReferenceConfig<IGreetingService> referenceConfig = new ReferenceConfig<IGreetingService>();
        referenceConfig.setApplication(new ApplicationConfig("deep-in-dubbo-first-async-consumer"));
        referenceConfig.setRegistry(new RegistryConfig("zookeeper://39.106.223.16:2181/"));
        referenceConfig.setInterface(IGreetingService.class);
        referenceConfig.setGroup("dubbo-sxzhongf-group");
        referenceConfig.setVersion("1.0.0");

        // 2. 设置为异步
        referenceConfig.setAsync(true);

        // 3. 直接返回null
        IGreetingService greetingService = referenceConfig.get();
        System.out.printf(greetingService.sayHello("async pan"));

        // 4. 等待异步返回结果
        Future<String> future = RpcContext.getContext().getFuture();
        System.out.printf("async api consumer return : " + future.get());
    }
}
