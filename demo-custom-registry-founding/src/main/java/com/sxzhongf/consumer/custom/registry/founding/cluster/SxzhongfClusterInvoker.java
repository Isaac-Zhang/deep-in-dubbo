package com.sxzhongf.consumer.custom.registry.founding.cluster;

import java.util.List;
import java.util.function.Supplier;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.cluster.Directory;
import org.apache.dubbo.rpc.cluster.LoadBalance;

/**
 * SxzhongfClusterInvoker for TODO
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/3/28
 **/
public class SxzhongfClusterInvoker<T> extends SxzhongfAbstractClusterInvoker<T> {

    public SxzhongfClusterInvoker(Directory<T> directory) {
        super(directory);
    }

    @Override
    protected Result doInvoke(Invocation invocation, List<Invoker<T>> invokers, LoadBalance loadbalance)
        throws RpcException {
        // 1. 检查是否设置了IP
        String ip = String.valueOf(RpcContext.getContext().get("ip"));
        if (StringUtils.isBlank(ip)) {
            throw new RuntimeException("ip is blank.");
        }

        // 2. 检查是否有可用的invoker
        checkInvokers(invokers, invocation);

        // 3. 根据指定的IP获取对应的Invoker
        Invoker ik = invokers.stream()
            .filter(invoker -> invoker.getUrl().getHost().equals(ip))
            .findFirst()
            .orElseThrow(
                () -> new RpcException(RpcException.NO_INVOKER_AVAILABLE_AFTER_FILTER, "没有找到可用的Invoker。"));
        // 这里需要判断异常
        try {
            return ik.invoke(invocation);
        } catch (RpcException e) {
            throw e;
        }
    }
}
