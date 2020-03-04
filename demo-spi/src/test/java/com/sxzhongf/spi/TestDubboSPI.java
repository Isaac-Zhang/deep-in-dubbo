package com.sxzhongf.spi;

import com.sxzhongf.spi.api.IRobot;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.junit.Test;

/**
 * TestDubboSPI for TODO
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/3/4
 **/
public class TestDubboSPI {

    @Test
    public void hi(){
        ExtensionLoader<IRobot> extensionLoader = ExtensionLoader.getExtensionLoader(IRobot.class);
        extensionLoader.getExtension("bumblebee").hi();
        extensionLoader.getExtension("optimusPrime").hi();
        extensionLoader.getDefaultExtension().hi();
    }
}
