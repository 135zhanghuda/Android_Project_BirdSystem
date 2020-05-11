package mytest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class loginSQL {
	Connection connection;
	String user;
	String passwords;
	//int power=0;
	String personal;
	public loginSQL(Connection connection,String username,String password) {
		// TODO Auto-generated constructor stub
		this.connection=connection;
		user=username;
		passwords=password;
		personal=user+"1";
		
	}
	public loginSQL(Connection connection,String username,String password,String power) {
		// TODO Auto-generated constructor stub
		this.connection=connection;
		user=username;
		passwords=password;
		//this.power=power;
		personal=user+"1";
		
	}
	
	public boolean isRegisterSuccess() {
		String loginSql="SELECT * FROM users where username =?;";
		try {
			//����sql������ͨ�����Ӷ���ķ���������sql������
			PreparedStatement ps=connection.prepareStatement(loginSql);
			//ע�붯̬������ͨ��sql�������set����������ע��
			//��һ��������ʾռλ����λ�ã��ڶ���������ʾҪ�������ֵ
			ps.setString(1, user);
			//ͨ��sql������ķ�����ִ��sql���
			//String passwordSql="";
			ResultSet resultSet =ps.executeQuery(); 
			if(resultSet.next()) {
					return false;
			}else {
			String registerSql="insert into  users VALUES(?,?,?,?);";
			PreparedStatement ps1=connection.prepareStatement(registerSql);
			ps1.setString(1, user);
			ps1.setString(2, passwords);
			ps1.setInt(3, 1);
			ps1.setString(4, personal);
			ps1.executeUpdate(); 
			//System.out.println("aaa");
			return true;
			}
			
	    }catch(SQLException e) {
		e.printStackTrace();}
		return true;
	}
	
	
	public int isdelete() {
		
		String sql="DELETE FROM users where username =?;";
		try {
			//����sql������ͨ�����Ӷ���ķ���������sql������
			PreparedStatement ps=connection.prepareStatement(sql);
			//ע�붯̬������ͨ��sql�������set����������ע��
			//��һ��������ʾռλ����λ�ã��ڶ���������ʾҪ�������ֵ
			ps.setString(1, user);
			//ͨ��sql������ķ�����ִ��sql���
			//String passwordSql="";
			int result =ps.executeUpdate(); 
			if(result==0) {
				return 0;
			}
			else
				return 1;
				//System.out.println(result);
			//�ͷ���Դ
			//connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return 0;
	}
	
	
	
	public void destroy() {
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}
	
	
	public int isloginsuccess() {
		//����sql��� ?��ʾһ����̬���������
		String sql="SELECT * FROM users where username =?;";
		try {
			//����sql������ͨ�����Ӷ���ķ���������sql������
			PreparedStatement ps=connection.prepareStatement(sql);
			//ע�붯̬������ͨ��sql�������set����������ע��
			//��һ��������ʾռλ����λ�ã��ڶ���������ʾҪ�������ֵ
			ps.setString(1, user);
			//ͨ��sql������ķ�����ִ��sql���
			//String passwordSql="";
			ResultSet resultSet =ps.executeQuery(); 
			if(resultSet.next()) {
				if(passwords.equals(resultSet.getString("password"))) {
					if(resultSet.getInt("power")==1) {
						return 1;
					}
					return 0;
				}
				else 
					return 2;
			}
			
			//�ͷ���Դ
			//connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 2;
		
	}

}
