package org.wanggz.algorithm.consistency.ketama;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.wanggz.algorithm.consistency.hash.GuavaAlgorithms;
import org.wanggz.algorithm.consistency.hash.HashAlgorithms;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * ketama 算法
 * Created by guangzhong.wgz on 16/11/10.
 */
public class KetamaLocator {

    /**
     * 默认虚节点数量
     */
    private final int DEFAULT_VIRTUAL_NUM = 160;
    /**
     * 0－2^32环集合
     */
    private final TreeMap<Long, Node> cycleNodes = Maps.newTreeMap();
    /**
     * 0－2^32环集合
     */
    private final List<Node> realNodes = Lists.newCopyOnWriteArrayList();

    /**
     * 构建0-2^32环, 默认加入拟节点为160个
     *
     * @param points 环上真实节点集合
     * @return treeMap 有序环
     */
    public TreeMap<Long, Node> buildCycleNodes(List<String> points) {
        return buildCycleNodes(points, 0);
    }

    /**
     * 构建0-2^32环
     *
     * @param points
     * @param virtual
     * @return treeMap 有序环
     */
    public TreeMap<Long, Node> buildCycleNodes(List<String> points, int virtual) {

        //初始化虚节点
        if (virtual == 0) {
            virtual = DEFAULT_VIRTUAL_NUM;
        }
        //初始化环上节点
        for (int i = 0; i < points.size(); i++) {
            Node node = new DefaultNode(points.get(i), Node.NodeType.real, i + 1);
            realNodes.add(node);
            for (int j = 0; j < virtual; j++) {
                String virtualName = getVirtualPoint(node.getName(), i, j);
                cycleNodes.put(hash(virtualName), new DefaultNode(virtualName, Node.NodeType.virtual, node));
            }
        }
        return cycleNodes;
    }

    /**
     * 删除环上真实节点
     *
     * @param realPoint
     */
    public synchronized void removeCycleNodeByRealPoint(Node realPoint) {
        //节点检查
        if (realPoint == null && "".equals(realPoint)) {
            throw new NullPointerException("realPoint is null!");
        }
        if (realNodes == null || realNodes.size() <= 0) {
            throw new NullPointerException("realNodes is null!");
        }
        //移除虚节点
        for (int i = 0; i < realPoint.realIndex; i++) {
            for (int j = 0; j < DEFAULT_VIRTUAL_NUM; j++) {
                Long nodeKey = hash(getVirtualPoint(realPoint.getName(), i, j));
                cycleNodes.remove(nodeKey);
            }
        }
        //移动实节点
        realNodes.remove(realPoint);
    }

    /**
     * 返回环上节点
     *
     * @param key 随机键值
     * @return
     */
    public Node getCycleNode(String key) {
        Long hashedKey = hash(key);
        Map.Entry<Long, Node> entry = cycleNodes.ceilingEntry(hashedKey);
        if (entry == null) {
            return cycleNodes.firstEntry().getValue();
        }
        return (Node) entry.getValue();
    }

    /**
     * 返回真实节点
     *
     * @param nodeName 真实节点
     * @return
     */
    public Node getRealNode(String nodeName) {
        for (Node node : realNodes) {
            if (node.getName().equals(nodeName)) {
                return node;
            }
        }
        return null;
    }

    /**
     * 返回真实节点集合
     *
     * @return
     */
    public List<Node> getRealNodes() {
        return realNodes;
    }

    /**
     * @return 获取环上节点
     */
    public TreeMap<Long, Node> getCycleNodes() {
        return cycleNodes;
    }

    /**
     * 返回虚节点名称
     *
     * @param point 真实节点名称
     * @param i     真实节点位置
     * @param j     拟节点位置
     * @return
     */
    private String getVirtualPoint(String point, int i, int j) {
        return point + ":" + Node.VIRTUAL_PREFIX + ":00:" + i + ":" + j;
    }

    /**
     * 一致性哈希函数
     *
     * @param key  键值
     * @param time 时间
     * @return
     */
    private long hash(String key, int time) {
        return Math.abs((long) HashAlgorithms.ketamaHash(GuavaAlgorithms.md5(key), time));
    }

    /**
     * 一致性哈希函数
     *
     * @param key 键值
     * @return
     */
    private long hash(String key) {
        //return Math.abs((long) HashAlgorithms.ketamaHash(GuavaAlgorithms.md5(key), 0));
        //return GuavaAlgorithms.murMurHash32(key.getBytes());
        //return (long) HashAlgorithms.FNVHash1(key);
        //return (long) HashAlgorithms.mixHash(key);
        return Math.abs(HashAlgorithms.murmurHash(key.getBytes())); //性能，效果比较好，低碰撞，分散性好，速度快
    }
}
