package com.sxzhongf.deep.in.dubbo.api.service;

/**
 * IGreetingServiceRpcContext for 演示 {@link org.apache.dubbo.rpc.AsyncContext} 如何实现异步执行
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/2/23
 **/
public interface IGreetingServiceRpcContext {
    String sayHello(String name);
}
