package mytest;

import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.alibaba.fastjson.JSON;

import bean.Bird;

public class selectBird {
	
	public Bird getBird(String birdName) {
		String getAllSql="select * from birds where birdname=?;";
		try {
			connectMySQL connectSql=new connectMySQL();
			PreparedStatement psAll=connectSql.getConnection().prepareStatement(getAllSql);
			psAll.setString(1,birdName);
//			Vector<Bird> birdVector=new Vector<Bird>();
			ResultSet resultSetAll =psAll.executeQuery(); 
			while(resultSetAll.next()){
				Bird bird=new Bird(resultSetAll.getString("birdname"),
						resultSetAll.getInt("habitat"),
						resultSetAll.getInt("lifestyle"),
						resultSetAll.getInt("residence"),
						resultSetAll.getString("details"),
						resultSetAll.getString("birdurl"),
						resultSetAll.getBytes("birdimg"));
				return bird;
				
				//json½âÎöÉÏ´«
				//System.out.println(JSON.toJSONString(bird));
			}
			psAll.close();
		}catch(NullPointerException e) {
		//System.out.println("ss");
			}catch(SQLException e) {}
		return null;
	}
}
