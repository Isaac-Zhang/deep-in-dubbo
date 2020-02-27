package com.sxzhongf.deep.in.dubbo.api.spi;

import java.sql.Driver;
import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * ServiceLoaderApplication for 验证{@link java.util.ServiceLoader}
 *
 * <span>
 *  引入实现依赖：
 *          <dependency>
 *             <groupId>mysql</groupId>
 *             <artifactId>mysql-connector-java</artifactId>
 *             <version>8.0.19</version>
 *         </dependency>
 *         <dependency>
 *             <groupId>com.oracle</groupId>
 *             <artifactId>ojdbc6</artifactId>
 *             <version>11.2.0.3</version>
 *         </dependency>
 * 输出结果：
 * driver:class com.mysql.cj.jdbc.Driver, loader:sun.misc.Launcher$AppClassLoader@18b4aac2
 * driver:class oracle.jdbc.OracleDriver, loader:sun.misc.Launcher$AppClassLoader@18b4aac2
 * Current main thread classloader: sun.misc.Launcher$AppClassLoader@18b4aac2
 * ServiceLoader was loaded by :null
 * </span>
 * <description>
 *     由于{@link ServiceLoader}是由{@link bootstrapClassloader}加载的，按照双亲委派原则，那么
 *     ServiceLoader依赖的类也应该又bootstrapClassLoader来加载，可是从结果我们可以看出，
 *     SPI实现类的jar包又是由{@link sun.misc.Launcher.AppClassLoader}来加载,这违反了双亲委派的原则，
 *     因此就需要一种方式来解决该问题，{@link ContextClassLoader}
 *
 * </description>
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/2/27
 **/
public class ServiceLoaderApplication {

    public static void main(String[] args) {

        // 获取当前ContextClassLoader,并传入service interface
        // 在java.util.ServiceLoader.LazyIterator 的next() & hasNextService方法中，查找所有的实现jar包
        // 下的META-INF -> services ->下的实现接口类
        ServiceLoader<Driver> serviceLoader = ServiceLoader.load(Driver.class);

        //
        Iterator<Driver> iterators = serviceLoader.iterator();
        while (iterators.hasNext()) {
            Driver driver = iterators.next();
            System.out.println("driver:" + driver.getClass() + ", loader:" + driver.getClass().getClassLoader());
        }

        System.out.println("Current main thread classloader: " + Thread.currentThread().getContextClassLoader());

        //
        System.out.println("ServiceLoader was loaded by :" + ServiceLoader.class.getClassLoader());
    }
}
