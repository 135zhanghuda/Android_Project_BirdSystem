package com.example.birdsystem.presenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.birdsystem.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyDiolog {
    EditText editText;
    String getText;
    URL url;
    AlertDialog.Builder alterDiaglog;
    AlertDialog dialog;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void DiyDialog2(final Activity activity, String title, final String kind) {
        alterDiaglog = new AlertDialog.Builder(activity);
        LayoutInflater inflater = LayoutInflater.from(activity.getApplication());
        View view = inflater.inflate(R.layout.mydialog,(ViewGroup)activity.getWindow().getDecorView(),false);
        TextView myTitle=view.findViewById(R.id.dialog_title);
        editText=view.findViewById(R.id.my_dialog);
        Button button=view.findViewById(R.id.my_dialog_button);
        Button closebutton=view.findViewById(R.id.close_dialog);
        myTitle.setText(title);
        alterDiaglog.setView(view);
        dialog = alterDiaglog.create();
        dialog.show();
        closebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getText=editText.getText().toString();
                editText.setText("");

                if(kind.equals("deleteuser"))
                deleteUserHttp(activity);
                else if(kind.equals("changeuser")){
                    changeUserHttp(activity);

                }else if(kind.equals("deletebird")){
                    deleteBirdHttp(activity);
                }


            }
        });

    }



    //修改权限点击事件
    void changeUserHttp(final Activity activity) {
        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                //1.生成okhttp对象
                OkHttpClient okHttpClient = new OkHttpClient();
                //2.生成request对象
                try {
                    url = new URL("http://192.168.0.100:8081/WebProject/Login" + "?name=" + getText+"&del="+"is");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                //System.out.println(url);
                Request request = new Request.Builder().url(url).build();
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
                        String s = response.body().string();
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
                if(values[0].equals("suc")){
                    Toast.makeText(activity, "删除成功", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }else{
                    Toast.makeText(activity, "用户不存在", Toast.LENGTH_SHORT).show();
                }

            }
        }.execute();
    }



    //删除鸟类信息点击事件
    void deleteBirdHttp(final Activity activity) {
        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                //1.生成okhttp对象
                OkHttpClient okHttpClient = new OkHttpClient();
                //2.生成request对象
                try {
                    url = new URL("http://192.168.0.100:8081/WebProject/getBird" + "?birdName=" + getText+"&del="+"is");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                //System.out.println(url);
                Request request = new Request.Builder().url(url).build();
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
                        String s = response.body().string();
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
                if(values[0].equals("suc")){
                    Toast.makeText(activity, "删除成功", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }else{
                    Toast.makeText(activity, "该鸟类不存在", Toast.LENGTH_SHORT).show();
                }

            }
        }.execute();
    }



    //删除用户点击事件
    void deleteUserHttp(final Activity activity) {
        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                //1.生成okhttp对象
                OkHttpClient okHttpClient = new OkHttpClient();
                //2.生成request对象
                try {
                    url = new URL("http://192.168.0.100:8081/WebProject/Login" + "?name=" + getText+"&del="+"is");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                //System.out.println(url);
                Request request = new Request.Builder().url(url).build();
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
                        String s = response.body().string();
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
                if(values[0].equals("suc")){
                    Toast.makeText(activity, "删除成功", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }else{
                    Toast.makeText(activity, "用户不存在", Toast.LENGTH_SHORT).show();
                }

            }
        }.execute();
    }







}
