package org.wanggz.algorithm.consistency.ketama;

/**
 * 实验节点
 * Created by guangzhong.wgz on 16/11/10.
 */
public class ClientNode extends Node {

    private String ip;

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }
}
