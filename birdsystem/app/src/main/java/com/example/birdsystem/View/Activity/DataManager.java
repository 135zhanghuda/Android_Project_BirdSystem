package com.example.birdsystem.View.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.birdsystem.R;
import com.example.birdsystem.presenter.MyDiolog;

public class DataManager extends AppCompatActivity {
    Button deleteUserButton,deleteBirdButton,changePowerButton,gotoHomeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_manager);
        deleteUserButton=(Button)findViewById(R.id.data_delete_user);
        deleteBirdButton=(Button)findViewById(R.id.data_delete_bird);
        gotoHomeButton=(Button)findViewById(R.id.data_goto_home);
        //四个按钮点击事件
        setDeleteBirdButton();
        setChangePowerButton();
        setDeleteUserButton();
        setGotoHomeButton();
    }

    public void setChangePowerButton() {
        /*changePowerButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                MyDiolog myDiolog=new MyDiolog();
                myDiolog.DiyDialog2(DataManager.this,"修改权限","changeuser");
            }
        });*/

    }

    public void setDeleteBirdButton() {
        deleteBirdButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                    MyDiolog myDiolog=new MyDiolog();
                    myDiolog.DiyDialog2(DataManager.this,"删除鸟类","deletebird");
                }
        });

    }

    public void setDeleteUserButton() {
        deleteUserButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                MyDiolog myDiolog=new MyDiolog();
                myDiolog.DiyDialog2(DataManager.this,"删除用户","deleteuser");
            }
        });


    }

    
    public void setGotoHomeButton() {
        gotoHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataManager.this.finish();
            }
        });

    }
}
