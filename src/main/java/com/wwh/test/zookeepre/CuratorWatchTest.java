package com.wwh.test.zookeepre;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.ACLProvider;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooDefs.Perms;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;

public class CuratorWatchTest {

    public static void main(String[] args) throws Exception {
        CuratorFramework client = getClient();
        // setListenterOne(client);

        // setListenterTwo(client);

        // testPathChildrenCache(client);

        testNodeCacheListenable(client);

        Thread.sleep(Long.MAX_VALUE);
    }

    private static CuratorFramework getClient() {
        // zk 地址
        String connectString = "192.168.1.213:2181";
        // 连接时间 和重试次数
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(connectString, retryPolicy);
        client.start();
        return client;
    }

    /**
     * 监听子节点的变化，只监听一层
     * 
     * @param client
     * @throws Exception
     */
    public static void testPathChildrenCache(CuratorFramework client) throws Exception {
        PathChildrenCache childrenCache = new PathChildrenCache(client, "/wwh/test/curator/two", true);
        // boolean cacheData 如果为 true 则Event中可以获取到节点的数据内容

        childrenCache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                switch (event.getType()) {
                case CHILD_ADDED:
                    System.out.println("CHILD_ADDED: " + event.getData().getPath());
                    String s = new String(event.getData().getData());
                    System.out.println(s);
                    break;
                case CHILD_REMOVED:
                    System.out.println("CHILD_REMOVED: " + event.getData().getPath());
                    break;
                case CHILD_UPDATED:
                    System.out.println("CHILD_UPDATED: " + event.getData().getPath());
                    String s2 = new String(event.getData().getData());
                    System.out.println(s2);
                    break;
                default:
                    break;
                }
            }
        });

        childrenCache.start(StartMode.POST_INITIALIZED_EVENT);
        System.out.println("开始监听。。。。");
    }

    /**
     * <pre>
     * zookeeper 的监听器
     *  监听单个节点的变化，监听数据的修改，删除---一次性监听 </per>
     * </pre>
     * 
     * @param client
     * @throws Exception
     */
    private static void setListenterOne(CuratorFramework client) throws Exception {
        // 注册观察者，当节点变动时触发
        byte[] data = client.getData().usingWatcher(new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("获取 two 节点 监听器 : " + event);
            }
        }).forPath("/wwh/test/curator/two");
        System.out.println("two 节点数据: " + new String(data));
    }

    /**
     * 监听 错误 和 background 事件 Receives notifications about errors and background events
     */
    private static void setListenterTwo(CuratorFramework client) throws Exception {

        ExecutorService pool = Executors.newCachedThreadPool();

        CuratorListener listener = new CuratorListener() {
            @Override
            public void eventReceived(CuratorFramework client, CuratorEvent event) throws Exception {
                System.out.println("监听器  : " + event.toString());
            }
        };
        client.getCuratorListenable().addListener(listener, pool);

        Thread.sleep(1000);

        client.getData().inBackground().forPath("/wwh/test/curator/two");
        client.getData().inBackground().forPath("/wwh/test/curator");
        client.getData().inBackground().forPath("/wwh/test/curator/two");
        client.getData().inBackground().forPath("/wwh/test/curator/two");

    }

    // 2.Node Cache 监控本节点的变化情况 连接 目录 是否压缩
    // 监听本节点的变化 节点可以进行修改操作 删除节点后会再次创建(空节点)
    public static void testNodeCacheListenable(CuratorFramework client) throws Exception {
        // 设置节点的cache
        final NodeCache nodeCache = new NodeCache(client, "/wwh/test/curator/two/wwh", false);
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                System.out.println("the test node is change and result is :");
                System.out.println("path : " + nodeCache.getCurrentData().getPath());
                System.out.println("data : " + new String(nodeCache.getCurrentData().getData()));
                System.out.println("stat : " + nodeCache.getCurrentData().getStat());
            }
        });
        nodeCache.start();
    }

    // 3.Tree Cache
    // 监控 指定节点和节点下的所有的节点的变化--无限监听 可以进行本节点的删除(不在创建)
    private static void setListenterThreeThree(CuratorFramework client) throws Exception {
        ExecutorService pool = Executors.newCachedThreadPool();
        // 设置节点的cache
        TreeCache treeCache = new TreeCache(client, "/test");
        // 设置监听器和处理过程
        treeCache.getListenable().addListener(new TreeCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
                ChildData data = event.getData();
                if (data != null) {
                    switch (event.getType()) {
                    case NODE_ADDED:
                        System.out.println("NODE_ADDED : " + data.getPath() + "  数据:" + new String(data.getData()));
                        break;
                    case NODE_REMOVED:
                        System.out.println("NODE_REMOVED : " + data.getPath() + "  数据:" + new String(data.getData()));
                        break;
                    case NODE_UPDATED:
                        System.out.println("NODE_UPDATED : " + data.getPath() + "  数据:" + new String(data.getData()));
                        break;

                    default:
                        break;
                    }
                } else {
                    System.out.println("data is null : " + event.getType());
                }
            }
        });
        // 开始监听
        treeCache.start();

    }

}
