package com.wwh.test.pool.connection;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ConnectionPool {
	/**
	 * 空闲连接
	 */
	private List<Connection> freeList = new ArrayList<Connection>();
	/**
	 * 等待线程队列，先进去出
	 */
	private final LinkedList<Object> waitQueue = new LinkedList<Object>();
	/**
	 * 最小连接数
	 */
	private int minSize = 0;
	/**
	 * 最大连接数
	 */
	private int maxSize = 32;
	/**
	 * 最大等待时间
	 */
	private int maxWaiting = 10000;
	/**
	 * 连接池是否已经初始化
	 */
	private boolean initialized = false;
	/**
	 * 连接池中的总连接数
	 */
	private volatile int totalSize = 0;
	/**
	 * 锁对象
	 */
	private Object totalSizeLock = new Object();

	/**
	 * 连接工厂
	 */
	private ConnectionFactory conncetionFactory;

	public ConnectionPool() {
	}

	public ConnectionPool(ConnectionFactory conncetionFactory) {
		this.conncetionFactory = conncetionFactory;
	}

	/**
	 * 初始化池
	 */
	private synchronized void initPool() {
		if (initialized) {
			return;
		}

		initialized = true;

		for (int i = 0; i < minSize; i++) {
			Connection conn = conncetionFactory.getConnection();
			freeList.add(conn);
			++totalSize;
		}
	}

	/**
	 * 获取连接，如果当前没有连接可用，则加入等待队列
	 */
	public Connection getConnection() {

		Connection result = internalGetConnection();

		if (result != null) {
			return result;
		} else {// 等待
			Object monitor = new Object();
			synchronized (monitor) {
				synchronized (waitQueue) {
					waitQueue.add(monitor);
				}
				try {
					monitor.wait(maxWaiting);
				} catch (InterruptedException ignore) {

				}
			}

			// 移除等待
			synchronized (waitQueue) {
				waitQueue.remove(monitor);
			}

			result = internalGetConnection();

			if (result != null) {
				return result;
			} else {
				// 抛出异常
				throw new RuntimeException("等待获取连接超时");
			}
		}
	}

	/**
	 * 获取连接，如果没有连接，则尝试增加连接池
	 */
	private Connection internalGetConnection() {
		if (!initialized) {
			initPool();
		}
		if (totalSize < minSize) {
			synchronized (totalSizeLock) {
				if (totalSize < minSize) {
					// 小于最小连接数
					++totalSize;
					return conncetionFactory.getConnection();
				}
			}
		}
		synchronized (freeList) {
			if (!freeList.isEmpty()) {
				return freeList.remove(0);
			}
		}
		if (totalSize < maxSize) {
			synchronized (totalSizeLock) {
				if (totalSize < maxSize) {
					// 当前创建的连接总数小于最大连接数
					++totalSize;
					return conncetionFactory.getConnection();
				}
			}
		}
		return null;
	}

	/**
	 * 唤醒等待的线程
	 */
	private void notifyWaitingThreads() {
		Object waitMonitor = null;
		synchronized (waitQueue) {
			if (waitQueue.size() > 0) {
				waitMonitor = waitQueue.removeFirst();
			}
		}

		if (waitMonitor != null) {
			synchronized (waitMonitor) {
				waitMonitor.notify();
			}
		}
	}

	/**
	 * 归还连接，同时唤醒等待的线程
	 */
	public void returnConnection(Connection conn) {
		synchronized (freeList) {
			freeList.add(conn);
		}
		notifyWaitingThreads();
	}

	/**
	 * 销毁连接
	 * 
	 * @param conn
	 */
	public void destroyConnection(Connection conn) {
		conn.close();
		synchronized (totalSizeLock) {
			totalSize--;
		}
		notifyWaitingThreads();
	}

	public int getMinSize() {
		return minSize;
	}

	public void setMinSize(int minSize) {
		this.minSize = minSize;
	}

	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	public int getMaxWaiting() {
		return maxWaiting;
	}

	public void setMaxWaiting(int maxWaiting) {
		this.maxWaiting = maxWaiting;
	}

	public boolean isInitialized() {
		return initialized;
	}

	public int getTotalSize() {
		return totalSize;
	}

}
