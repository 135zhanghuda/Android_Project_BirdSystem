package com.example.birdsystem.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.birdsystem.R;

import org.w3c.dom.Text;

public class UserSafeActivity extends AppCompatActivity {
    TextView user,power;
    Button changePasswordButton,gotoButton;
    String userString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_safe);
        user=(TextView)findViewById(R.id.safe_user);
        power=(TextView)findViewById(R.id.safe_power);
        changePasswordButton=(Button)findViewById(R.id.password_change);
        gotoButton=(Button)findViewById(R.id.gotohome);
        getMessage();
    }
    void getMessage(){
        Intent intent=getIntent();
        userString=intent.getStringExtra("user");
        String powerString=intent.getStringExtra("power");
        user.setText(userString);
        if(powerString.equals("0")){
            power.setText("管理员");
        }else{
            power.setText("普通用户");
        }
        gotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserSafeActivity.this.finish();
            }
        });
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent=new Intent(UserSafeActivity.this,ChangePasswordActivity.class);
                myIntent.putExtra("user",userString);
                startActivity(myIntent);

            }
        });
    }

}
