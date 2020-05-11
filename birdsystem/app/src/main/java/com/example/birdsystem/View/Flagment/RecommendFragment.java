package com.example.birdsystem.View.Flagment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.example.birdsystem.R;
import com.example.birdsystem.model.Bird;
import com.example.birdsystem.presenter.Adapter.RecyclerviewAdapter;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class RecommendFragment extends Fragment {
    public RecyclerView recyclerView ;
    View rootView;
    int habitat,lifestyle,residence;
    Vector<Bird> birdVector=new Vector<>();
    RadioGroup radioGroup;
    URL url;
    public RecommendFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_recommend, container, false);
        recyclerView= (RecyclerView) rootView.findViewById(R.id.recycler_view);
        //创建线性布局
       //RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(rootView.getContext());
        LinearLayoutManager linerLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(linerLayoutManager);
        //给RecyclerView设置布局管理器
        //recyclerView.setLayoutManager(mLayoutManager);
        //创建适配器，并且设置
        FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();

         getmessage();




        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        birdVector.clear();
        //birdVector.clear();
    }

    void getmessage(){
        habitat=(int)(Math.random()*7);
        lifestyle=(int)(Math.random()*7);
        residence=(int)(Math.random()*5);
        try {
            url=new URL( "http://192.168.0.100:8081/WebProject/GetInfo"+ "?habitat="
                    + habitat + "&lifestyle=" + lifestyle+"&residence="+residence);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        AsyncTask task=new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                OkHttpClient okHttpClient=new OkHttpClient();
                Request request=new Request.Builder().url(url).build();
                Call call=okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

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
                if(values[0].equals("")){
                }else {
                    String string = values[0].toString();
                    String[] strings = string.split("\n");
                    int i = 0;
                    while (i < strings.length) {
                        Bird bird = JSON.parseObject(strings[i], Bird.class);
                        birdVector.add(bird);
                        i++;
                    }
                    RecyclerviewAdapter mAdapter = new RecyclerviewAdapter(recyclerView,getActivity(),birdVector);
                    recyclerView.setAdapter(mAdapter);
                }

            }
        }.execute();
    }


}
