package com.sxzhongf.deep.in.dubbo.consumer.api.async;

import com.sxzhongf.deep.in.dubbo.api.service.IGreetingService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.RpcContext;

import java.util.concurrent.CompletableFuture;

/**
 * ApiAsyncConsumerForCompletableFutureApplication for dubbo 2.7.* async
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/2/24
 **/
public class ApiAsyncConsumerForCompletableFutureApplication {

    public static void main(String[] args) throws InterruptedException {
        //1. 创建对象和初始化
        ReferenceConfig<IGreetingService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setApplication(new ApplicationConfig("deep-in-dubbo-first-async-consumer3"));
        referenceConfig.setRegistry(new RegistryConfig("zookeeper://39.106.223.16:2181/"));
        referenceConfig.setInterface(IGreetingService.class);
        referenceConfig.setGroup("dubbo-sxzhongf-group");
        referenceConfig.setVersion("1.0.0");
        referenceConfig.setTimeout(5000);

        //2. 设置异步
        referenceConfig.setAsync(true);

        //3. 直接返回null
        IGreetingService greetingService = referenceConfig.get();
        System.out.println(greetingService.sayHello("async 3 pan"));

        //4.异步执行回调
        CompletableFuture<String> future = RpcContext.getContext().getCompletableFuture();
        future.whenComplete((v, e) -> {
                    if (e != null) {
                        e.printStackTrace();
                    } else {
                        System.out.printf(v);
                    }
                }
        );
        System.out.println("main function process over!");
        Thread.currentThread().join();
    }
}
