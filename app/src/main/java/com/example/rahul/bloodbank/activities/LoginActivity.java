package com.example.rahul.bloodbank.activities;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rahul.bloodbank.R;
import com.example.rahul.bloodbank.constants.Constant;
import com.example.rahul.bloodbank.fragments.ForgetPwdFragment;
import com.example.rahul.bloodbank.fragments.RegistrationFragment;
import com.example.rahul.bloodbank.pojo.RegistrationPojo;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    TextView mTvSignUp, mTvForgotPwd;
    Button mBtLogin;
    EditText mEtUsername, mEtPassword;
    List<RegistrationPojo> registrationPojoList = null;
    RegistrationPojo registrationPojo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeViews();
        getUserForAuthenticate();

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
                getSupportFragmentManager().beginTransaction().add(R.id.login_container, new RegistrationFragment()).addToBackStack("register").commit();

            }
        });


        mTvForgotPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().add(R.id.login_container, new ForgetPwdFragment()).addToBackStack("forgotpwd").commit();

            }
        });


        mBtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEtUsername.getText() != null && mEtPassword != null && !mEtUsername.getText().toString().isEmpty() && !mEtPassword.getText().toString().isEmpty()) {
                    getUser();
                    if (registrationPojo != null) {
                        Log.e(registrationPojo.getPassword(), mEtPassword.getText().toString());
                        if (registrationPojo.getPassword().equals(mEtPassword.getText().toString())) {
                            Intent intentDashboard = new Intent(LoginActivity.this, MainActivity.class);
                            intentDashboard.putExtra("personObject", registrationPojo);
                            startActivity(intentDashboard);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Password does not match", Toast.LENGTH_SHORT).show();
                        }
                    } else
                        Toast.makeText(LoginActivity.this, "Username does not exists", Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(getBaseContext(), "Username or password can not empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void getUser() {
        registrationPojo = null;
        if (registrationPojoList != null) {
            for (int i = 0; i < registrationPojoList.size(); i++) {
                if (mEtUsername.getText().toString().equals(registrationPojoList.get(i).getUsername())) {
                    registrationPojo = registrationPojoList.get(i);
                }
            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getSupportFragmentManager().popBackStack();

    }

    public void getUserForAuthenticate() {

        registrationPojoList = new ArrayList<>();
        Constant.FIREBASE_REF.child("person").addValueEventListener(new ValueEventListener() {
                                                                        @Override
                                                                        public void onDataChange(DataSnapshot snapshot) {
                                                                            for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                                                                RegistrationPojo registrationPojo = postSnapshot.getValue(RegistrationPojo.class);
                                                                                registrationPojoList.add(registrationPojo);


                                                                            }
                                                                        }

                                                                        @Override
                                                                        public void onCancelled(FirebaseError firebaseError) {

                                                                        }

                                                                    }

        );

    }
}



