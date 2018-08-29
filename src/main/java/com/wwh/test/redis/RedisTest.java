package com.wwh.test.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisTest {

    public static void demo1() {
        Jedis jedis = new Jedis("192.168.1.213", 6379);

        jedis.set("eclipse", "oxygen");

        System.out.println(jedis.get("eclipse"));

        System.out.println(jedis.get("name"));

        jedis.close();

    }

    public static void main(String[] args) {
        // 连接池配置对象
        JedisPoolConfig config = new JedisPoolConfig();
        // 最大连接数
        config.setMaxTotal(30);
        // 最大空闲数
        config.setMaxIdle(10);

        JedisPool jedisPool = new JedisPool(config, "192.168.1.213", 6379);

        Jedis jedis = null;

        try {
            // 通过连接池获取连接
            jedis = jedisPool.getResource();

            jedis.set("name2", "张三");

            System.out.println(jedis.get("name2"));
            
            jedis.expire("name2", 1);
            jedis.expire("name222", 1);
            jedis.expire("name23333", 1);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
            if (jedisPool != null) {
                jedisPool.close();
            }
        }
    }
}
