package com.sxzhongf.deep.in.dubbo.generic;

import com.alibaba.dubbo.common.Constants;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.common.io.UnsafeByteArrayInputStream;
import org.apache.dubbo.common.io.UnsafeByteArrayOutputStream;
import org.apache.dubbo.common.serialize.Serialization;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.service.GenericService;

import java.io.IOException;

/**
 * ApiConsumerForNativejavaGeneric for 演示泛化调用 nativejava
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/2/25
 **/
public class ApiConsumerForNativejavaGeneric {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // 1. 创建配置并初始化，将其引用类型固定为：GenericService
        ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setApplication(new ApplicationConfig("first-generic-consumer-bean"));
        referenceConfig.setRegistry(new RegistryConfig("zookeeper://39.106.223.16:2181/"));
        referenceConfig.setGroup("dubbo-sxzhongf-group");
        referenceConfig.setVersion("1.0.0");
        referenceConfig.setTimeout(10000);

        // 2. 设置为泛化引用，类型为true
        referenceConfig.setInterface("com.sxzhongf.deep.in.dubbo.api.service.IGreetingService");
        referenceConfig.setGeneric("nativejava");
        RpcContext.getContext().setAttachment("company", "nativejava sxzhongf");

        //3. 使用GenericService代替接口引用
        GenericService genericService = referenceConfig.get();

        //4. 泛型调用，参数使用nativejava进行序列化
        UnsafeByteArrayOutputStream outputStream = new UnsafeByteArrayOutputStream();
        ExtensionLoader.getExtensionLoader(Serialization.class)
                .getExtension(Constants.GENERIC_SERIALIZATION_NATIVE_JAVA)
                .serialize(null, outputStream).writeObject("nativejava pan");
        Object result = genericService.$invoke(
                "sayHello",
                new String[]{"java.lang.String"}, // 参数类型
                new Object[]{outputStream.toByteArray()} // 将使用JavaBean序列化的参数传入
        );

        // 将二进制结果使用java反序列为对象
        UnsafeByteArrayInputStream inputStream = new UnsafeByteArrayInputStream((byte[]) result);

        System.out.println(ExtensionLoader.getExtensionLoader(Serialization.class).getExtension(Constants.GENERIC_SERIALIZATION_NATIVE_JAVA).deserialize(
                null, inputStream
        ).readObject());

    }
}
