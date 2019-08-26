package com.han.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @Description TODO
 * @Date 2019/7/20 15:43
 * @Author hanyf
 */
public class JdbcUtil {

	public static	Connection getConnection() throws SQLException {
		String url="jdbc:mysql://localhost:3306/user";

		String user="root";

		String password="tiger";

		return DriverManager.getConnection(url, user, password);
	}
}
