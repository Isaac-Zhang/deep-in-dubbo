package com.sxzhongf.deep.in.dubbo.consumer.api.async;

import com.sxzhongf.deep.in.dubbo.api.service.IGreetingServiceAsync;
import com.sxzhongf.deep.in.dubbo.api.service.IGreetingServiceRpcContext;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.RpcContext;

/**
 * ApiConsumerForAsyncContextProviderApplication for 调用异步context provider
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/2/25
 **/
public class ApiConsumerForAsyncContextProviderApplication {

    public static void main(String[] args) throws InterruptedException {
        ReferenceConfig<IGreetingServiceRpcContext> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setApplication(new ApplicationConfig("first-ref-async-provider"));
        referenceConfig.setRegistry(new RegistryConfig("zookeeper://39.106.223.16:2181/"));
        referenceConfig.setInterface(IGreetingServiceRpcContext.class);
        referenceConfig.setVersion("1.0.0");
        referenceConfig.setGroup("dubbo");
        referenceConfig.setTimeout(5000);

        //2. 引用服务
        IGreetingServiceRpcContext greetingServiceAsync = referenceConfig.get();
        //3. 设置隐性参数
        RpcContext.getContext().setAttachment("company", "众福网络2");
        //4. 获取future 并设置回调
        String result = greetingServiceAsync.sayHello("pan2");
        System.out.println(result);
    }
}
