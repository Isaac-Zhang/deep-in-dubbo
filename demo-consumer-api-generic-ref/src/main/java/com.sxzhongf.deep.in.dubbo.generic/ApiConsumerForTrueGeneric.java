package com.sxzhongf.deep.in.dubbo.generic;

import org.apache.dubbo.common.json.JSON;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * ApiConsumerForTrueGeneric for 演示泛化调用 true
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/2/25
 **/
public class ApiConsumerForTrueGeneric {

    public static void main(String[] args) throws IOException {
        // 1. 创建配置并初始化，将其引用类型固定为：GenericService
        ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setApplication(new ApplicationConfig("first-generic-consumer-true"));
        referenceConfig.setRegistry(new RegistryConfig("zookeeper://39.106.223.16:2181/"));
        referenceConfig.setGroup("dubbo-sxzhongf-group");
        referenceConfig.setVersion("1.0.0");
        referenceConfig.setTimeout(10000);

        // 2. 设置为泛化引用，类型为true
        referenceConfig.setInterface("com.sxzhongf.deep.in.dubbo.api.service.IGreetingService");
        referenceConfig.setGeneric(true);

        //3. 使用GenericService代替接口引用
        GenericService genericService = referenceConfig.get();

        //4. 泛型调用，如果返回类型为
        //  4.1 基本类型/Date/List/Map 等则不需要转换，直接调用
        //  4.2 返回值为SamplePojo等对象类型，将自动转换为Map
        Object result = genericService.$invoke(
                "sayHello",
                new String[]{"java.lang.String"},
                new Object[]{"generic pan"}
        );
        System.out.println(JSON.json(result));

        //5. POJO参数转换为Map
        Map<String, Object> map = new HashMap<>();
        // map中固定有一个key=“class”,用于反射调用
        map.put("class", "com.sxzhongf.deep.in.dubbo.api.service.SamplePojo");
        map.put("id", "1988");
        map.put("name", "zhangpan");
        //6. 发起泛型调用
        result = genericService.$invoke(
                "testGeneric",
                new String[]{"com.sxzhongf.deep.in.dubbo.api.service.SamplePojo"}, //接受的参数类型
                new Object[]{map} //参数值
        );

        System.out.println(result);
    }
}
