package com.sxzhongf.deep.in.dubbo.provider.service.impl;

import com.sxzhongf.deep.in.dubbo.api.service.IDemoService;

/**
 * DemoServiceImpl for 接口实现
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/1/21
 **/
public class DemoServiceImpl implements IDemoService {
    public String sayHello(String name) {
        return "Hello, " + name;
    }
}
