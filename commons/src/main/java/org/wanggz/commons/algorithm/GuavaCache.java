package org.wanggz.commons.algorithm;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by guangzhong.wgz on 17/4/11.
 */
public class GuavaCache {

    final static Cache<String, Object> cacheOne = CacheBuilder.newBuilder().refreshAfterWrite(3, TimeUnit.SECONDS)// 给定时间内没有被读/写访问，则回收。
            .expireAfterAccess(5, TimeUnit.MINUTES)// 缓存过期时间和redis缓存时长一样
            .maximumSize(1000).// 设置缓存个数
            build(new CacheLoader<String, Object>() {
        @Override
        /** 当本地缓存命没有中时，调用load方法获取结果并将结果缓存 **/
        public Object load(String appKey) throws Exception {
            return appKey;
        }
    });

    final static Cache<String, String> cacheTwo = CacheBuilder.newBuilder()
            //设置cache的初始大小为10，要合理设置该值
            .initialCapacity(10)
            //设置并发数为5，即同一时间最多只能有5个线程往cache执行写入操作
            .concurrencyLevel(5)
            //设置cache中的数据在写入之后的存活时间为10秒
            .expireAfterWrite(2, TimeUnit.SECONDS)
            //构建cache实例
            .build();

    final static ExecutorService readPools = Executors.newSingleThreadExecutor();
    final static ExecutorService writePools = Executors.newSingleThreadExecutor();

    public static void main(String args[]) {

        final String key = "test";

        cacheTwo.put(key, "111111111222222333");

        readPools.execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                        System.out.println(cacheTwo.getIfPresent(key));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        writePools.execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if(cacheTwo.getIfPresent(key) == null) {
                        cacheTwo.put(key, Math.random() + "");
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        readPools.shutdown();
    }
}
