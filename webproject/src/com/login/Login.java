package com.login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mytest.*;
/**
 * Servlet implementxation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
//http://localhost:8081/WebProject/Login?username=admin&password=123456
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name=request.getParameter("name");
		String password=request.getParameter("password");
		String del=request.getParameter("del");
		del=del+"a";
		if(del.equals("isa")) {
			connectMySQL connectSql=new connectMySQL();
			loginSQL loginSql=new loginSQL(connectSql.getConnection(),name,"0");
			int result=loginSql.isdelete();
			if(result==0) {
			    PrintWriter pw=response.getWriter();
			    pw.write("fai");pw.close();
			}else {
			    PrintWriter pw=response.getWriter();
			    pw.write("suc");pw.close();
			}
			
			
			//loginSql.destroy();
		}
		else {
		//2.进行数据
		try {
			connectMySQL connectSql=new connectMySQL();
			loginSQL loginSql=new loginSQL(connectSql.getConnection(),name,password);
			if(loginSql.isloginsuccess()==1){
				
			    PrintWriter pw=response.getWriter();
			    pw.write("success");pw.close();
			    //System.out.println("aaaaaaaaaaaaa"+name+password);
			}else if(loginSql.isloginsuccess()==0) {
			    PrintWriter pw=response.getWriter();
			    pw.write("admin");pw.close();
			}
			else {
				PrintWriter pw=response.getWriter();
			    pw.write("fail");pw.close();
			    //System.out.println("bbbbbbbbbbbbb"+name+password);
			}
			
		//loginSql.destroy();
	}catch(NullPointerException e) {
		//System.out.println("ss");
	}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
