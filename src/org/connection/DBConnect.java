package org.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnect {
	
	
	static{
		try {
				Class.forName("com.mysql.jdbc.Driver");
				System.out.println("Database Driver loaded !!!");
			} catch (ClassNotFoundException e) {
				System.out.println("Mysql Driver not loaded !!!");
				e.printStackTrace();
			}
	}
	
	
	public static Connection getConnection(){
		Connection con=null;
		
		try{
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/practice", "root", "root");
			System.out.println("Connection Succesfull Made !!!");
		}catch(Exception e){
			con=null;
			System.out.println("Connection Failed !!!");
			e.printStackTrace();
		}
		return con;
	}

}
