package javaBean;

import java.sql.*;

public class connectMysql {

	public static Connection connectMysql(){
		
		
		Connection conn = null;
		try {

			
			String jdbcUrl = "jdbc:mysql://localhost:3306/buzzcloud";
			String dbId = "buzzcloud";
			String dbPasswd = "rksk!@12";
					
			
			Class.forName( "com.mysql.jdbc.Driver");
			
			conn = DriverManager.getConnection(jdbcUrl, dbId, dbPasswd);
			
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("db접속에 실패하였습니다\n" + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(conn == null){
			System.out.println("db생성실패!");
			return null;
			
		}
		return conn;
		
		
		
	}
	
}
