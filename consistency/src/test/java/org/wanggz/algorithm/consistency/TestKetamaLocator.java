package org.wanggz.algorithm.consistency;

import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.wanggz.algorithm.consistency.ketama.KetamaLocator;
import org.wanggz.algorithm.consistency.ketama.Node;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by guangzhong.wgz on 16/11/11.
 */
public class TestKetamaLocator {

    @Test
    public void testBuildCycleNodes() {
        long begin = System.currentTimeMillis();
        final KetamaLocator ketama = new KetamaLocator();

        // 初始化环上真实节点
        List<String> serverList = Lists.newArrayList();

        serverList.add("192.168.0.1:8080");
        serverList.add("192.168.0.2:8080");
        serverList.add("192.168.1.1:8080");
        serverList.add("192.168.1.2:8080");
        serverList.add("192.168.2.2:8080");
        serverList.add("192.168.3.1:8080");
        serverList.add("192.168.3.2:8080");
        serverList.add("192.168.4.1:8080");
        serverList.add("192.168.4.2:8080");
        serverList.add("192.168.5.1:8080");
        serverList.add("192.168.5.2:8080");

        serverList.add("192.168.7.1:8080");
        serverList.add("192.168.7.2:8080");
        serverList.add("192.168.8.1:8080");
        serverList.add("192.168.9.2:8080");
        serverList.add("192.168.9.2:8080");
        serverList.add("192.168.10.1:8080");
        serverList.add("192.168.10.2:8080");
        serverList.add("192.168.11.1:8080");
        serverList.add("192.168.11.2:8080");
        serverList.add("192.168.12.1:8080");
        serverList.add("192.168.12.2:8080");

        ketama.buildCycleNodes(serverList);

        // 模拟命中
        ExecutorService es = Executors.newCachedThreadPool();
        final CountDownLatch cdl = new CountDownLatch(1000);
        // 1000个线程
        for (int j = 0; j < 1000; j++) {
            es.execute(new Runnable() {

                @Override
                public void run() {
                    // Random rd = new Random(1100);
                    for (int k = 0; k < 10000; k++) {
                        ketama.getCycleNode(String.valueOf(Math.random())).incTimes();
                    }
                    cdl.countDown();
                }
            });
        }

        // 等待所有线程结束
        try {
            cdl.await();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("========执行时间===========: time : " + (end - begin));
        for (Node node : ketama.getRealNodes()) {
            System.out.println("node: " + node.getName() + " 占比:  " + (node.getHits().get() * 1.0) / (1000 * 10000) * 100 + "%");
        }
        es.shutdown();
    }

    @Test
    public void testRemoveRecyleNodes() {
        List<String> serverList = Lists.newArrayList();
        serverList.add("192.168.0.1:8080");
        serverList.add("192.168.0.2:8080");
        serverList.add("192.168.1.1:8080");
        serverList.add("192.168.1.2:8080");
        serverList.add("192.168.2.2:8080");
        serverList.add("192.168.3.1:8080");

        final KetamaLocator ketama = new KetamaLocator();
        ketama.buildCycleNodes(serverList);

        Node node = ketama.getRealNode(serverList.get(0));
        ketama.removeCycleNodeByRealPoint(node);

        //检查是否删除真实结点
        boolean check = true;
        for (Node tpNode : ketama.getRealNodes()) {
            if (serverList.get(0).equals(tpNode.getName())) {
                check = false;
                break;
            }
        }
        Assert.assertTrue(check);
        //检查是否删除虚节点
        for (Node virtualNode : ketama.getCycleNodes().values()) {
            if (serverList.get(0).equals(virtualNode.getRealNode().getName())) {
                check = false;
                break;
            }
        }
        Assert.assertTrue(check);
    }

}
