package com.example.birdsystem.View.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.birdsystem.R;
import com.example.birdsystem.View.Flagment.LoginFragment;

public class LoginActivity extends AppCompatActivity {
    Button loginButton,registerButton;
    EditText usernameEditText,passwordEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_login_change,new LoginFragment()).commit();
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(option);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

}
}
