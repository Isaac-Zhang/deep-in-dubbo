package com.sxzhongf.deep.in.dubbo.cluster;

import java.util.List;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.cluster.Directory;
import org.apache.dubbo.rpc.cluster.LoadBalance;
import org.apache.dubbo.rpc.cluster.support.AbstractClusterInvoker;

/**
 * SxzhongfCustomClusterInvoker for 自定义cluster invoker
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/3/20
 **/
public class SxzhongfCustomClusterInvoker<T> extends AbstractClusterInvoker<T> {

    public SxzhongfCustomClusterInvoker(Directory directory) {
        super(directory);
    }

    @Override
    protected Result doInvoke(Invocation invocation, List<Invoker<T>> invokers, LoadBalance loadbalance) throws RpcException {
        checkInvokers(invokers, invocation);
        System.out.printf("自定义集群容错。");
        return null;
    }
}
