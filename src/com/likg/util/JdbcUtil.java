package com.likg.util;

import java.sql.*;

/**
 * JDBC工具类
 */
public class JdbcUtil {
	
	/**
	 * 数据库类型
	 */
	public static final String DB_ORACLE = "oracle";
	public static final String DB_MYSQL = "mysql";
	
	/**
	 * 获取数据库连接对象
	 * @param dbName
	 * @param user
	 * @param password
	 * @return
	 * @author likaige
	 * @create 2014年3月28日 下午4:00:29
	 */
	public static Connection getConnection(String dbName, String user, String password) {
		String driverName = null;
		String url = null;
		Connection conn = null;

		if (JdbcUtil.DB_ORACLE.equalsIgnoreCase(dbName)) {
			driverName = "oracle.jdbc.driver.OracleDriver";
			url = "jdbc:oracle:thin:@192.168.0.26:1521:tarena";
		} else if (JdbcUtil.DB_MYSQL.equalsIgnoreCase(dbName)) {
			driverName = "com.mysql.jdbc.Driver";
			url = "jdbc:mysql://192.168.0.29:3306/shop";
		}

		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 关闭JDBC对象
	 * @param rs ResultSet结果集对象
	 * @param stmt Statement数据库操作对象
	 * @param conn Connection数据库连接对象
	 */
	public static void close(ResultSet rs, Statement stmt, Connection conn) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			System.out.println("关闭ResultSet对象出现异常");
			e.printStackTrace();
		}
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {
			System.out.println("关闭Statement对象出现异常");
			e.printStackTrace();
		}
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("关闭Connection对象出现异常");
			e.printStackTrace();
		}
	}

	/**
	 * 获取ResultSet内容
	 * @param rs
	 * @return
	 * @author likaige
	 * @create 2014年3月28日 下午3:58:06
	 */
	public static StringBuffer getTableContent(ResultSet rs) {
		StringBuffer sb = new StringBuffer();
		try {
			ResultSetMetaData meta = rs.getMetaData();
			int cols = meta.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <= cols; i++) {
					sb.append(meta.getColumnName(i) + ":" + rs.getString(i) + "\t");
				}
				sb.append("\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sb;
	}
}
