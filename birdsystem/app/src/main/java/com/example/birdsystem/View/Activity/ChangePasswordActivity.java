package com.example.birdsystem.View.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.birdsystem.R;
import com.example.birdsystem.View.Flagment.LoginFragment;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ChangePasswordActivity extends AppCompatActivity {
    EditText newPassword,surePassword;
    Button changeButton;
    String newpass,username;
    String surepass;
    URL url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        newPassword=(EditText)findViewById(R.id.password_new);
        surePassword=(EditText)findViewById(R.id.password_sure);
        changeButton=(Button)findViewById(R.id.changeit);
        Intent myIntent=getIntent();
        username=myIntent.getStringExtra("user");
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newpass=newPassword.getText().toString();
                surepass=surePassword.getText().toString();
                if(newpass.equals("")||surepass.equals("")){
                    Toast.makeText(ChangePasswordActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                }else if(!newpass.equals(surepass)){
                    Toast.makeText(ChangePasswordActivity.this, "前后两次密码不一致", Toast.LENGTH_SHORT).show();
                }else{
                    AsyncTask task =new AsyncTask() {
                        @Override
                        protected Object doInBackground(Object[] objects) {
                            //1.生成okhttp对象
                            OkHttpClient okHttpClient = new OkHttpClient();
                            //2.生成request对象
                            try {
                                url=new URL("http://192.168.0.100:8081/WebProject/ChangePassword"+ "?username="+username+"&password="+newpass);
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                            //System.out.println(url);
                            Request request=new Request.Builder().url(url).build();
                            //3.使用newCall创建call对象
                            Call call = okHttpClient.newCall(request);
                            //4.异步get
                            call.enqueue(new Callback() {
                                @Override
                                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                    //System.out.println(url);
                                    //System.out.println("aaaaaaaaaaaaaa");
                                }

                                @Override
                                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                    String s=response.body().string();
                                    publishProgress(s);
//                            Toast.makeText(ChangePasswordActivity.this, "密码修改完成", Toast.LENGTH_SHORT).show();
                                    //System.out.println(s);
                                    //System.out.println("bbbbbbbbbbb");

                                }

                            });
                            return null;
                        }

                        @Override
                        protected void onProgressUpdate(Object[] values) {
                            super.onProgressUpdate(values);
                            registerDialog();

                        }
                    }.execute();

                }
            }
        });
    }
    void registerDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ChangePasswordActivity.this).setIcon(R.mipmap.ic_launcher).setTitle("修改密码")
                .setMessage("修改成功").setPositiveButton("返回", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ChangePasswordActivity.this.finish();
                    }
                });
        builder.create().show();
    }
}
