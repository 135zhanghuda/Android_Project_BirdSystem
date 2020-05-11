package com.example.birdsystem.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.birdsystem.R;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BirdActivity extends AppCompatActivity {
    String birdName,url,detail,habitat,lifestyle,residence;
    byte[] img;
    Bitmap bitmap;
    TextView nameText,urlText,detailText,habitatText,lifestyleText,residenceText;
    String bh,bl,br;
    ImageView showImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bird);
        birdInit();
        setUI();

    }
    //数据可控件初始化
    public void birdInit(){
        //获取传过来的数据
        Intent intent=getIntent();
        birdName=intent.getStringExtra("birdName");
        img=intent.getByteArrayExtra("img");
        url=intent.getStringExtra("url");
        habitat=intent.getStringExtra("habitat");
        detail=intent.getStringExtra("detail");
        lifestyle=intent.getStringExtra("lifestyle");
        residence=intent.getStringExtra("residence");
        bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        //控件初始化
        nameText=(TextView)findViewById(R.id.bird_name);
        urlText=(TextView)findViewById(R.id.bird_url);
        detailText=(TextView)findViewById(R.id.bird_information);
        habitatText=(TextView)findViewById(R.id.bird_habitat);
        lifestyleText=(TextView)findViewById(R.id.bird_lifestyle);
        residenceText=(TextView)findViewById(R.id.bird_residence);
        showImg=(ImageView)findViewById(R.id.bird_img);
        showImg.setImageBitmap(bitmap);
        //getImgFrom(showImg);
    }

    public void getImgFrom(final ImageView Img){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://localhost:8080/my/"+img)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {

            }
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();//得到图片的流
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                System.out.println(bitmap.toString());
                Img.setImageBitmap(bitmap);

            }
        });
    }

    //更新界面
    public void setUI(){
        nameText.setText(birdName);
        urlText.setText(url);
        detailText.setText(detail);
        intShow();
        habitatText.setText(bh);
        lifestyleText.setText(bl);
        residenceText.setText(br);
    }

    public void intShow(){
        switch (Integer.parseInt(habitat)){
            case 0:
                bh="*湿地鸟类";
                break;
            case 1:
                bh="*林灌鸟类";
                break;
            case 2:
                bh="*山地鸟类";
                break;
            case 3:
                bh="*城镇鸟类";
                break;
            case 4:
                bh="*农田草地鸟类";
                break;
            case 5:
                bh="*高原荒漠鸟类";
                break;
            case 6:
                bh="*其他类型鸟类";
                break;
            default:
                break;
        }

        switch (Integer.parseInt(lifestyle)){
            case 0:
                bl="*游禽";
                break;
            case 1:
                bl="*涉禽";
                break;
            case 2:
                bl="*陆禽";
                break;
            case 3:
                bl="*猛禽";
                break;
            case 4:
                bl="*攀禽";
                break;
            case 5:
                bl="*鸣禽";
                break;
            case 6:
                bl="*其他类型鸟类";
                break;
            default:
                break;
        }
        switch (Integer.parseInt(residence)){
            case 0:
                br="*留鸟";
                break;
            case 1:
                br="*漂鸟";
                break;
            case 2:
                br="*候鸟";
                break;
            case 3:
                br="*迷鸟";
                break;
            case 6:
                br="*其他类型鸟类";
                break;
            default:
                break;
        }

    }

}
