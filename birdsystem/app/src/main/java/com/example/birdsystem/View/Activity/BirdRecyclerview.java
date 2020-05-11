package com.example.birdsystem.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.birdsystem.R;
import com.example.birdsystem.model.Bird;
import com.example.birdsystem.presenter.Adapter.RecyclerviewAdapter;

import org.json.JSONException;

import java.util.Vector;

public class BirdRecyclerview extends AppCompatActivity {
    public RecyclerView recyclerView ;
    Vector<Bird> birdVector=new Vector<Bird>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getInfo();
        setContentView(R.layout.activity_bird_recyclerview);
        recyclerView= (RecyclerView)findViewById(R.id.bird_recycler_view);
        //创建线性布局
        //RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(rootView.getContext());
        LinearLayoutManager linerLayoutManager = new LinearLayoutManager(BirdRecyclerview.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.addItemDecoration(new DividerItemDecoration(BirdRecyclerview.this,DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(linerLayoutManager);
        //给RecyclerView设置布局管理器
        //recyclerView.setLayoutManager(mLayoutManager);
        //创建适配器，并且设置
        RecyclerviewAdapter mAdapter = new RecyclerviewAdapter(recyclerView,BirdRecyclerview.this,birdVector);
        recyclerView.setAdapter(mAdapter);
    }

    /*
    1.取出传过来的intent的数据
    2.使用JSON解析
    3.将解析出的数据装入vector
     */
    public void getInfo(){
        Intent intent=getIntent();
        String num=intent.getStringExtra("num");
        //System.out.println(num);
        for(int i=0;i<Integer.parseInt(num);i++){
            String string=intent.getStringExtra(Integer.toString(i));
            /*JSONObject jsonObject=new JSONObject();
            //System.out.println(string);
            String birdName=jsonObject.optString("birdName");
            String birdDetails=jsonObject.optString("birdDetails");
            String birdUrl=jsonObject.optString("birdUrl");
            String birdImg= JSONObject("birdImg");
            //System.out.println(birdImg.getBytes());
            int birdHabitat=jsonObject.optInt("birdHabitat");
            int birdLifestyle=jsonObject.optInt("birdLifestyle");
            int birdResidence=jsonObject.optInt("birdResidence");
            Bird bird=new Bird(birdName,birdHabitat,birdLifestyle,birdResidence,birdDetails,birdUrl,birdImg.getBytes());

            birdVector.add(bird);*/
            Bird bird = JSON.parseObject(string, Bird.class);
           // System.out.print(bird.getBirdImg());
            birdVector.add(bird);
            //System.out.println("JavaBean对象：" + grade);
        }
    }
}
