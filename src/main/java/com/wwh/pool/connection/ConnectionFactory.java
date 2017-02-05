package com.wwh.pool.connection;

public class ConnectionFactory {

	/**
	 * 获取连接
	 * @return
	 */
	Connection getConnection(){
		return new Connection();
	}
}
