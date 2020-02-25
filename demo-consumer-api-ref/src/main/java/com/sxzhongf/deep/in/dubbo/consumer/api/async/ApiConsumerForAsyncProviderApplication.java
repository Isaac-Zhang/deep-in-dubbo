package com.sxzhongf.deep.in.dubbo.consumer.api.async;

import com.sxzhongf.deep.in.dubbo.api.service.IGreetingServiceAsync;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.RpcContext;

/**
 * ApiConsumerForAsyncProviderApplication for 调用异步provider
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/2/25
 **/
public class ApiConsumerForAsyncProviderApplication {

    public static void main(String[] args) throws InterruptedException {
        ReferenceConfig<IGreetingServiceAsync> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setApplication(new ApplicationConfig("first-ref-async-provider"));
        referenceConfig.setRegistry(new RegistryConfig("zookeeper://39.106.223.16:2181/"));
        referenceConfig.setInterface(IGreetingServiceAsync.class);
        referenceConfig.setVersion("1.0.0");
        referenceConfig.setGroup("dubbo");
        referenceConfig.setTimeout(5000);

        //2. 引用服务
        IGreetingServiceAsync greetingServiceAsync = referenceConfig.get();
        //3. 设置隐性参数
        RpcContext.getContext().setAttachment("company", "众福网络");
        //4. 获取future 并设置回调
        greetingServiceAsync.sayHello("pan").whenComplete((b, t) -> {
            if (t != null) {
                System.out.println(t);
            } else {
                System.out.println(b);
            }
        });

        Thread.currentThread().join();
    }
}
