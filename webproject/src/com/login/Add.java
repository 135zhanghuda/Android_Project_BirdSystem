package com.login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import bean.Bird;
import mytest.connectMySQL;
import mytest.loginSQL;
import mytest.test;

/**
 * Servlet implementation class Add
 */
@WebServlet("/Add")
public class Add extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name,url,detail,t;
		String habitat,lifestyle,residence;
		byte[] img;
		BufferedReader br = new BufferedReader(new InputStreamReader(
                (ServletInputStream) request.getInputStream(), "utf-8"));
        StringBuffer sb = new StringBuffer("");
        String temp;
        while ((temp = br.readLine()) != null) {
            sb.append(temp);
     
	}
        JSONObject jsonObject=JSON.parseObject(sb.toString());
        name=jsonObject.getString("birdName");
        //System.out.println(sb.toString());
        //System.out.println(name+"aa");
        url=jsonObject.getString("birdUrl");
        detail=jsonObject.getString("birdDetails");
        img=jsonObject.getBytes("birdImg");
        t=jsonObject.getString("birdImg");
        //System.out.println(t);
        habitat=jsonObject.getString("birdHabitat");
        lifestyle=jsonObject.getString("birdLifestyle");
        residence=jsonObject.getString("birdResidence");
       
        //System.out.println(habitat+"aa");
        
        String getAllSql="insert into  birds VALUES(?,?,?,?,?,?,?);";
		try {
			connectMySQL connectSql=new connectMySQL();
			PreparedStatement psAll=connectSql.getConnection().prepareStatement(getAllSql);
			psAll.setString(1,name);
			psAll.setInt(2,Integer.parseInt(habitat));
			psAll.setInt(3,Integer.parseInt(lifestyle));
			psAll.setInt(4,Integer.parseInt(residence));
			psAll.setBytes(5, img);
			psAll.setString(6,url);
			psAll.setString(7,detail);
			//System.out.println(img);
//			Vector<Bird> birdVector=new Vector<Bird>();
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

}
