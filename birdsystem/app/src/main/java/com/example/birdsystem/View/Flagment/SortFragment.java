package com.example.birdsystem.View.Flagment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birdsystem.R;
import com.example.birdsystem.View.Activity.BirdRecyclerview;
import com.example.birdsystem.presenter.Adapter.RecyclerviewAdapter;
import com.example.birdsystem.presenter.sql.GetMySql;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SortFragment extends Fragment {
    View sortView;
    URL url;
    Spinner habitatSpinner,lifestyleSpinner,residenceSpinner;
    ArrayAdapter<String> habitatAdapter,lifestyleAdapter,residenceAdapter;
    TextView getHabitat,getLifestyle,getResidence;
    Button sortInquireButton;
    //三个获取当前分类textview
    List<String> habitatSpringList=new ArrayList<>();
    List<String> lifestyleSpringList=new ArrayList<>();
    List<String> residenceSpringList=new ArrayList<>();
    int habitat=0,lifestyle=0,residence=0,i;
    static {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        sortView=inflater.inflate(R.layout.fragment_sort, container, false);
        habitatSpinner=sortView.findViewById(R.id.spinner_habitat);
        lifestyleSpinner=sortView.findViewById(R.id.spinner_lifestyle);
        residenceSpinner=sortView.findViewById(R.id.spinner_residence);
        getHabitat=sortView.findViewById(R.id.get_habitat);
        getLifestyle=sortView.findViewById(R.id.get_lifestyle);
        getResidence=sortView.findViewById(R.id.get_residence);
        sortInquireButton=sortView.findViewById(R.id.button_inquire);

        //给栖息地下拉框添加加元素,设置样式
        initHabitatSpinner();
        //给生活习性下拉框添加加元素,设置样式
        initLifestyleSpinner();
        //给留居类型下拉框添加加元素,设置样式
        initResidenceSpinner();
        //栖息地下拉框点击事件
        habitatOnclick();
        //生活习性下拉框点击事件
        lifestyleOnclick();
        //留居类型下拉框点击事件
        residenceOnclick();
        //分类查询按钮点击事件
        sortInquireOnclick();




        return sortView;

    }



    //分类查询点击事件
    public void sortInquireOnclick(){
        sortInquireButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    url=new URL( "http://192.168.0.100:8081/WebProject/GetInfo"+ "?habitat="
                            + habitat + "&lifestyle=" + lifestyle+"&residence="+residence);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                //GetMySql getMySql=new GetMySql(habitat,lifestyle,residence);
                //getMySql.getInfo();
                getInfoFromMySql();
            }
        });
    }


    //湿地鸟类、林灌鸟类、山地鸟类、城镇鸟类、农田草地鸟类、高原荒漠鸟类
    public void initHabitatSpinner(){
        habitatSpringList.add("湿地鸟类");
        habitatSpringList.add("林灌鸟类");
        habitatSpringList.add("山地鸟类");
        habitatSpringList.add("城镇鸟类");
        habitatSpringList.add("农田草地鸟类");
        habitatSpringList.add("高原荒漠鸟类");
        habitatSpringList.add("其他类型鸟类");
        //设置字体尺寸
        habitatAdapter=new ArrayAdapter<String>(getActivity(), R.layout.item_isfly,R.id.test,habitatSpringList );
        habitatSpinner.setAdapter(habitatAdapter);
    }

    public void initLifestyleSpinner(){
        lifestyleSpringList.add("游禽");
        lifestyleSpringList.add("涉禽");
        lifestyleSpringList.add("陆禽");
        lifestyleSpringList.add("猛禽");
        lifestyleSpringList.add("攀禽");
        lifestyleSpringList.add("鸣禽");
        lifestyleSpringList.add("其他类型");
        lifestyleAdapter=new ArrayAdapter<String>(getActivity(), R.layout.item_isfly,R.id.test,lifestyleSpringList );
        lifestyleSpinner.setAdapter(lifestyleAdapter);
    }

    public  void initResidenceSpinner(){
        residenceSpringList.add("留鸟");
        residenceSpringList.add("漂鸟");
        residenceSpringList.add("候鸟");
        residenceSpringList.add("迷鸟");
        residenceSpringList.add("其他类型");
        residenceAdapter=new ArrayAdapter<String>(getActivity(), R.layout.item_isfly,R.id.test,residenceSpringList );
        residenceSpinner.setAdapter(residenceAdapter);
    }

    public void habitatOnclick(){
        habitatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getHabitat.setText((String)habitatSpinner.getItemAtPosition(position));
                habitat=position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void lifestyleOnclick(){
        lifestyleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getLifestyle.setText((String)lifestyleSpinner.getItemAtPosition(position));
                lifestyle=position;
                //System.out.println("aaaaaaaaa"+position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    public void residenceOnclick(){
        residenceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getResidence.setText((String)residenceSpinner.getItemAtPosition(position));
                residence=position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }
    //从mysql中获取数据
    void getInfoFromMySql(){
        AsyncTask task=new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] objects) {

                OkHttpClient okHttpClient = new OkHttpClient();
                //2.生成request对象
                Request request=new Request.Builder().url(url).build();
                //3.使用newCall创建call对象
                Call call = okHttpClient.newCall(request);
                //System.out.println("aaaaaa");
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
                //System.out.println(values[0].toString());
                Vector<String> vector=new Vector<String>();
                //String[] stringa=new String[100];

                String[] strings=string.split("\n");
                //System.out.println(strings.length);
                //System.out.println(strings[2]);
                //System.out.println("A");
                //JSONObject jsonObject=new JSONObject();
                //org.json.JSONObject jsonObject;
                    i=0;
                    Intent intent=new Intent(getActivity(), BirdRecyclerview.class);
                    if(string.equals(""))
                        intent.putExtra("num","0");
                    else
                    intent.putExtra("num",Integer.toString(strings.length));
                    while (i<strings.length) {
                        //jsonObject = new org.json.JSONObject(strings[i]);
                        intent.putExtra(Integer.toString(i),strings[i]);
                        i++;
                        //System.out.println(Integer.toString(i));
                    }
                    startActivity(intent);
                    //System.out.println(jsonObject.toString());

                //String result = string.replaceAll("\n", "");

            }
        }.execute();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        habitatSpringList.clear();
        lifestyleSpringList.clear();
        residenceSpringList.clear();
    }
}
