package com.sxzhongf.consumer.custom.registry.founding;

import com.google.common.base.Joiner;
import java.io.IOException;
import org.apache.dubbo.common.json.JSON;

/**
 * TestApplication for test
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/3/28
 **/
public class TestApplication {

    private static final Joiner jStrFormat = Joiner.on("|").useForNull("nil");

    public static void main(String[] args) throws InterruptedException {
        ZKRegistryIpList zkRegistryIpList = new ZKRegistryIpList();
        zkRegistryIpList.init("39.106.223.16:2181", "dubbo",
            "com.sxzhongf.deep.in.dubbo.api.service.IGreetingService:1.0.0", "dubbo-sxzhongf-group");
        try {
            System.out.println(jStrFormat.join("parseIpList", JSON.json(zkRegistryIpList.getIpList())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread.currentThread().join();
    }
}
