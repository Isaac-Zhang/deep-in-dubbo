package com.sxzhongf.deep.in.dubbo.api.service;

/**
 * IDemoService for 测试接口
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/1/21
 **/
public interface IGreetingService {

    String sayHello(String name);

    SampleResult<String> testGeneric(SamplePojo pojo);
}
