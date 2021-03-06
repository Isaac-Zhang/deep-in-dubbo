package com.sxzhongf.deep.in.dubbo.provider.api.async;

import com.sxzhongf.deep.in.dubbo.api.service.IGreetingServiceAsync;
import com.sxzhongf.deep.in.dubbo.api.service.IGreetingServiceRpcContext;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;

import java.io.IOException;

/**
 * ApiProviderForAsyncContextApplication for 发布异步服务
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/2/25
 **/
public class ApiProviderForAsyncContextApplication {

    public static void main(String[] args) throws IOException {
        //1. 创建服务发布实例，初始化
        ServiceConfig<IGreetingServiceRpcContext> serviceConfig = new ServiceConfig<>();
        serviceConfig.setApplication(new ApplicationConfig("first-async-provider"));
        serviceConfig.setRegistry(new RegistryConfig("zookeeper://39.106.223.16:2181/"));
        serviceConfig.setInterface(IGreetingServiceRpcContext.class);
        serviceConfig.setGroup("dubbo");
        serviceConfig.setVersion("1.0.0");
        serviceConfig.setRef(new GreetingServiceAsyncContextImpl());

        //2. 设置线程池策略
//        HashMap<String,String> parameters = new HashMap<>();
//        //isaacthreadpool 自定义的线程池
//        parameters.put("threadpool","isaacthreadpool");
//        serviceConfig.setParameters(parameters);

        //3. 导出服务
        serviceConfig.export();

        //4. 阻塞线程
        System.out.println("Async provider2 service is started...");
        System.in.read();
    }
}
