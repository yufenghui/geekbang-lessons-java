package cn.yufenghui.lession.consistent.hash;

import cn.yufenghui.lession.consistent.hash.api.ConsistentHashRouter;
import cn.yufenghui.lession.consistent.hash.api.Node;

import java.util.Arrays;

/**
 * @author Yu Fenghui
 * @date 2021/5/19 15:08
 * @since
 */
public class MyServerNode implements Node {

    private final String region;
    private final String ip;
    private final int port;

    public MyServerNode(String region, String ip, int port) {
        this.region = region;
        this.ip = ip;
        this.port = port;
    }

    @Override
    public String getKey() {
        return region + "-" + ip + ":" + port;
    }

    @Override
    public String toString() {
        return getKey();
    }

    private static void goRoute(ConsistentHashRouter<MyServerNode> router, String... requestIps) {
        for (String ip : requestIps) {
            System.out.println(ip + " is route to " + router.routeNode(ip));
        }
    }

    public static void main(String[] args) {
        //initialize 4 service node
        MyServerNode node1 = new MyServerNode("IDC1", "127.0.0.1", 8080);
        MyServerNode node2 = new MyServerNode("IDC1", "127.0.0.1", 8081);
        MyServerNode node3 = new MyServerNode("IDC1", "127.0.0.1", 8082);
        MyServerNode node4 = new MyServerNode("IDC1", "127.0.0.1", 8084);

        //hash them to hash ring
        ConsistentHashRouter<MyServerNode> consistentHashRouter = new ConsistentHashRouter<>(Arrays.asList(node1, node2, node3, node4), 10);

        //we have 5 requester ip, we are trying them to route to one service node
        String requestIP1 = "192.168.0.1";
        String requestIP2 = "192.168.0.2";
        String requestIP3 = "192.168.0.3";
        String requestIP4 = "192.168.0.4";
        String requestIP5 = "192.168.0.5";

        goRoute(consistentHashRouter, requestIP1, requestIP2, requestIP3, requestIP4, requestIP5);

        MyServerNode node5 = new MyServerNode("IDC2", "127.0.0.1", 8080);
        System.out.println("-------------putting new node online " + node5.getKey() + "------------");
        consistentHashRouter.addNode(node5, 10);

        goRoute(consistentHashRouter, requestIP1, requestIP2, requestIP3, requestIP4, requestIP5);

        consistentHashRouter.removeNode(node3);
        System.out.println("-------------remove node online " + node3.getKey() + "------------");
        goRoute(consistentHashRouter, requestIP1, requestIP2, requestIP3, requestIP4, requestIP5);

    }

}
