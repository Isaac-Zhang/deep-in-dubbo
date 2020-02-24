package com.sxzhongf.deep.in.dubbo.consumer.api.async;

import com.alibaba.dubbo.remoting.exchange.ResponseCallback;
import com.alibaba.dubbo.rpc.protocol.dubbo.FutureAdapter;
import com.sxzhongf.deep.in.dubbo.api.service.IGreetingService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.RpcContext;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * ApiAsyncConsumerApplication for dubbo 2.6.* 提供的异步调用-可设置回调函数
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/2/24
 **/
public class ApiAsyncConsumerForCallBackApplication {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // 1. 创建引用实例，初始化
        ReferenceConfig<IGreetingService> referenceConfig = new ReferenceConfig<IGreetingService>();
        referenceConfig.setApplication(new ApplicationConfig("deep-in-dubbo-first-async-consumer2"));
        referenceConfig.setRegistry(new RegistryConfig("zookeeper://39.106.223.16:2181/"));
        referenceConfig.setInterface(IGreetingService.class);
        referenceConfig.setGroup("dubbo-sxzhongf-group");
        referenceConfig.setVersion("1.0.0");
        referenceConfig.setTimeout(5000);

        // 2. 设置为异步
        referenceConfig.setAsync(true);

        // 3. 直接返回null
        IGreetingService greetingService = referenceConfig.get();
        System.out.println(greetingService.sayHello("async pan"));

        // 4. 等待异步返回结果
        ((FutureAdapter) RpcContext.getContext().getFuture()).getFuture().setCallback(
                // 借助Netty异步通信机制，Netty 底层IO线程在接受到响应后，自动回调注册的回调函数
                // 不需要业务线程干预
                new ResponseCallback() {
                    @Override
                    public void done(Object response) {

                    }

                    @Override
                    public void caught(Throwable exception) {

                    }
                }
        );
        //等待子线程执行结束
        Thread.currentThread().join();
    }
}
