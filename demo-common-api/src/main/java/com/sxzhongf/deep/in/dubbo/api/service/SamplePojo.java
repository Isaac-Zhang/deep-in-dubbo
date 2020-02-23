package com.sxzhongf.deep.in.dubbo.api.service;

/**
 * SamplePojo for 演示泛化调用时的参数转换
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/2/23
 **/
public class SamplePojo {

    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
