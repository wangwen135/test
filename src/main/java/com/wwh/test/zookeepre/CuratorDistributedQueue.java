package com.wwh.test.zookeepre;

import java.util.UUID;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.framework.recipes.queue.DistributedQueue;
import org.apache.curator.framework.recipes.queue.QueueBuilder;
import org.apache.curator.framework.recipes.queue.QueueConsumer;
import org.apache.curator.framework.recipes.queue.QueueSerializer;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

public class CuratorDistributedQueue {

//    private static final String PATH = "/wwh/test/curator/queue";
    private static final String PATH = "/wwh/test/curator/node/node1/queue";
    private static final String zookeeperConnectionString = "192.168.1.213:2181,192.168.1.214:2181";

    public static void main(String[] args) {
        CuratorFramework client = null;
        DistributedQueue<String> queue = null;
        try {
            client = CuratorFrameworkFactory.newClient(zookeeperConnectionString, new ExponentialBackoffRetry(1000, 3));

            client.getCuratorListenable().addListener(new CuratorListener() {
                @Override
                public void eventReceived(CuratorFramework client, CuratorEvent event) throws Exception {
                    System.out.println("CuratorEvent: " + event.getType().name());
                }
            });

            client.start();

            QueueConsumer<String> consumer = createQueueConsumer();
            QueueBuilder<String> builder = QueueBuilder.builder(client, consumer, createQueueSerializer(), PATH);

            queue = builder.buildQueue();

            queue.start();


            String uuid = UUID.randomUUID().toString();

            for (int i = 0; i < 30; i++) {
                queue.put(uuid + "----" + i);
                Thread.sleep((long) (1000 * Math.random()));
            }

            Thread.sleep(50000);

        } catch (Exception ex) {

        } finally {
            CloseableUtils.closeQuietly(queue);
            CloseableUtils.closeQuietly(client);

        }
    }

    private static QueueSerializer<String> createQueueSerializer() {
        return new QueueSerializer<String>() {

            @Override
            public byte[] serialize(String item) {
                return item.getBytes();
            }

            @Override
            public String deserialize(byte[] bytes) {
                return new String(bytes);
            }

        };
    }

    private static QueueConsumer<String> createQueueConsumer() {

        return new QueueConsumer<String>() {

            @Override
            public void stateChanged(CuratorFramework client, ConnectionState newState) {
                System.out.println("connection new state: " + newState.name());
            }

            @Override
            public void consumeMessage(String message) throws Exception {
                System.out.println("consume one message: " + message);
                Thread.sleep(1000);
            }

        };
    }
}
