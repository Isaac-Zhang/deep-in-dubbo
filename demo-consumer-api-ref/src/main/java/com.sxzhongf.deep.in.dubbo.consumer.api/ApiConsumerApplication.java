package com.sxzhongf.deep.in.dubbo.consumer.api;

import com.sxzhongf.deep.in.dubbo.api.service.IGreetingService;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.RpcContext;

/**
 * ApiConsumerApplication for API同步调用服务
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/2/24
 **/
public class ApiConsumerApplication {

    public static void main(String[] args) {
        // 1. 创建服务引用对象实例
        ReferenceConfig<IGreetingService> referenceConfig = new ReferenceConfig<IGreetingService>();
        // 2. 设置应用程序信息
        ApplicationConfig applicationConfig = new ApplicationConfig("deep-in-dubbo-first-consumer");
        applicationConfig.setCompiler("jdk");
        referenceConfig.setApplication(applicationConfig);
        // 3. 设置注册中心
        referenceConfig.setRegistry(new RegistryConfig("zookeeper://39.106.223.16:2181/"));
        // 4. 设置服务接口和超时时间
        referenceConfig.setInterface(IGreetingService.class);
        // 默认重试3次
        referenceConfig.setTimeout(5000);
//        // 5. 设置自定义负载均衡策略和集群容错策略
//        referenceConfig.setLoadbalance("isaacLoadBalance");
//        referenceConfig.setCluster("isaacBroadcast");
        // 6. 设置服务分组和版本
//        referenceConfig.setGroup("*");
        referenceConfig.setGroup("dubbo-sxzhongf-group,dubbo");
        // 设置路由匹配规则
        // 条件路由规则的格式如下：([服务消费者匹配条件] => [服务提供者匹配条件])
//        referenceConfig.setRouter();
        referenceConfig.setVersion("1.0.0");
        // 重试5次+默认1次，共执行6次。org.apache.dubbo.rpc.cluster.support.FailoverClusterInvoker.doInvoke
        referenceConfig.setRetries(5);
        // 7. 引用服务
        IGreetingService greetingService = referenceConfig.get();
        // 8. 设置隐式参数
        RpcContext.getContext().setAttachment("company", "sxzhongf");
        // 获取provider端传递的隐式参数(FIXME: 需要后续追踪)
//        System.out.println("年龄是：" + RpcContext.getContext().getAttachment("age"));
        //9. 调用服务
        System.out.println(greetingService.sayHello("pan"));
    }
}
