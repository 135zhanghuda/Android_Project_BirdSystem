package com.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mytest.connectMySQL;

/**
 * Servlet implementation class ChangePassword
 */
@WebServlet("/ChangePassword")
public class ChangePassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name=request.getParameter("username");
		String password=request.getParameter("password");
		 String getAllSql="update users set password=? where username=?;";
			try {
				connectMySQL connectSql=new connectMySQL();
				PreparedStatement psAll=connectSql.getConnection().prepareStatement(getAllSql);
				psAll.setString(1,password);
				psAll.setString(2,name);
				//System.out.println(img);
//				Vector<Bird> birdVector=new Vector<Bird>();
				//ResultSet resultSetAll =psAll.executeQuery(); 
				int result=psAll.executeUpdate();
				PrintWriter pw=response.getWriter();
			    pw.write("s");
			    psAll.close();
			    pw.close();
			}catch(NullPointerException e) {
			//System.out.println("ss");
				}catch(SQLException e) {
					PrintWriter pw=response.getWriter();
				    pw.write("f");
				    pw.close();
				}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
