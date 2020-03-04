package com.sxzhongf.spi.api.impl;

import com.sxzhongf.spi.api.IRobot;

/**
 * Bumblebee for TODO
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/3/4
 **/
public class Bumblebee implements IRobot {
    @Override
    public void hi() {
        System.out.println("hi, i am bumblebee");
    }
}
