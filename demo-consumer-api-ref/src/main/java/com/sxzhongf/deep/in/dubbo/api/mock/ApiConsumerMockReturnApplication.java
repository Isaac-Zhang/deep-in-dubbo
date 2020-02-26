package com.sxzhongf.deep.in.dubbo.api.mock;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.registry.Registry;
import org.apache.dubbo.registry.RegistryFactory;

/**
 * ApiConsumerMockReturnApplication for 降级策略配置演示
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/2/26
 **/
public class ApiConsumerMockReturnApplication {

    public static void main(String[] args) {
        mockReturn("force");
    }

    private static void mockReturn(String type) {
        //1. 通过增强SPI获取服务注册中心
        RegistryFactory registryFactory = ExtensionLoader.getExtensionLoader(RegistryFactory.class)
                .getAdaptiveExtension();
        //2. 根据zookeeper地址，获取具体的zookeeper注册中心的客户端实例
        Registry registry = registryFactory.getRegistry(URL.valueOf("zookeeper://39.106.223.16:2181/"));
//        //3. 注册降级方案到注册中心
//        registry.register(URL.valueOf(
//                "override://0.0.0.0/com.sxzhongf.deep.in.dubbo.api.service.IGreetingService?category=configurators&dynamic=false&" +
//                        "application=deep-in-dubbo-first-consumer&mock="+type+":return+null&group=dubbo-sxzhongf-group&version=1.0.0"
//        ));
        //4. 从注册中心取消降级策略
        registry.unregister(URL.valueOf(
                "override://0.0.0.0/com.sxzhongf.deep.in.dubbo.api.service.IGreetingService?category=configurators&dynamic=false&" +
                        "application=deep-in-dubbo-first-consumer&mock="+type+":return+null&group=dubbo-sxzhongf-group&version=1.0.0"
        ));
    }
}
