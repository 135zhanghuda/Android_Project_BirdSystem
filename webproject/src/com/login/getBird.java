package com.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import bean.Bird;
import mytest.connectMySQL;
import mytest.selectBird;

/**
 * Servlet implementation class getImg
 */
@WebServlet("/getBird")
public class getBird extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String birdName=request.getParameter("birdName");
		String del=request.getParameter("del");
		del=del+"a";
		if(del.equals("isa")) {
			String getAllSql="delete from birds where birdname=?;";
			try {
				connectMySQL connectSql=new connectMySQL();
				PreparedStatement psAll=connectSql.getConnection().prepareStatement(getAllSql);
				psAll.setString(1,birdName);
//				Vector<Bird> birdVector=new Vector<Bird>();
				int result=psAll.executeUpdate();
				if(result==1) {
					PrintWriter out=response.getWriter();
					out.write("suc");		
				}
				else {
					PrintWriter out=response.getWriter();
					out.write("fai");
				}
				psAll.close();
			}catch(NullPointerException e) {
			//System.out.println("ss");
				}catch(SQLException e) {}
		}else {
		selectBird selBird=new selectBird();
		selBird.getBird(birdName);
		if(selBird.getBird(birdName)!=null) {
			response.setContentType("text/html;charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out=response.getWriter();
			//System.out.println(JSON.toJSONString(bird));
			
			out.println(JSON.toJSONString(selBird.getBird(birdName)));
			out.close();
		}else {
		
			PrintWriter pw=response.getWriter();
		    pw.write("null");
		    pw.close();
			}
		}
		

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
