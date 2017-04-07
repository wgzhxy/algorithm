package org.wanggz.algorithm.consistency.ketama;

/**
 * Created by guangzhong.wgz on 16/11/11.
 */
public class DefaultNode extends Node {

    public DefaultNode() {
    }

    public DefaultNode(String name, NodeType type) {
        this.name = name;
        this.type = type;
        this.realNode = realNode;
    }

    public DefaultNode(String name, NodeType type, Node realNode) {
        this.name = name;
        this.type = type;
        this.realNode = realNode;
    }

    public DefaultNode(String name, NodeType type, int position) {
        this.name = name;
        this.type = type;
        this.realIndex = position;
    }

    public DefaultNode(String name, NodeType type, int position, Node realNode) {
        this.name = name;
        this.type = type;
        this.realNode = realNode;
        this.realIndex = position;
    }
}
