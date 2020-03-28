package com.sxzhongf.consumer.custom.registry.founding;

import com.google.common.base.Joiner;
import com.sxzhongf.deep.in.dubbo.api.service.IGreetingService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.json.JSON;
import org.apache.dubbo.common.utils.Assert;
import org.apache.dubbo.common.utils.CollectionUtils;
import org.apache.dubbo.common.utils.UrlUtils;

/**
 * ZKRegistryIpList for 查看zk中provider节点下的所有服务提供者
 * <div>
 * <ul>
 * <li> 1.服务启动时，查找zk上订阅服务（eg. {@link IGreetingService）的节点信息 </li>
 * <li> 2. </li>
 * </ul>
 * </div>
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/3/28
 **/
public class ZKRegistryIpList {

    private static final String SERVICE_NAME = "com.sxzhongf.deep.in.dubbo.api.service.IGreetingService";
    private static final String VERSION = "1.0.0";
    private String dataId = "";// "/dubbo/" + SERVICE_NAME + "/providers:" + VERSION;
    private URL CONSUMER_URL;
    /**
     * Guava 帮助类 https://www.cnblogs.com/lijia0511/p/5770179.html
     *
     * StringBuilder sb = new StringBuilder("result:"); Joiner.on(" ").appendTo(sb, 1, 2, 3);
     * System.out.println(sb);//result:1 2 3
     */
    private static final Joiner j = Joiner.on("|").useForNull("nil");
    private volatile List<String> ipList = new ArrayList<>();

    public final List<String> getIpList() {
        return this.ipList;
    }

    public void init(String zkServerAddr, String zkGroup, String dataId, String serviceGroup) {
        // 1. 参数校验
        Assert.notNull(zkServerAddr, "zkServerAddr is null.");
        Assert.notNull(zkGroup, "zkGroup is null.");
        Assert.notNull(dataId, "dataId is null.");
        Assert.notNull(serviceGroup, "serviceGroup is null.");

        // 2. 拼接订阅的 path
        String[] tempPath = dataId.split(":");
        if (tempPath.length != 2) {
            throw new RuntimeException("dataId is illegal.");
        }
        this.dataId = "/" + zkGroup + "/" + tempPath[0] + "/providers";
        String consumerURL =
            "/consumer://127.0.0.1/?group=" + serviceGroup + "&interface=" + tempPath[0] + "&version=" + tempPath[1];
        CONSUMER_URL = URL.valueOf(consumerURL);

        // 3. 开启zookeeper, 订阅 path 路径下服务提供者信息，并添加监听器
        System.out.println(j.join("init zk ", zkServerAddr, this.dataId, consumerURL));
        ZkClient zkClient = new ZkClient(zkServerAddr);
        List<String> list = zkClient.subscribeChildChanges(this.dataId, new IZkChildListener() {
            @Override
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                System.out.println("处理解析获取到的ZK节点变更");
                // 3.1) 解析服务提供者地址列表
                parseIpList(currentChilds);
                System.out.println(j.join("ipList changed: ", JSON.json(ipList)));
            }
        });

        parseIpList(list);
    }

    /**
     * 对传入的列表内容进行过滤
     */
    private static List<URL> toUrlsWithoutEmpty(URL consumer, List<String> providers) {
        List<URL> urls = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(providers) && providers.size() > 0) {
            urls = providers.stream().map(p -> {
                // 1.对 传入的providers进行 dubbo 协议decode
                return URL.decode(p);
            })  // 2. 过滤出包含:// 字符串的 数据
                .filter(p -> p.contains("://"))
                // 3. 针对过滤出来的 provider ，转换为 URL 对象
                .map(p -> URL.valueOf(p))
                // 4. 过滤传入的 consumer是否和 解密过滤后的url 相匹配
                .filter(url -> UrlUtils.isMatch(consumer, url))
                // 5. 收集所有的匹配后的结果集
                .collect(Collectors.toList());
        }
        return urls;
    }

    private void parseIpList(List<String> ips) {
        List<URL> urlList = toUrlsWithoutEmpty(CONSUMER_URL, ips);
        final List<String> ipListTemp = urlList.stream()
            // 1. 查询过滤后的url中的address信息
            .map(url -> url.getAddress())
            // 2. 通过截取address, 获取address中的IP信息
            .map(endpoint -> endpoint.split(":")[0])
            // 3. 收集所有的 ip 信息
            .collect(Collectors.toList());
        this.ipList = ipListTemp;
    }


}
