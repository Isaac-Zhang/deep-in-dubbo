package com.sxzhongf.nacos.provider.api;

import com.sxzhongf.deep.in.dubbo.api.service.IGreetingService;
import com.sxzhongf.deep.in.dubbo.api.service.SamplePojo;
import com.sxzhongf.deep.in.dubbo.api.service.SampleResult;
import org.apache.dubbo.common.json.JSON;
import org.apache.dubbo.rpc.RpcContext;

/**
 * GreetingServiceImpl for 实现 {@link IGreetingService}
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/2/24
 **/
public class GreetingServiceImpl implements IGreetingService {

    @Override
    public String sayHello(String name) {

        try {
            System.out.println("thread sleep 1s.");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 获取调用方在上下文对象中附加的变量的值
        return "Hello, " + name + " ," + RpcContext.getContext().getAttachment("company");
    }


    @Override
    public SampleResult<String> testGeneric(SamplePojo pojo) {
        SampleResult<String> result = new SampleResult<String>();
        result.setSuccess(true);

        try {
            result.setData(JSON.json(pojo));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
