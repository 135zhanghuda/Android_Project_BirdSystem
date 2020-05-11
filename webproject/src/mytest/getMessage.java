package mytest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class getMessage {
	Connection connection;
	String user="te";
	String password="12";
	public getMessage(Connection connection) {
		// TODO Auto-generated constructor stub
		this.connection=connection;
		
	}
	public void runSql() {
		//定义sql语句 ?表示一个动态参数的入参
		String sql="insert into usermanage(user,password) values(?,?)";
		try {
			//创建sql语句对象（通过链接对象的方法来创建sql语句对象
			PreparedStatement ps=connection.prepareStatement(sql);
			//注入动态参数，通过sql语句对象的set方法来进行注入
			//第一个参数表示占位符的位置，第二个参数表示要插入的数值
			ps.setString(1, user);
			ps.setString(2, password);
			//通过sql语句对象的方法来执行sql语句
			ps.executeUpdate();
			
			//释放资源
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
