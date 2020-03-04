package com.sxzhongf.spi;

import com.sxzhongf.spi.api.IRobot;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ServiceLoader;

/**
 * TestApplication for TODO
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/3/4
 **/
public class TestApplication {

    @Test
    public void testJavaSpi() {
        ServiceLoader<IRobot> serviceLoader = ServiceLoader.load(IRobot.class);
        serviceLoader.forEach(
                IRobot::hi
        );
    }
}
