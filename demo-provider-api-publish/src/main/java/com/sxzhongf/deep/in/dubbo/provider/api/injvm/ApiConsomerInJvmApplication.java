package com.sxzhongf.deep.in.dubbo.provider.api.injvm;

import com.sxzhongf.deep.in.dubbo.api.service.IGreetingService;
import com.sxzhongf.deep.in.dubbo.provider.api.GreetingServiceImpl;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.rpc.RpcContext;

/**
 * ApiConsomerInJvmApplication for  本地服务（在同一个JVM中）暴露和引用
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/2/26
 **/
public class ApiConsomerInJvmApplication {

    public static void main(String[] args) throws InterruptedException {
        exportService();
        refService();
        Thread.currentThread().join();
    }

    /**
     * 本地服务暴露
     */
    public static void exportService() {
        //1. 创建ServiceConfig实例，初始化
        ServiceConfig<IGreetingService> serviceConfig = new ServiceConfig<>();
        serviceConfig.setApplication(new ApplicationConfig("first-provider-injvm"));
        serviceConfig.setRegistry(new RegistryConfig("zookeeper://39.106.223.16:2181/"));
        serviceConfig.setInterface(IGreetingService.class);
        serviceConfig.setRef(new GreetingServiceImpl());

        serviceConfig.setVersion("1.0.0");
        serviceConfig.setGroup("dubbo");

        //导出服务
        serviceConfig.export();

        //挂起线程，避免服务停止（在这里不需要挂起）
        System.out.println("Injvm provider server is started...");
    }

    /**
     * 引用本地服务
     */
    public static void refService() {
        // 1. 创建ReferenceConfig，初始化
        ReferenceConfig<IGreetingService> referenceConfig = new ReferenceConfig<>();
        // 2. 设置应用程序信息
        referenceConfig.setApplication(new ApplicationConfig("deep-in-dubbo-first-consumer-injvm"));
        // 3. 设置注册中心
        referenceConfig.setRegistry(new RegistryConfig("zookeeper://39.106.223.16:2181/"));
        // 4. 设置服务接口和超时时间
        referenceConfig.setInterface(IGreetingService.class);
        // 默认重试3次
        referenceConfig.setTimeout(5000);
        referenceConfig.setVersion("1.0.0");
        referenceConfig.setGroup("dubbo");
        referenceConfig.setCheck(false);

        //引用服务
        IGreetingService greetingService = referenceConfig.get();
        RpcContext.getContext().setAttachment("company", "injvm sxzhongf");
        System.out.println(greetingService.sayHello("injvm pan"));
    }
}
