package com.sxzhongf.deep.in.dubbo.provider.api.dispatcher;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.remoting.ChannelHandler;
import org.apache.dubbo.remoting.transport.dispatcher.WrappedChannelHandler;

/**
 * SxzhongfCustomChannelHandler for
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/3/23
 **/
public class SxzhongfCustomChannelHandler extends WrappedChannelHandler {

    public SxzhongfCustomChannelHandler(ChannelHandler handler, URL url) {
        super(handler, url);
    }
}
