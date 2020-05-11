package com.example.birdsystem.presenter;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceControl;

import androidx.fragment.app.FragmentTransaction;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Http {
    private String userName;
    private String passWord;
    public  boolean rt=true;
    public Handler handler;
    public Activity activity;
    String getBuffer="";
    String pathLogin="http://192.168.0.105:8081/WebProject/Login";
    String pathRegister="http://192.168.0.105:8081/WebProject/Register";
    URL url,url1,url2;
    boolean isChecked;
    public Http(String username, String password, Handler handler, Activity activity, boolean isChecked){
        userName=username;
        passWord=password;
        this.handler=handler;
        this.activity=activity;
        this.isChecked=isChecked;
    }

    void updateUrl(String mypath){
        try {
            url=new URL(mypath + "?name=" + userName + "&password=" + passWord);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /*
    注册判定
    1.连接和访问服务器
    2.检查mysql中数据是否已经存在（在服务端实现）
    3.获取服务器返回的数据
     */
    public void isrRegisterSuccess(){
        updateUrl(pathRegister);
        connectHttp();
    }




    //            URL url=new URL(path + "?uname=" + userName + "&upass=" + passWord);
    /*
    登录判定
     */
    public void isLoginSuccess() {
        updateUrl(pathLogin);
        connectHttp();
    }
//System.out.println(checkUrl(url.toString(),2000));
    /*
    1.使用synctask来实现线程通信
    2.使用okhttp来访问服务器
     */
    public void connectHttp(){
        //Task task=new Task(activity,userName,passWord,url,isChecked);
        //task.execute(url.toString());
    }





}
