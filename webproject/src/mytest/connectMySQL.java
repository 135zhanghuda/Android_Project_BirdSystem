package mytest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connectMySQL {
	
	static String url="jdbc:mysql://localhost:3306/database_android?useUnicode=true&characterEncoding=utf8";	
	static String usename="root";
	static String password="zhanghuda";
	
	//connection�ṩip��ַ���˿ںš��û��������롣
	static Connection connection;
	
	//��̬��ʼ�����ʼ��connction����
	static {
		//����������
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		//��ȡ���ݿ�����
		try {
			connection=DriverManager.getConnection(url, usename, password);
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
		
		//�ṩһ����̬�����������ݿ����
		public static Connection getConnection() {
			return connection;
			
		}

		
}
	
	
	
	
	
	
	
	
	
	
	
	
	 /*public void connectToMySql() {
		 try { 
			   Class.forName("com.mysql.jdbc.Driver");   //����MYSQL JDBC��������  
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
			      //����URLΪ  jdbc:mysql//��������ַ/���ݿ��� �������2�������ֱ��ǵ�½�û��������� 
			  
			   System.out.println("Success connect Mysql server!"); 
			   java.sql.Statement stmt =  connect.createStatement(); 
			  /*ResultSet rs = stmt.executeQuery("select * from user"); 
			                               //user Ϊ�������� 
			   while (rs.next()) { 
			   System.out.println(rs.getString("name")); 
			  } */
			
			 /* catch (Exception e) { 
			   System.out.print("get data error!"); 
			   e.printStackTrace(); 
			  } */

