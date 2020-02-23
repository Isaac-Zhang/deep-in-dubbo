package com.sxzhongf.deep.in.dubbo.api.service;

import java.io.Serializable;

/**
 * SampleResult for {@link SamplePojo} 的返回值类型，用于演示泛化调用的参数转换
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/2/23
 **/
public class SampleResult<T> implements Serializable {
    private static final long serialVersionID = 1L;

    private T data;
    private boolean success;
    private String msg;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
