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
		//����sql��� ?��ʾһ����̬���������
		String sql="insert into usermanage(user,password) values(?,?)";
		try {
			//����sql������ͨ�����Ӷ���ķ���������sql������
			PreparedStatement ps=connection.prepareStatement(sql);
			//ע�붯̬������ͨ��sql�������set����������ע��
			//��һ��������ʾռλ����λ�ã��ڶ���������ʾҪ�������ֵ
			ps.setString(1, user);
			ps.setString(2, password);
			//ͨ��sql������ķ�����ִ��sql���
			ps.executeUpdate();
			
			//�ͷ���Դ
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
