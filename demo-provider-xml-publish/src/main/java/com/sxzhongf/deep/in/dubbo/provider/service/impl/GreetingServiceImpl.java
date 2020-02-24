package com.sxzhongf.deep.in.dubbo.provider.service.impl;

import com.sxzhongf.deep.in.dubbo.api.service.IGreetingService;
import com.sxzhongf.deep.in.dubbo.api.service.SamplePojo;
import com.sxzhongf.deep.in.dubbo.api.service.SampleResult;
import org.apache.dubbo.common.json.JSON;
import org.apache.dubbo.rpc.RpcContext;

/**
 * DemoServiceImpl for 接口实现
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/1/21
 **/
public class GreetingServiceImpl implements IGreetingService {
    public String sayHello(String name) {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 获取调用方在上下文对象中附加的变量的值
        return "Hello, " + name + " ," + RpcContext.getContext().getAttachment("company");
    }

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
