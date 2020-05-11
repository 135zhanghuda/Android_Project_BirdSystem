package com.example.birdsystem.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.SurfaceControl;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentTransaction;

import com.example.birdsystem.R;
import com.example.birdsystem.View.Activity.HomeScreen;
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

/*
AsyncTask用法总结：
    1.AsyncTask对handler和Thread进行了封装，它有三个泛型参数：
        <Params，Progress,Result>
        Params是Asyntask子线程所需参数，如url等
        Progress表示在线程运行过程中，获取的结果（如http访问中获取的response中的数据—），其涉及了publishProgress(Progress…)，在下面简述。
        Result表示任务完成后，，doInBackground返回值，会在onPostExecute调用。
    2.AsyncTask常用方法有：onPreExecute、doInBackground、onProgressUpdate、onPostExecute
        onPreEecute:在task执行之前执行，如加载一个进度条等，其在主线程中调用，可以进行UI操作。
        doInBackground：在onPreEecute执行完后进行，为一个独立线程，接收Params，可以返回一个Result，实现线程通信，或者调用publishProgress(Progress…)实现线程通讯
        onProgressUpdate：在doInBackground调用publishProgress(Progress…)后调用，个人感觉类似handler。
        onPostExecute：当doInBackgroud方法执行完毕后调用，获取其返回值，实现线程之间的通信。
    3.
 */

public class Task extends AsyncTask<String,String,String> {
    Activity activity;
    URL url;
    String username,password;
    boolean isChecked;
    FragmentTransaction transaction;
    String pathLogin="http://192.168.0.100:8081/WebProject/Login";
    String pathRegister="http://192.168.0.100:8081/WebProject/Register";

    public Task(Activity activity, String username, String password, boolean isChecked, FragmentTransaction transaction){
        this.activity=activity;
        //this.url=url;
        this.username=username;
        this.password=password;
        this.isChecked=isChecked;
        this.transaction=transaction;
    }

    void updateUrl(String mypath){
        try {
            url=new URL(mypath + "?name=" + username + "&password=" + password);
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
    }




    //            URL url=new URL(path + "?uname=" + userName + "&upass=" + passWord);
    /*
    登录判定
     */
    public void isLoginSuccess() {
        updateUrl(pathLogin);
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(String... strings) {
        //ArrayList<?> a=new ArrayList<String>();
        //1.生成okhttp对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //2.生成request对象
        Request request=new Request.Builder().url(url).build();
        //3.使用newCall创建call对象
        Call call = okHttpClient.newCall(request);
        //4.异步get
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                publishProgress("error");
                //System.out.println(url);
                //System.out.println("aaaaaaaaaaaaaa");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String s=response.body().string();
                //System.out.println(s);
                publishProgress(s);
               // System.out.println("bbbbbbbbbbb");

            }
        });
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        //System.out.println(values[0]);
        if(values[0].equals("success")){
            onclickIsRemember();
            Toast.makeText(activity, "登录成功", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(activity, HomeScreen.class);
            intent.putExtra("user",username);
            intent.putExtra("password",password);
            intent.putExtra("power","1");
            activity.startActivity(intent);
            activity.finish();
        }//登录失败（为输入账号密码）
        else if(values[0].equals("admin")){
            onclickIsRemember();
            Toast.makeText(activity, "登录成功,该账号为管理员", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(activity, HomeScreen.class);
            intent.putExtra("user",username);
            intent.putExtra("password",password);
            intent.putExtra("power","0");
            activity.startActivity(intent);
            activity.finish();
        }
        else if (username.equals("")||password.equals("") ) {
            Toast.makeText(activity, "请输入账号名密码", Toast.LENGTH_SHORT).show();
        }else if(values[0].equals("error")){
            Toast.makeText(activity, "未连接上服务器", Toast.LENGTH_SHORT).show();
        }else if(values[0].equals("rsuccess")){
            registerDialog();
        }else if(values[0].equals("rfail")){
            Toast.makeText(activity, "用户名已存在请修改", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(activity, "请确保您的输入账号密码正确", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
    //记住密码控件操作
    public void onclickIsRemember(){
        SharedPreferences sharedPreferences = activity.getSharedPreferences("login",activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        /*
        如果记住密码：
        1.将togglerbutton添加到数据库
        2.将password和username加入数据库中
         */
        if(isChecked){
            editor.putString("username",username);
            editor.putString("password",password);
            editor.putString("remember","1");
            editor.commit();
        }
    }
    /*
 注册成功dialog
 1.返回登录界面
 2.继续注册
  */
    void registerDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity).setIcon(R.mipmap.ic_launcher).setTitle("最普通dialog")
                .setMessage("注册成功").setPositiveButton("请登录", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //ToDo: 你想做的事情
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
