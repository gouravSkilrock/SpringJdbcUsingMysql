package org.panwar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.panwar.model.Circle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

@Component
public class JdbcDaoImp {

	
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate ;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public DataSource getDataSource() {
		return dataSource;
	}
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate= new JdbcTemplate(dataSource);
		this.namedParameterJdbcTemplate=new NamedParameterJdbcTemplate(dataSource);
	}

	public Circle getCircle(int circleId){
		
		Connection connection=null;
		ResultSet rs=null;
		PreparedStatement ps=null;
		String query = null;
		Circle circle=null;
		
		try{
			
			connection = dataSource.getConnection();
			query = "Select * from Circle where id=?";
			ps=connection.prepareStatement(query);
			ps.setInt(1, circleId);
			rs=ps.executeQuery();
			while(rs.next()){
				circle = new Circle(circleId, rs.getString("name"));
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				rs.close();
				ps.close();
				connection.close();
			}catch(Exception e){
				
			}
		}
		return circle;
	}
	public int getCircleCount(){
		String query="SELECT COUNT(*) FROM CIRCLE"; 
		//jdbcTemplate.setDataSource(getDataSource());
		return jdbcTemplate.queryForObject(query, Integer.class);
				
	}
	
	public Circle getAllCircle(int circleId){
		String query="SELECT * from CIRCLE where id=?";
		return jdbcTemplate.queryForObject(query, new Object[]{circleId}, new CircleMapper());
	}
	
   public List<Circle> getAllCircleName(){
	   String query="SELECT * from CIRCLE";
	   return jdbcTemplate.query(query, new CircleMapper());
   }
   
   public void insertRecordInCircle(Circle circle){
	   String query ="INSERT INTO CIRCLE(ID, NAME) VALUES(?, ?)";
	   int k=jdbcTemplate.update(query, new Object[]{circle.getId(), circle.getName()});
	   if(k!=0){
		   System.out.println(k+" Record inserted");
	   }
   }
   
   public void insertRecordInCircleUsingNamedParameter(Circle circle){
	   String query ="INSERT INTO CIRCLE(ID, NAME) VALUES(:id, :name)";
	   SqlParameterSource sqlParameter =new MapSqlParameterSource("id",circle.getId()).addValue("name", circle.getName());
	   namedParameterJdbcTemplate.update(query, sqlParameter);
   }
   
   public void createNewTable(String tableName){
	   String query="CREATE TABLE "+tableName+" (ID INTEGER, NAME VARCHAR(50))";
	   jdbcTemplate.execute(query);
   }
	
	public static final class CircleMapper implements RowMapper<Circle>{

		@Override
		public Circle mapRow(ResultSet resultSet, int arg1) throws SQLException {
			Circle circle = new Circle();
			circle.setId(resultSet.getInt("ID"));
			circle.setName(resultSet.getString("NAME"));
			return circle;
		}
		
	}
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return namedParameterJdbcTemplate;
	}
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
}
