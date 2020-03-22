package com.sxzhongf.deep.in.dubbo.loadbalance;

import java.util.Comparator;
import java.util.List;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.cluster.loadbalance.AbstractLoadBalance;

/**
 * SxzhongfCustomLoadBalance for custom load balance
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/3/22
 **/
public class SxzhongfCustomLoadBalance extends AbstractLoadBalance {

    @Override
    protected <T> Invoker<T> doSelect(List<Invoker<T>> invokers, URL url, Invocation invocation) {
        Invoker invoker = null;
        //自定义负载均衡实现算法
        System.out.println("自定义loadbalance实现");
        return invokers.stream().sorted(Comparator.comparing(Invoker::isAvailable)).findFirst().get();
    }
}
