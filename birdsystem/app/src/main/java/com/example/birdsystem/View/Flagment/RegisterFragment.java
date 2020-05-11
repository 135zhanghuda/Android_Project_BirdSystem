package com.example.birdsystem.View.Flagment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.birdsystem.R;
import com.example.birdsystem.presenter.Http;
import com.example.birdsystem.presenter.Task;

public class RegisterFragment extends Fragment {
    View view;
    Handler handler;
    EditText username,password;
    Button registerButton;
    FragmentTransaction transaction;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_register, container, false);
        username=(EditText) view.findViewById(R.id.register_username);
        password=(EditText)view.findViewById(R.id.register_password) ;
        registerButton=(Button)view.findViewById(R.id.register_button);
        transaction=getActivity().getSupportFragmentManager().beginTransaction();;
        onclickRegisterButton();
        return view;
    }



    /*
    注册控件点击事件
    调用Http类，来判断是否注册成功
     */
    public void onclickRegisterButton(){
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //myHandler();
                //Http httpLogin = new Http(username.getText().toString(), password.getText().toString(),handler,getActivity());
               // httpLogin.isrRegisterSuccess();
                //final Http httpLogin = new Http(username.getText().toString(), password.getText().toString(),handler,getActivity(),false);
                //httpLogin.isrRegisterSuccess();
                Task task=
                        new Task(getActivity(),username.getText().toString(), password.getText().toString(),false,transaction);
                task.isrRegisterSuccess();
                task.execute("");
                username.setText("");
                password.setText("");
            }
        });


    }

    /*
    handler处理
    接收子线程传来的数据
    更新UI
     */
    public void myHandler(){
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                System.out.println(msg.obj+"asdsdsdsa");
                if(msg.obj.equals("rsuccess")){
                    registerDialog();
                }//登录失败（为输入账号密码）
                else if (username.getText().toString().equals("") || password.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "请输入账号名密码", Toast.LENGTH_SHORT).show();
                } else {
                    //System.out.println(msg.obj+"aaaaaaaaaaa");
                    Toast.makeText(getActivity(), "用户名已存在请修改", Toast.LENGTH_SHORT).show();
                }
            }
        };

    }



    /*
    注册成功dialog
    1.返回登录界面
    2.继续注册
     */
    void registerDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setIcon(R.mipmap.ic_launcher).setTitle("注册")
                .setMessage("注册成功").setPositiveButton("去登录", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_login_change,new LoginFragment()).commit();
                    }
                }).setNegativeButton("继续注册", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        builder.create().show();
    }

}
