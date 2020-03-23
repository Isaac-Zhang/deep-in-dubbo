package com.sxzhongf.deep.in.dubbo.provider.api.dispatcher;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.remoting.ChannelHandler;
import org.apache.dubbo.remoting.Dispatcher;

/**
 * SxzhongfCustomDispatcher for
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/3/23
 **/
public class SxzhongfCustomDispatcher implements Dispatcher {

    private static final String NAME = "sxzhongfCustomDispatcher";

    @Override
    public ChannelHandler dispatch(ChannelHandler handler, URL url) {
        System.out.println("自定义dispatcher");
        return handler;// new SxzhongfCustomChannelHandler(handler, url);
    }
}
