package com.example.birdsystem.View.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.birdsystem.R;
import com.example.birdsystem.View.Flagment.MenuFragment;
import com.example.birdsystem.View.Flagment.RecommendFragment;
import com.example.birdsystem.View.Flagment.SortFragment;
import com.example.birdsystem.model.Bird;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeScreen extends AppCompatActivity {
    String birdPath = "http://192.168.0.100:8081/WebProject/GetInfo";
    Handler handler;
    RadioGroup radioGroup;
    RadioButton recommendButton, classfficationButton, myMenuButton;
    //Fragment changeFragment;
    RecommendFragment recommendFragment;
    SortFragment sortFragment;
    MenuFragment myMenuFragment;
    Button addBirdButton,searchButton;
    String user,password;
    String power;
    EditText searchText;
    String getText;
    URL url;
    //装要显示的所有fragment的列表
    private List<Fragment> fragmentList = new ArrayList<>();
    //fragment索引位置 : 0为推荐、1为分类、2为我的（我的主页）
    private int position = 0;
    //当前fragment
    private Fragment currentFragment = new Fragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        /*View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(option);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();*/
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);*/



        //changeFragment=(Fragment)findViewById(R.id.top_fragment) ;
        //fragment初始化
        //setSupportActionBar(toolbar);
        Intent myintent=getIntent();
        user=myintent.getStringExtra("user");
        password=myintent.getStringExtra("password");
        power=myintent.getStringExtra("power");
        //System.out.println(user+"    -----"+power);
        //控件初始化
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        recommendButton = (RadioButton) findViewById(R.id.button_recommend);
        classfficationButton = (RadioButton) findViewById(R.id.button_classiffication);
        myMenuButton = (RadioButton) findViewById(R.id.button_mymenu);
        addBirdButton=(Button)findViewById(R.id.bird_add);
        searchButton=(Button)findViewById(R.id.bird_search);
        searchText=(EditText)findViewById(R.id.bird_search_text);
        initFragment();
        //radiobutton的点击事件，给position赋值
        radioButtonChange();
        addButtonOnclick();
        setSearchButton();
        //加载fragment
        //showFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //初始化界面（主fragment）
         transaction.add(R.id.addfragment, fragmentList.get(position), "" + position);//第三个参数给添加的fragment增加一个标识
        transaction.commit();

        //createDatabase();
        //getJson();
    }
    public void addButtonOnclick(){
        addBirdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addIntent=new Intent(HomeScreen.this,AddBirdActivity.class);
                startActivity(addIntent);
            }
        });
    }
    public void setSearchButton(){
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getText=searchText.getText().toString();
                try {
                    url=new URL( "http://192.168.0.100:8081/WebProject/getBird"+ "?birdName="+getText);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
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
                    @SuppressLint({"StaticFieldLeak", "WrongThread"})
                    @Override
                    protected void onProgressUpdate(Object[] values) {
                        super.onProgressUpdate(values);
                        String string=values[0].toString();
                        if(!string.equals(null)) {
                            if (string.equals("null")) {
                                Toast.makeText(HomeScreen.this, "为查询到相关鸟类", Toast.LENGTH_SHORT).show();

                            } else {
                                Bird bird = JSON.parseObject(string, Bird.class);
                                Intent intent = new Intent(HomeScreen.this, BirdActivity.class);
                                //System.out.println(position);
                                //System.out.println(birdVector.get(position).getBirdName());


                                intent.putExtra("birdName", bird.getBirdName());
                                intent.putExtra("habitat", bird.getBirdHabitat() + "");
                                intent.putExtra("lifestyle", bird.getBirdLifestyle() + "");
                                intent.putExtra("residence", bird.getBirdResidence() + "");
                                intent.putExtra("detail", bird.getBirdDetails());
                                //intent.putExtra("img",birdVector.get(position).getBirdImg());
                                intent.putExtra("url", bird.getBirdUrl());
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bird.getBirdImg(), 0, bird.getBirdImg().length);
                                //System.out.println(bitmap);
                                if (bitmap == null) {

                                    Resources r = HomeScreen.this.getResources();
                                    Bitmap bmp = BitmapFactory.decodeResource(r, R.drawable.no_img);
                                    //Bitmap newb = Bitmap.createBitmap( 300, 300, Config.ARGB_8888 );
                                    //bitmap=bmp;
                                    //imageView.setImageBitmap((bmp));
                                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                    bmp.compress(Bitmap.CompressFormat.JPEG, 80, baos);
                                    byte[] data = baos.toByteArray();
                                    intent.putExtra("img", data);


                                } else {
                                    intent.putExtra("img", bird.getBirdImg());
                                }

                                startActivity(intent);
                            }
                        }
                    }
                }.execute();
            }
        });
    }


    /*
     * 初始化frament并将fragment放入lsit
     */
    public void initFragment() {
        recommendFragment = new RecommendFragment();
        sortFragment = new SortFragment();
        myMenuFragment = new MenuFragment(user,password,power);
        fragmentList.add(recommendFragment);
        fragmentList.add(sortFragment);
        fragmentList.add(myMenuFragment);
        currentFragment = recommendFragment;
    }

    /*
    通过FragmentTransaction加载fragment
     */

    public void showFragment() {
        //获取FragmentTransaction
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        //如果需要展示的这个Fragment没有被添加过，那么隐藏当前Fragmnet并且添加这个Fragment
        transaction.replace(R.id.addfragment, fragmentList.get(position), "" + position);//第三个参数给添加的fragment增加一个标识

        //把这个Fragment设置为当前Fragment
        currentFragment = fragmentList.get(position);
        //提交
        transaction.commit();
    }

    /*
    radioButton点击事件
     */
    public void radioButtonChange() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == recommendButton.getId()) {
                    //Toast.makeText(MainActivity.this,"b1选中", Toast.LENGTH_LONG).show();
                    position = 0;
                }
                if (checkedId == classfficationButton.getId()) {
                    //Toast.makeText(MainActivity.this,"b2选中", Toast.LENGTH_LONG).show();
                    position = 1;
                }
                if (checkedId == myMenuButton.getId()) {
                    position = 2;
                    //Toast.makeText(main.this,"b3选中", Toast.LENGTH_LONG).show();
                }
                showFragment();
            }
        });
    }

    public void getJson() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(birdPath);
                    //得到connection对象。
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //设置超时
                    connection.setConnectTimeout(6 * 1000);
                    //设置请求方式
                    connection.setRequestMethod("GET");
                    //连接
                    connection.connect();
                    // Toast.makeText(activity.getApplicationContext(), "yes",Toast.LENGTH_SHORT).show();
                    // System.out.println("println输入日志信息");
                    if (connection.getResponseCode() == 200) {
                        InputStream inputStream = connection.getInputStream();
                        StringBuilder stringBuilder = new StringBuilder();
                        InputStream is = connection.getInputStream();
                        BufferedReader br = new BufferedReader(new InputStreamReader(is));
                        String json;
                        // DatabaseHelper dbHelper = new DatabaseHelper(MainActivity.this,3);
                        //1.得到连接
                        //SQLiteDatabase sb = dbHelper.getReadableDatabase();
                        while ((json = br.readLine()) != null) {
                            JSONObject jsonObject = new JSONObject(json);
                            String birdName = jsonObject.optString("birdName");
                            String birdDetails = jsonObject.optString("birdDetails");
                            String birdUrl = jsonObject.optString("birdUrl");
                            String birdImg = jsonObject.optString("birdImg");
                            System.out.println(birdImg);
                            int birdHabitat = jsonObject.optInt("birdHabitat");
                            int birdLifestyle = jsonObject.optInt("birdLifestyle");
                            int birdResidence = jsonObject.optInt("birdResidence");
                            ContentValues values = new ContentValues();
                            values.put("birdname", birdName);
                            values.put("details", birdDetails);
                            values.put("url", birdUrl);
                            values.put("img", birdImg);
                            values.put("habitat", birdHabitat);
                            values.put("lifestyle", birdLifestyle);
                            values.put("residence", birdResidence);
                            //long id = sb.insert("bird_info",null,values);
                        }
                        //sb.close();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    //创建数据库,并放入数据
    public void createDatabase() {
        //DatabaseHelper dbHelper = new DatabaseHelper(MainActivity.this,1);
        //SQLiteDatabase db = dbHelper.getWritableDatabase();
       // System.out.println("aaaaaaaaaaaaaaaaaaa");
        // String sql=""

    }

    //handle通信
    public void getHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

            }
        };
    }
}
