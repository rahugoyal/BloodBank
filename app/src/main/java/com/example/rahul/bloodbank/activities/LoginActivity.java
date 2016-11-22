package com.example.rahul.bloodbank.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rahul.bloodbank.R;
import com.example.rahul.bloodbank.fragments.ForgetPwdFragment;
import com.example.rahul.bloodbank.fragments.RegistrationFragment;

public class LoginActivity extends AppCompatActivity {

    TextView mTvSignUp, mTvForgotPwd;
    Button mBtLogin;
    EditText mEtUsername, mEtPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeViews();
    }

    private void initializeViews() {
        mTvSignUp = (TextView) findViewById(R.id.tv_signup_login);
        mTvForgotPwd = (TextView) findViewById(R.id.tv_forgot_login);
        mBtLogin = (Button) findViewById(R.id.bt_login);
        mEtUsername = (EditText) findViewById(R.id.et_username_login);
        mEtPassword = (EditText) findViewById(R.id.et_password_login);

        mTvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.login_container, new RegistrationFragment()).addToBackStack("register").commit();

            }
        });


        mTvForgotPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.login_container, new ForgetPwdFragment()).addToBackStack("forgotpwd").commit();

            }
        });


        mBtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEtUsername.getText() != null && mEtPassword != null) {
                    Intent intentDashboard = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intentDashboard);
                }
                else{
                    Toast.makeText(getBaseContext(),"user name or password can not empty",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getSupportFragmentManager().popBackStack();

    }
}
