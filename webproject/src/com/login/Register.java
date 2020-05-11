package com.login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mytest.connectMySQL;
import mytest.loginSQL;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String name=request.getParameter("name");
		String password=request.getParameter("password");
		//2.进行数据
		try {
		    //System.out.println("r3333333"+name+password);
			connectMySQL connectSql=new connectMySQL();
			loginSQL loginSql=new loginSQL(connectSql.getConnection(),name,password);
			if(loginSql.isRegisterSuccess()){
			    PrintWriter pw=response.getWriter();
			    pw.write("rsuccess");
			    System.out.println("r1");
			}else {
				
				PrintWriter pw=response.getWriter();
			    pw.write("rfail");
			    //System.out.println("r2"+name+password);
			}
			//loginSql.destroy();
	}catch(NullPointerException e) {
		//System.out.println("ss");
	}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
