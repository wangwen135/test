package com.ww.jdbcTest;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 测试JDBC
 * 
 * @author 313921
 *
 */
public class JDBCTest {

	// 驱动程序就是之前在classpath中配置的jdbc的驱动程序jar中
	public static final String DRIVE = "oracle.jdbc.driver.OracleDriver";
	/**
	 * 连接地址，各个厂商提供单独记住 jdbc:oracle:thin:@localhost:1521:ORCL localhost 是ip地址。
	 */
	public static final String URL = "jdbc:oracle:thin:@10.0.76.110:1521:cmspst";
	/**
	 * 用户 密码
	 */
	public static final String DBUSER = "cmsp";
	public static final String PASSWORD = "oracle";

	public static void main(String[] args) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;

		/**
		 * <pre>
		 * oracle.jdbc.driver.OracleDriver 类中有一个static块，类加载时会将自己注册到DriverManager中
		 * defaultDriver = new OracleDriver();
		 * DriverManager.registerDriver(defaultDriver);
		 * </pre>
		 */
		Class.forName(DRIVE);

		Enumeration<Driver> erd = DriverManager.getDrivers();
		/**
		 * sun.jdbc.odbc.JdbcOdbcDriver@8813f2
		 * oracle.jdbc.driver.OracleDriver@1d58aae
		 */
		for (; erd.hasMoreElements();) {
			Driver driver = erd.nextElement();
			
			System.out.println(driver);

		}

		conn = DriverManager.getConnection(URL, DBUSER, PASSWORD);

		stmt = conn.createStatement();

		// 执行SQL语句来查询数据库

		result = stmt.executeQuery("select * from AA_T1");

		ResultSetMetaData rsmd = result.getMetaData();

		// 获得结果集列数
		int columnCount = rsmd.getColumnCount();
		while (result.next()) {// 判断有没有下一行

			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 1; i <= columnCount; i++) {
				map.put(rsmd.getColumnLabel(i), result.getObject(i));
			}

			System.out.println(map);

		}

		result.close();// 数据库先开后关
		stmt.close();
		conn.close();// 关闭数据库
	}
}
