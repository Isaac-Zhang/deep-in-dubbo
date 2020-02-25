package com.sxzhongf.deep.in.dubbo.generic;

import org.apache.dubbo.common.beanutil.JavaBeanDescriptor;
import org.apache.dubbo.common.beanutil.JavaBeanSerializeUtil;
import org.apache.dubbo.common.json.JSON;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * ApiConsumerForBeanGeneric for 演示泛化调用 bean
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/2/25
 **/
public class ApiConsumerForBeanGeneric {

    public static void main(String[] args) throws IOException {
        // 1. 创建配置并初始化，将其引用类型固定为：GenericService
        ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setApplication(new ApplicationConfig("first-generic-consumer-bean"));
        referenceConfig.setRegistry(new RegistryConfig("zookeeper://39.106.223.16:2181/"));
        referenceConfig.setGroup("dubbo-sxzhongf-group");
        referenceConfig.setVersion("1.0.0");
        referenceConfig.setTimeout(10000);

        // 2. 设置为泛化引用，类型为true
        referenceConfig.setInterface("com.sxzhongf.deep.in.dubbo.api.service.IGreetingService");
        referenceConfig.setGeneric("bean");

        //3. 使用GenericService代替接口引用
        GenericService genericService = referenceConfig.get();

        //4. 泛型调用，参数使用Java Bean进行序列化
        JavaBeanDescriptor param = JavaBeanSerializeUtil.serialize("bean pan");
        Object result = genericService.$invoke(
                "sayHello",
                new String[]{"java.lang.String"}, // 参数类型
                new Object[]{param} // 将使用JavaBean序列化的参数传入
        );

        // 结果需要反序列化
        result = JavaBeanSerializeUtil.deserialize((JavaBeanDescriptor) result);
        System.out.println(result);

        //下面代码放开会报错，因为map无法转换为具体的java对象
//        //5. POJO参数转换为Map
//        Map<String, Object> map = new HashMap<>();
//        // map中固定有一个key=“class”,用于反射调用
//        map.put("class", "com.sxzhongf.deep.in.dubbo.api.service.SamplePojo");
//        map.put("id", "1988");
//        map.put("name", "zhangpan");
//
//        JavaBeanDescriptor param2 = JavaBeanSerializeUtil.serialize(map);
//        //6. 发起泛型调用
//        result = genericService.$invoke(
//                "testGeneric",
//                new String[]{"com.sxzhongf.deep.in.dubbo.api.service.SamplePojo"}, //接受的参数类型
//                new Object[]{param2} //参数值
//        );
//
//        System.out.println(JavaBeanSerializeUtil.deserialize((JavaBeanDescriptor) result));
    }
}
