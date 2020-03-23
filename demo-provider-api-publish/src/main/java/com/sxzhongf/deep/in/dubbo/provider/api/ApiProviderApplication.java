package com.sxzhongf.deep.in.dubbo.provider.api;

import com.sxzhongf.deep.in.dubbo.api.service.IGreetingService;
import java.util.HashMap;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.rpc.RpcContext;

import java.io.IOException;

/**
 * ApiProviderApplication for
 * TODO : 表示在此处将要实现的功能，提醒在后续阶段将会在此处添加代码
 * FIXME : 表示此处的代码逻辑有出入，或者根本不能运行，提醒在后续阶段将会修改此处代码
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/2/24
 **/
public class ApiProviderApplication {

    public static void main(String[] args) throws IOException {
        // 1. 创建ServiceConfig实例
        ServiceConfig<IGreetingService> serviceConfig = new ServiceConfig<>();
        // 2. 设置应用程序配置
        serviceConfig.setApplication(new ApplicationConfig("deep-in-dubbo-first-provider"));
        // 3. 设置注册中心
        RegistryConfig registryConfig = new RegistryConfig("zookeeper://39.106.223.16:2181/");
        serviceConfig.setRegistry(registryConfig);
        // 4. 设置接口和实现类
        // 5. 设置服务分组和版本
        // dubbo中，服务接口+服务分组+服务版本 唯一的确定一个服务，同一个接口可以有不同版本，方便维护升级
        serviceConfig.setInterface(IGreetingService.class);
        serviceConfig.setRef(new GreetingServiceImpl());
        serviceConfig.setVersion("1.0.0");
        serviceConfig.setGroup("dubbo-sxzhongf-group");
        // 本地启动服务进行一些调试工作，可以设置禁止服务导出
        serviceConfig.setExport(true);
        // 延迟导出服务
        serviceConfig.setDelay(1000);
        RpcContext.getContext().setAttachment("age","18");

        // 6. 设置自定义线程模型
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("dispatcher", "sxzhongfCustomDispatcher");
        serviceConfig.setParameters(parameters);

        // 7. 导出服务，启动Netty监听链接请求，并将服务注册到注册中心
        serviceConfig.export();
        /*
            比如服务的配置信息，服务实例等。每个被导出的服务对应一个 {@link ProviderModel}。
            ApplicationModel 持有所有的 ProviderModel。
         */

        // 8. 挂起线程，避免服务停止
        System.out.println("api provider service is started...");
        System.in.read();
    }
}
