package com.sxzhongf.deep.in.dubbo.provider.api.async;

import com.sxzhongf.deep.in.dubbo.api.service.IGreetingServiceAsync;
import org.apache.dubbo.common.utils.NamedThreadFactory;
import org.apache.dubbo.rpc.RpcContext;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * GreetingServiceAsyncImpl for TODO
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/2/25
 **/
public class GreetingServiceAsyncImpl implements IGreetingServiceAsync {

    //1. 创建业务自定义线程池
    private final ThreadPoolExecutor businessThreadpool = new ThreadPoolExecutor(
            8, 16, 1, TimeUnit.MINUTES,
            new SynchronousQueue<>(),
            new NamedThreadFactory("business-thread-pool"),
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

    /**
     * 实现服务处理接口
     *
     * @param name
     * @return {@link CompletableFuture}
     */
    @Override
    public CompletableFuture<String> sayHello(String name) {
        // 使用CompletableFuture.supplyAsync 让服务异步化处理

        //保存当前线程上下文，为supplyAsync提供自定义线程池，避免使用
        //JDK公用线程池（ForkJoinPool.commonPool()）
        RpcContext rpcContext = RpcContext.getContext();

        return CompletableFuture.supplyAsync(() -> {

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("provider async 1 return: ");
            return "Hello " + name + " " + rpcContext.getAttachment("company");

        }, businessThreadpool);
    }
}
