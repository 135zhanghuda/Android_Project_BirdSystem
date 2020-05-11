package com.example.birdsystem.View.Flagment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.birdsystem.R;
import com.example.birdsystem.View.Activity.HomeScreen;
import com.example.birdsystem.View.Activity.LoginActivity;
import com.example.birdsystem.presenter.Http;
import com.example.birdsystem.presenter.Task;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginFragment extends Fragment {
    Button loginButton,registerButton;
    Handler handler;
    EditText username,password;
    ToggleButton isRemeber;
    View view;
    FragmentTransaction transaction;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_login, container, false);
        /*
        控件的初始化和点击事件
         */
        isRemeber=(ToggleButton)view.findViewById(R.id.login_remember_password) ;
        loginButton=(Button)view.findViewById(R.id.login_login_button);
        registerButton=(Button)view.findViewById(R.id.login_register_button);
        username=(EditText) view.findViewById(R.id.login_username);
        password=(EditText)view.findViewById(R.id.login_password) ;
        transaction=getActivity().getSupportFragmentManager().beginTransaction();
        remeberPassword();
        onclickLoginButton();
        onclickRegisterButton();

        return view;
    }

    //记住密码功能实现
    public void remeberPassword(){

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login",getActivity().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //判断原先是否为记住密码
        if(!sharedPreferences.getString("remember","fail").equals("fail")){
            username.setText(sharedPreferences.getString("username","0"));
            password.setText(sharedPreferences.getString("password","0"));
            isRemeber.setChecked(true);
        }
        //格式化login表
        editor.clear().commit();
    }

    //登录按钮点击事件的实现
    public void onclickLoginButton(){
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //changeHandler();
                Task task=
                        new Task(getActivity(),username.getText().toString(), password.getText().toString(),isRemeber.isChecked(),transaction);
                task.isLoginSuccess();
                task.execute("");
            }
        });
    }


    //记住密码控件操作
    public void onclickIsRemember(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login",getActivity().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        /*
        如果记住密码：
        1.将togglerbutton添加到数据库
        2.将password和username加入数据库中
         */
        if(isRemeber.isChecked()){
            editor.putString("username",username.getText().toString());
            editor.putString("password",password.getText().toString());
            editor.putString("remember","1");
            editor.commit();
        }

    }



    //注册按钮点击事件的实现
    public void onclickRegisterButton(){
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.fragment_login_change,new RegisterFragment()).commit();

            }
        });

    }
    //handler刷新界面
    public void changeHandler(){
         handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                //System.out.println(msg.obj);
                if(msg.obj.equals("success")){
                    onclickIsRemember();
                    Toast.makeText(getActivity(), "登录成功", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getActivity(), HomeScreen.class);

                    startActivity(intent);
                    getActivity().finish();
                }//登录失败（为输入账号密码）
                else if (username.getText().toString().equals("") || password.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "请输入账号名密码", Toast.LENGTH_SHORT).show();
                }else if(msg.obj.equals("error")){
                    Toast.makeText(getActivity(), "未连接上服务器", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(), "请确保您的输入账号密码正确", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }



}
