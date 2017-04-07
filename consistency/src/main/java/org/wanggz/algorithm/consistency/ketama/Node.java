package org.wanggz.algorithm.consistency.ketama;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 节点类型
 * Created by guangzhong.wgz on 16/11/10.
 */
public abstract class Node {

    public static final String VIRTUAL_PREFIX = "virtual";

    /**
     * 节点名称
     */
    protected String name;
    /**
     * 节点类型, V虚节点，R实节点
     */
    protected NodeType type;
    /**
     * 标记真实节点的位置
     */
    protected int realIndex = 0;
    /**
     * 命中次数
     */
    protected AtomicLong hits = new AtomicLong(0);
    /**
     * 真实节点
     */
    protected Node realNode;

    /**
     * 节点调用次数记数
     */
    public void incTimes() {
        if (this.getType() == NodeType.real) {
            hits.incrementAndGet();
        } else {
            realNode.hits.incrementAndGet();
        }
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected void setType(NodeType type) {
        this.type = type;
    }

    protected void setRealNode(Node realNode) {
        this.realNode = realNode;
    }

    protected String getName() {
        return name;
    }

    protected NodeType getType() {
        return type;
    }

    protected void setHits(AtomicLong hits) {
        this.hits = hits;
    }

    protected Node getRealNode() {
        return realNode;
    }

    protected AtomicLong getHits() {
        return hits;
    }

    public enum NodeType {
        real, virtual;
    }
}
