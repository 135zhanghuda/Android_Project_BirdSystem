package com.login;

import java.io.Console;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import bean.Bird;
import jdk.nashorn.internal.runtime.JSONFunctions;
import mytest.connectMySQL;
import mytest.loginSQL;
import mytest.test;


@WebServlet("/GetInfo")
public class GetInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String habitatstr=request.getParameter("habitat");
		String lifestylestr=request.getParameter("lifestyle");
		String residencestr=request.getParameter("residence");
		//System.out.println(habitatstr+"aaaaa");
		//System.out.println(lifestylestr);
		//System.out.println(residencestr);
		//System.out.println("asdsadsd");
		int habitat=Integer.parseInt(habitatstr);
		int lifestyle=Integer.parseInt(lifestylestr);
		int residence=Integer.parseInt(residencestr);
		String getAllSql="select * from birds where habitat=? and lifestyle=? and residence=?;";
		try {
			connectMySQL connectSql=new connectMySQL();
			PreparedStatement psAll=connectSql.getConnection().prepareStatement(getAllSql);
			psAll.setInt(1,habitat);
			psAll.setInt(2,lifestyle);
			psAll.setInt(3,residence);
//			Vector<Bird> birdVector=new Vector<Bird>();
			ResultSet resultSetAll =psAll.executeQuery(); 
			byte[] a;
			while(resultSetAll.next()){
				Bird bird=new Bird(resultSetAll.getString("birdname"),
						resultSetAll.getInt("habitat"),
						resultSetAll.getInt("lifestyle"),
						resultSetAll.getInt("residence"),
						resultSetAll.getString("details"),
						resultSetAll.getString("birdurl"),
						resultSetAll.getBytes("birdimg"));	
				/*test my=new test();
				my.byte2image(resultSetAll.getBytes("birdimg"), "E:\\html\\addmap.png");*/
				
				//json½âÎöÉÏ´«
				response.setContentType("text/html;charset=UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				response.setCharacterEncoding("UTF-8");
				PrintWriter out=response.getWriter();
				//System.out.println(JSON.toJSONString(bird));
				out.println(JSON.toJSONString(bird));				
			}
			psAll.close();
		}catch(NullPointerException e) {
		//System.out.println("ss");
			}catch(SQLException e) {}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
