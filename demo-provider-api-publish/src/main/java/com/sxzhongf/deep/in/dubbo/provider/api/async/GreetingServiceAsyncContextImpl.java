package com.sxzhongf.deep.in.dubbo.provider.api.async;

import com.sxzhongf.deep.in.dubbo.api.service.IGreetingServiceRpcContext;
import org.apache.dubbo.common.utils.NamedThreadFactory;
import org.apache.dubbo.rpc.AsyncContext;
import org.apache.dubbo.rpc.RpcContext;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * GreetingServiceAsyncContextImpl for 异步context 实现
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/2/25
 **/
public class GreetingServiceAsyncContextImpl implements IGreetingServiceRpcContext {

    //1. 创建业务自定义线程池
    private final ThreadPoolExecutor businessThreadpool = new ThreadPoolExecutor(
            8, 16, 1, TimeUnit.MINUTES,
            new SynchronousQueue<>(),
            new NamedThreadFactory("business-thread-pool"),
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

    @Override
    public String sayHello(String name) {
        // 开启异步
        final AsyncContext asyncContext = RpcContext.startAsync();
        businessThreadpool.execute(() -> {
            // 如果想要使用原上下文 RpcContext，则必须在第一句执行切换上下文
            asyncContext.signalContextSwitch();
            try {
                System.out.println("等待com.sxzhongf.deep.in.dubbo.provider.api.async.GreetingServiceAsyncContextImpl.sayHello唤醒：");
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 输出响应内容
            asyncContext.write("Hello " + name + " " + RpcContext.getContext().getAttachment("company"));
        });
        return null;
    }
}
