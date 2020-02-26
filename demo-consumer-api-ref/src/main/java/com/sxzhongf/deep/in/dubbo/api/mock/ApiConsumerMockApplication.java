package com.sxzhongf.deep.in.dubbo.api.mock;

import com.sxzhongf.deep.in.dubbo.api.service.IGreetingService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.RpcContext;

/**
 * ApiConsumerMockApplication for mock provider for consumer
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/2/26
 **/
public class ApiConsumerMockApplication {

    public static void main(String[] args) {
        //1. 配置&初始化
        ReferenceConfig<IGreetingService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setApplication(new ApplicationConfig("first-consumer-mock"));
        referenceConfig.setRegistry(new RegistryConfig("zookeeper://39.106.223.16:2181/"));
        referenceConfig.setGroup("dubbo-sxzhongf-group");
        referenceConfig.setVersion("1.0.0");
        referenceConfig.setInterface(IGreetingService.class);
        // provider 返回需要1秒，设置500毫秒超时，让调用mock对象
        referenceConfig.setTimeout(500);

        //2. 设置在服务启动时不检查服务提供者是否可用
        referenceConfig.setCheck(false);
        //3. 设置mock
        referenceConfig.setMock(true);
        //4. 服务引用
        IGreetingService greetingService = referenceConfig.get();
        //5. 设置隐式参数
        RpcContext.getContext().setAttachment("company","mock sxzhongf");
        //6. 服务调用
        System.out.println(greetingService.sayHello("pan"));
    }
}
