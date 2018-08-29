package com.wwh.test.zookeepre;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class CuratorLeaderTest {

    public static void main(String[] args) {

        String zookeeperConnectionString = "192.168.1.213:2181,192.168.1.214:2181";
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(zookeeperConnectionString, retryPolicy);
        client.start();
        System.out.println("客户端启动。。。。");
        System.out.println("开始选择leader");

        LeaderSelectorListener listener = new LeaderSelectorListenerAdapter() {
            public void takeLeadership(CuratorFramework client) throws Exception {
                // this callback will get called when you are the leader
                // do whatever leader work you need to and only exit
                // this method when you want to relinquish leadership

                System.out.println("4444这个当选了leader ");

                Thread.sleep(10000);

                System.out.println("放弃Leader");
            }
        };

        LeaderSelector selector = new LeaderSelector(client, "/wwh/test/curator", listener);
        selector.autoRequeue(); // not required, but this is behavior that you will probably expect
        //autoRequeue()方法使放弃Leadership的Listener有机会重新获得Leadership，如果不设置的话放弃了的Listener是不会再变成Leader的。
        selector.start();

        System.out.println("主线程需要在这里等着");
        try {
            Thread.sleep(1000000000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        selector.close();
        

        client.close();
        System.out.println("客户端关闭。。。。");

    }
}
