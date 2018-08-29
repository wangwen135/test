package com.wwh.test.zookeepre;

import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.nodes.PersistentEphemeralNode;
import org.apache.curator.framework.recipes.nodes.PersistentEphemeralNode.Mode;
import org.apache.curator.framework.recipes.nodes.PersistentNode;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.CreateMode;

public class PersistentEphemeralNodeExample {
    private static final String PATH = "/wwh/test/curator/example/ephemeralNode";
    private static final String PATH2 = "/wwh/test/curator/example/node";

    public static void main(String[] args) throws Exception {
        String zookeeperConnectionString = "192.168.1.213:2181,192.168.1.214:2181";
        CuratorFramework client = null;
        PersistentNode node = null;
        try {
            client = CuratorFrameworkFactory.newClient(zookeeperConnectionString, new ExponentialBackoffRetry(1000, 3));

            client.getConnectionStateListenable().addListener(new ConnectionStateListener() {

                @Override
                public void stateChanged(CuratorFramework client, ConnectionState newState) {
                    System.out.println("client state:" + newState.name());

                }
            });

            client.start();

            node = new PersistentNode(client, CreateMode.EPHEMERAL, true, PATH, "test".getBytes());
            node.start();

            node.waitForInitialCreate(3, TimeUnit.SECONDS);

            String actualPath = node.getActualPath();

            System.out.println("node " + actualPath + " value: " + new String(client.getData().forPath(actualPath)));

            client.create().forPath(PATH2, "persistent node".getBytes());

            System.out.println("node " + PATH2 + " value: " + new String(client.getData().forPath(PATH2)));

            // 怎么端口连接，重连
            // client.getZookeeperClient().getZooKeeper().close();
            // KillSession.kill(client.getZookeeperClient().getZooKeeper(), server.getConnectString());

            // client.getZookeeperClient().getZooKeeper().

            System.out.println("node " + actualPath + " doesn't exist: " + (client.checkExists().forPath(actualPath) == null));
            System.out.println("node " + PATH2 + " value: " + new String(client.getData().forPath(PATH2)));

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            CloseableUtils.closeQuietly(node);
            CloseableUtils.closeQuietly(client);
        }
    }

}
