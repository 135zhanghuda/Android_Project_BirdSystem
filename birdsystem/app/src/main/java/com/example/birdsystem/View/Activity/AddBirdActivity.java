package com.example.birdsystem.View.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.birdsystem.R;
import com.example.birdsystem.View.Flagment.LoginFragment;
import com.example.birdsystem.model.Bird;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddBirdActivity extends AppCompatActivity {
    public static final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
    ArrayAdapter<String> habitatAdapter,lifestyleAdapter,residenceAdapter;
    //三个获取当前分类textview
    Spinner habitatSpinner,lifestyleSpinner,residenceSpinner;
    List<String> habitatSpringList=new ArrayList<>();
    List<String> lifestyleSpringList=new ArrayList<>();
    List<String> residenceSpringList=new ArrayList<>();
    TextView birdName,birdUrl,birdDetail;
    int habitat=0,lifestyle=0,residence=0;
    public static final int RC_CHOOSE_PHOTO = 2;
    private static final int PHOTO_REQUEST_GALLERY=100;
    String getName,getUrl,getDetail;
    String json;
    Button addButton,selectImg;
    ImageView getImg;
    byte[] map;
    int ischoose=0;
    private ImageView picture;
    private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bird);
        habitatSpinner=(Spinner)findViewById(R.id.spinner_habitat_add);
        lifestyleSpinner=(Spinner) findViewById(R.id.spinner_lifestyle_add);
        residenceSpinner=(Spinner) findViewById(R.id.spinner_residence_add);
        birdName=(TextView)findViewById(R.id.add_bird_name) ;
        birdUrl=(TextView)findViewById(R.id.add_bird_url) ;
        birdDetail=(TextView)findViewById(R.id.add_bird_details) ;
        addButton=(Button)findViewById(R.id.add_button);
        selectImg=(Button)findViewById(R.id.add_bird_img);
        getImg = (ImageView) findViewById(R.id.add_img);
        habitatOnclick();
        lifestyleOnclick();
        residenceOnclick();
        initHabitatSpinner();
        initLifestyleSpinner();
        initResidenceSpinner();

        selectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intent,RC_CHOOSE_PHOTO);*/
                openGallery();
                }

        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getName=birdName.getText().toString();
                getUrl=birdUrl.getText().toString();
                getDetail=birdDetail.getText().toString();
                Bird bird=new Bird(getName,habitat,lifestyle,residence,getDetail,getUrl,map);
                json=com.alibaba.fastjson.JSON.toJSONString(bird);
                //System.out.println(json);
                if(getName.equals("")||getUrl.equals("")){
                    Toast.makeText(AddBirdActivity.this,"输入不能为空", Toast.LENGTH_SHORT).show();
                }else
                okhttpPost();
            }
        });

    }
    // 打开相册
    public void openGallery() {
        //getcamera();
        Intent intent =new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");

        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLE
        startActivityForResult(intent,PHOTO_REQUEST_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        switch (requestCode) {
            case PHOTO_REQUEST_GALLERY:
                if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(AddBirdActivity.this, "取消了", Toast.LENGTH_LONG).show();
                    break;
                }
                Bitmap  photo = null;
                try {
                    photo = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    map=baos.toByteArray();

                    ischoose=1;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                /*System.out.println(data);
                System.out.println(photo+"aaaaa");*/
                //Bitmap bmp= BitmapFactory.decodeByteArray(map, 0, map.length);
                //getImg.setImageBitmap(bmp);
                getImg.setImageBitmap(photo);
                break;
        }
    }


    public void choosePhoto() {
        Intent intentToPickPic = new Intent(Intent.ACTION_PICK, null);
        intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intentToPickPic, RC_CHOOSE_PHOTO);
    }


    public void habitatOnclick(){
        habitatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
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
                residence=position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
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
        habitatAdapter=new ArrayAdapter<String>(AddBirdActivity.this, R.layout.item_isfly,R.id.test,habitatSpringList );
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
        lifestyleAdapter=new ArrayAdapter<String>(AddBirdActivity.this, R.layout.item_isfly,R.id.test,lifestyleSpringList );
        lifestyleSpinner.setAdapter(lifestyleAdapter);
    }

    public  void initResidenceSpinner(){
        residenceSpringList.add("留鸟");
        residenceSpringList.add("漂鸟");
        residenceSpringList.add("候鸟");
        residenceSpringList.add("迷鸟");
        residenceSpringList.add("其他类型");
        residenceAdapter=new ArrayAdapter<String>(AddBirdActivity.this, R.layout.item_isfly,R.id.test,residenceSpringList );
        residenceSpinner.setAdapter(residenceAdapter);
    }
    public void okhttpPost(){
        AsyncTask task=new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                OkHttpClient okHttpClient=new OkHttpClient();
                RequestBody requestBody = RequestBody.create(json,JSON);
                Request request = new Request.Builder()
                        .url("http://192.168.0.100:8081/WebProject/Add")
                        .post(requestBody)
                        .build();
                Call call = okHttpClient.newCall(request);
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
                if(getName.equals("")||getUrl.equals("")||ischoose==0) {
                    Toast.makeText(AddBirdActivity.this,"输入不能为空", Toast.LENGTH_SHORT).show();
                }
                    else if(values[0].equals("f")){
                    Toast.makeText(AddBirdActivity.this,"该鸟类信息已存在", Toast.LENGTH_SHORT).show();
                }
                else if(values[0].equals("s")){
                    Toast.makeText(AddBirdActivity.this,"", Toast.LENGTH_SHORT).show();
                    registerDialog();
                }
            }
        }.execute();
    }
    void registerDialog(){
        birdName.setText("");
        birdDetail.setText("");
        birdUrl.setText("");
        AlertDialog.Builder builder = new AlertDialog.Builder(AddBirdActivity.this).setIcon(R.mipmap.ic_launcher).setTitle("添加鸟类信息")
                .setMessage("添加成功").setPositiveButton("返回首页", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //ToDo: 你想做的事情
                        //Intent mintent=new Intent(AddBirdActivity.this,HomeScreen.class);
                        //startActivity(mintent);
                        AddBirdActivity.this.finish();
                    }
                }).setNegativeButton("继续添加", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        builder.create().show();
    }


    void getcamera(){
        // 创建一个File对象，用于保存摄像头拍下的图片，这里把图片命名为output_image.jpg
        // 并将它存放在手机SD卡的应用关联缓存目录下
        File outputImage = new File(getExternalCacheDir(), "output_image.jpg");

        // 对照片的更换设置
        try {
            // 如果上一次的照片存在，就删除
            if (outputImage.exists()) {
                outputImage.delete();
            }
            // 创建一个新的文件
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 如果Android版本大于等于7.0
        if (Build.VERSION.SDK_INT >= 24) {
            // 将File对象转换成一个封装过的Uri对象
            imageUri = FileProvider.getUriForFile(AddBirdActivity.this, "com.example.lenovo.cameraalbumtest.fileprovider", outputImage);
        } else {
            // 将File对象转换为Uri对象，这个Uri标识着output_image.jpg这张图片的本地真实路径
            imageUri = Uri.fromFile(outputImage);
        }

        // 动态申请权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
        } else {
            // 启动相机程序
            startCamera();
        }

    }
    private void startCamera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        // 指定图片的输出地址为imageUri
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, 1);
    }
}
