package com.sxzhongf.deep.in.dubbo.api.service;

import java.util.concurrent.CompletableFuture;

/**
 * IGreetingServiceAsync for 演示 基于{@link CompletableFuture} 签名的接口如何实现异步执行
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/2/23
 **/
public interface IGreetingServiceAsync {

    CompletableFuture<String> sayHello(String name);
}
