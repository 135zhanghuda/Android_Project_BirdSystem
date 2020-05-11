package mytest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connectMySQL {
	
	static String url="jdbc:mysql://localhost:3306/database_android?useUnicode=true&characterEncoding=utf8";	
	static String usename="root";
	static String password="zhanghuda";
	
	//connection提供ip地址，端口号、用户名、密码。
	static Connection connection;
	
	//静态初始化块初始化connction对象
	static {
		//加载驱动类
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		//获取数据库链接
		try {
			connection=DriverManager.getConnection(url, usename, password);
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
		
		//提供一个静态方法返回数据库对象
		public static Connection getConnection() {
			return connection;
			
		}

		
}
	
	
	
	
	
	
	
	
	
	
	
	
	 /*public void connectToMySql() {
		 try { 
			   Class.forName("com.mysql.jdbc.Driver");   //加载MYSQL JDBC驱动程序  
			   //Class.forName("org.gjt.mm.mysql.Driver"); 
			   System.out.println("Success loading Mysql Driver!"); 
			  } 
			  catch (Exception e) { 
			   System.out.print("Error loading Mysql Driver!"); 
			   e.printStackTrace(); 
			  } 
			  try { 
			   Connection connect = DriverManager.getConnection( 
			     "jdbc:mysql://localhost:3306","root","zhanghuda"); 
			      //连接URL为  jdbc:mysql//服务器地址/数据库名 ，后面的2个参数分别是登陆用户名和密码 
			  
			   System.out.println("Success connect Mysql server!"); 
			   java.sql.Statement stmt =  connect.createStatement(); 
			  /*ResultSet rs = stmt.executeQuery("select * from user"); 
			                               //user 为你表的名称 
			   while (rs.next()) { 
			   System.out.println(rs.getString("name")); 
			  } */
			
			 /* catch (Exception e) { 
			   System.out.print("get data error!"); 
			   e.printStackTrace(); 
			  } */

