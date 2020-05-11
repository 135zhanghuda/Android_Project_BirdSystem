package mytest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.stream.FileImageOutputStream;

import com.alibaba.fastjson.JSON;

import bean.Bird;

public class test {
    public void byte2image(byte[] data,String path){


    	File file=new File(path);

    	FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);

		  fos.write(data,0,data.length);


	    	fos.flush();

	    	fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	}
	public static void main(String[] args) {
		String getAllSql="select * from birds where habitat=1 and lifestyle=1 and residence=1;";
		try {
			connectMySQL connectSql=new connectMySQL();
			PreparedStatement psAll=connectSql.getConnection().prepareStatement(getAllSql);
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
						resultSetAll.getString("birdimg").getBytes());	
				//json½âÎöÉÏ´«
				test my=new test(); 
				my.byte2image(bird.getBirdImg(),"E:\\html\\addmap.png");
				
			}
		
		}catch(NullPointerException e) {
		//System.out.println("ss");
			}catch(SQLException e) {}
	}


}
