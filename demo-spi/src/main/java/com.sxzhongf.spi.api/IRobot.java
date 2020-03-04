package com.sxzhongf.spi.api;

import org.apache.dubbo.common.extension.SPI;

/**
 * IRobot for test spi
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/3/4
 **/
@SPI(value = "optimusPrime")
public interface IRobot {

    void hi();
}
