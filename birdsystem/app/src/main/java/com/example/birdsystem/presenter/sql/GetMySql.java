package com.example.birdsystem.presenter.sql;

import android.os.AsyncTask;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetMySql {
    int habitat,lifestyle,residence;
    URL url;
    int i;
    public GetMySql(int habitat, int lifestyle, int residence){
        this.habitat=habitat;
        this.lifestyle=lifestyle;
        this.residence=residence;

        try {
             url=new URL( "http://localhost:8081/WebProject/GetInfo"/*+ "?habitat=" + habitat + "&lifestyle=" + lifestyle+"&residence="+residence*/);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    public void getInfo(){
        AsyncTask task=new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] objects) {

                OkHttpClient okHttpClient = new OkHttpClient();
                //2.生成request对象
                System.out.println(url.toString());
                Request request=new Request.Builder().url(url).build();
                //3.使用newCall创建call对象
                Call call = okHttpClient.newCall(request);
                System.out.println("aaaaaa");
                //4.异步get
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        publishProgress("error");
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        String s=response.body().string();
                        //System.out.println(s);
                        publishProgress(s);

                    }
                });

                return null;
            }

            @Override
            protected void onProgressUpdate(Object[] values) {
                super.onProgressUpdate(values);
                String string=values[0].toString();
                Vector<String> vector=new Vector<String>();
                //String[] stringa=new String[100];

                String[] strings=string.split("\n");
                //System.out.println(strings.length);
                //System.out.println(strings[2]);
                //System.out.println("A");
                //JSONObject jsonObject=new JSONObject();
                org.json.JSONObject jsonObject;
                try {
                    i=0;
                    while (i<strings.length) {
                        jsonObject = new org.json.JSONObject(strings[i]);
                        i++;
                        //System.out.println(jsonObject.toString());
                    }
                    //System.out.println(jsonObject.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //String result = string.replaceAll("\n", "");

            }
        }.execute();
    }

}
