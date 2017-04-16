package org.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;


public class InsertDataIntoPracticeTable {

	public static void main(String[] args) {
		String query="";
		PreparedStatement ps=null;
		int flag=0;
		try{
			Connection con=DBConnect.getConnection();
			query="INSERT INTO circle VALUES(3,'Quad circle')";
			ps=con.prepareStatement(query);
			flag=ps.executeUpdate();
					
		}catch(Exception e){
			System.out.println("In a custom Exception");
			e.printStackTrace();
		}
		if(flag!=0){
			System.out.println("Rows inserted : "+flag);
		}
	}
}
