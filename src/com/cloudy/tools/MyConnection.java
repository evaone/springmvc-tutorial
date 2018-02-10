package com.cloudy.tools;

import java.sql.Connection;
import java.sql.DriverManager;

public class MyConnection {

	public static Connection getMysqlInstance() throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn=DriverManager.getConnection("jdbc:mysql://192.168.2.60:3306/test","root","123456");
		return conn;
	}
	
	public static Connection getHiveInstance() throws Exception{
		Class.forName("org.apache.hive.jdbc.HiveDriver");
		Connection con = DriverManager.getConnection("jdbc:hive2://192.168.2.60:10000/default", "", "");	    
		return con;
	}
	
	public static void main(String[] args) {
		try {
			System.out.println("mysql:"+MyConnection.getMysqlInstance());
			//System.out.println("hive:"+MyConnection.getHiveInstance());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
