package cn.yufenghui.lession.consistent.hash.api;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author Yu Fenghui
 * @date 2021/5/19 14:51
 * @since
 */
public class ConsistentHashRouter<T extends Node> {

    private final SortedMap<Long, VirtualNode<T>> ring = new TreeMap<>();
    private final HashFunction hashFunction;


    public ConsistentHashRouter(List<T> physicalNodes, int virtualNodeCount) {
        this(physicalNodes, virtualNodeCount, new MD5Hash());
    }

    public ConsistentHashRouter(List<T> physicalNodes, int virtualNodeCount, HashFunction hashFunction) {
        if (hashFunction == null) {
            throw new NullPointerException("hash function is null");
        }

        this.hashFunction = hashFunction;
        if (physicalNodes != null) {
            for (T physicalNode : physicalNodes) {
                addNode(physicalNode, virtualNodeCount);
            }
        }
    }

    public T routeNode(String key) {
        if (ring.isEmpty()) {
            return null;
        }

        Long hashValue = hashFunction.hash(key);
        SortedMap<Long, VirtualNode<T>> tailMap = ring.tailMap(hashValue);
        Long nodeHashValue = !tailMap.isEmpty() ? tailMap.firstKey() : ring.firstKey();
        ;

        return ring.get(nodeHashValue).getPhysicalNode();
    }

    /**
     * add physic node to the hash ring with some virtual nodes
     *
     * @param physicalNode
     * @param virtualNodeCount
     */
    public void addNode(T physicalNode, int virtualNodeCount) {
        if (virtualNodeCount < 0) {
            throw new IllegalArgumentException("illegal virtual node counts: " + virtualNodeCount);
        }

        int existingReplicas = getExistingReplicas(physicalNode);
        for (int i = 0; i < virtualNodeCount; i++) {
            VirtualNode<T> vNode = new VirtualNode<>(physicalNode, i + existingReplicas);
            ring.put(hashFunction.hash(vNode.getKey()), vNode);
        }
    }

    public void removeNode(T pNode) {
        Iterator<Long> iterator = ring.keySet().iterator();
        while (iterator.hasNext()) {
            Long key = iterator.next();
            VirtualNode<T> virtualNode = ring.get(key);
            if (virtualNode.isVirtualNodeOf(pNode)) {
                iterator.remove();
            }
        }
    }

    private int getExistingReplicas(T physicalNode) {
        int replicas = 0;

        for (VirtualNode<T> vNode : ring.values()) {
            if (vNode.isVirtualNodeOf(physicalNode)) {
                replicas++;
            }
        }

        return replicas;
    }

    private static class MD5Hash implements HashFunction {
        MessageDigest instance = null;

        public MD5Hash() {
            try {
                instance = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {

            }
        }

        @Override
        public long hash(String key) {
            instance.reset();
            instance.update(key.getBytes());
            byte[] digest = instance.digest();

            long h = 0;
            for (int i = 0; i < 4; i++) {
                h <<= 8;
                h |= ((int) digest[i]) & 0xFF;
            }
            return h;
        }
    }

}
