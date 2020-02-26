package com.sxzhongf.deep.in.dubbo.api.service;

import org.apache.dubbo.rpc.RpcContext;

/**
 * IGreetingServiceMock for 针对com.sxzhongf.deep.in.dubbo.api.service.IGreetingService进行模拟（mock）
 * mock 实现类必须符合'接口包名.类名+Mock'格式，否则启动会抛出异常
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/2/26
 **/
public class IGreetingServiceMock implements IGreetingService {
    @Override
    public String sayHello(String name) {
        return "mock IGreetingService#sayHello"+ RpcContext.getContext().getAttachment("company");
    }

    @Override
    public SampleResult<String> testGeneric(SamplePojo pojo) {
        SampleResult<String> result = new SampleResult<>();
        result.setMsg("mock IGreetingService#testGeneric");
        return result;
    }
}
