package cn.yufenghui.lession.consistent.hash.api;

/**
 * @author Yu Fenghui
 * @date 2021/5/19 14:44
 * @since
 */
public class VirtualNode<T extends Node> implements Node {

    private final T physicalNode;
    private final int replicaIndex;

    public VirtualNode(T physicalNode, int replicaIndex) {
        this.physicalNode = physicalNode;
        this.replicaIndex = replicaIndex;
    }

    @Override
    public String getKey() {
        return physicalNode.getKey() + "-" + replicaIndex;
    }

    public boolean isVirtualNodeOf(T physicalNode) {
        return this.physicalNode.getKey().equals(physicalNode.getKey());
    }

    public T getPhysicalNode() {
        return this.physicalNode;
    }

}
