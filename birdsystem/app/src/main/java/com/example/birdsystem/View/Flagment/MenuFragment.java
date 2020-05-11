package com.example.birdsystem.View.Flagment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.birdsystem.R;
import com.example.birdsystem.View.Activity.DataManager;
import com.example.birdsystem.View.Activity.HomeScreen;
import com.example.birdsystem.View.Activity.LoginActivity;
import com.example.birdsystem.View.Activity.UserSafeActivity;

public class MenuFragment extends Fragment {
    View view;
    Button userSafeButton,collectButton,setUpButton,dropUpButton;
    String user,password;
    String power;

    public MenuFragment(String user,String password,String power){
        this.user=user;
        this.password=password;
        this.power=power;
    }
    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_menu, container, false);
        userSafeButton=(Button)view.findViewById(R.id.menu_user);
        collectButton=(Button)view.findViewById(R.id.menu_collect);
        setUpButton=(Button)view.findViewById(R.id.menu_data_manage);
        dropUpButton=(Button)view.findViewById(R.id.menu_dropout);
       // System.out.println(user+"    -----"+power);
        //四个按钮点击事件
        userButtonOnclick();
        setCollectButtonOnclick();
        setSetUpButtonOnclick();
        setDropUpButtonOnclick();





        return view;
    }
    void userButtonOnclick(){
        userSafeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), UserSafeActivity.class);
                //System.out.println(user+"    -----"+power);
                intent.putExtra("user",user);
                intent.putExtra("power",power);
                getActivity().startActivity(intent);
            }
        });

    }
    void setCollectButtonOnclick(){
        collectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
    void setSetUpButtonOnclick(){
        setUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), DataManager.class);
                startActivity(intent);
            }
        });

    }
    void setDropUpButtonOnclick(){
        dropUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();

            }
        });

    }
}
