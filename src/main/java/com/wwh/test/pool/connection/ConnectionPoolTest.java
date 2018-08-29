package com.wwh.test.pool.connection;

import java.util.Random;

public class ConnectionPoolTest {
	/**
	 * 模拟客户端线程不断获取，释放连接的情况
	 */
	private static class PoolClient implements Runnable {

		private static int idGenerator = 0;
		private final ConnectionPool pool;
		private final String threadName;
		private Random random = new Random();

		public PoolClient(ConnectionPool pool) {
			this.pool = pool;
			threadName = "pool-client-" + (++idGenerator);
			Thread.currentThread().setName(threadName);
		}

		public void run() {

			for (int i = 0; i < 100; i++) {
				Connection conn = null;
				try {
					conn = pool.getConnection();
				} catch (Exception e) {
					System.out.println("Thread " + threadName + " 获取连接超时啦！！！！！！！！！！ ");
					continue;
				}
				System.out.println("Thread " + threadName + " 取到一个连接");
				int sleepTime = (random.nextInt(20)) * 1000;
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException ignore) {
				}

				if (sleepTime > 15000) {
					pool.destroyConnection(conn);
					System.out.println("Thread " + threadName + " 销毁一个连接");
				} else {
					pool.returnConnection(conn);
					System.out.println("Thread " + threadName + " 释放一个连接");
				}

			}
		}
	}

	public static void main(String[] args) throws Exception {

		ConnectionFactory factory = new ConnectionFactory();
		ConnectionPool pool = new ConnectionPool(factory);

		pool.setMaxSize(100);
		pool.setMinSize(10);

		//
		for (int i = 0; i < 200; i++) {
			Thread thread = new Thread(new PoolClient(pool));
			thread.start();
		}

		while (true) {
			System.out.println("池中连接数量：" + pool.getTotalSize());
			Thread.sleep(1000);
		}

	}
}
