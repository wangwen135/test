package com.wwh.test.zookeepre;

import java.io.UnsupportedEncodingException;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

public class CuratorCRUD {

    public static void main(String[] args) throws Exception {

        String zookeeperConnectionString = "192.168.1.213:2181,192.168.1.214:2181";
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(zookeeperConnectionString, retryPolicy);
        client.start();
        System.out.println("客户端启动。。。。");

        // curd(client);
        createEphemeralPath(client);

        System.out.println("主线程需要在这里等着");
        try {
            Thread.sleep(1000000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        client.close();
        System.out.println("客户端关闭。。。。");

    }

    /**
     * 临时节点下不能再创建临时节点
     * 
     * @param client
     * @throws Exception
     * @throws UnsupportedEncodingException
     */
    private static void createEphemeralPath(CuratorFramework client) throws Exception, UnsupportedEncodingException {
        String path = "/wwh/test/curator/test/ephe1";

        System.out.println("创建第一层临时节点");
        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path, "第一层".getBytes("UTF-8"));

        String path2 = path + "/ephe2";

        System.out.println("创建第二层临时节点");
        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path2, "second".getBytes("UTF-8"));

        System.out.println("获取数据1：");
        byte[] data = client.getData().forPath(path);
        System.out.println(new String(data, "UTF-8"));

        System.out.println("获取数据2：");
        byte[] data2 = client.getData().forPath(path2);
        System.out.println(new String(data2, "UTF-8"));
    }

    private static void curd(CuratorFramework client) throws Exception, UnsupportedEncodingException {
        String path = "/wwh/test/curator/test/ephemeral";

        System.out.println("设置数据 hello");
        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path, "hello".getBytes("UTF-8"));

        System.out.println("获取数据：");
        byte[] data = client.getData().forPath(path);
        System.out.println(new String(data, "UTF-8"));

        System.out.println("更新数据为：hello world");
        client.setData().forPath(path, "hello world ".getBytes("UTF-8"));

        System.out.println("再次获取数据：");
        byte[] data2 = client.getData().forPath(path);
        System.out.println(new String(data2, "UTF-8"));
    }
}
