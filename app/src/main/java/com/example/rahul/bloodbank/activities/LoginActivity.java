package com.example.rahul.bloodbank.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    TextView mTvSignUp, mTvForgotPwd;
    Button mBtLogin;
    EditText mEtUsername, mEtPassword;
    RegistrationPojo registrationPojo;
    ProgressDialog progress;

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
        progress = new ProgressDialog(this);

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
                if (mEtUsername.getText() != null && mEtPassword != null && !mEtUsername.getText().toString().isEmpty() && !mEtPassword.getText().toString().isEmpty()) {
                    progress.setMessage("Logging in ...");
                    progress.setIndeterminate(true);
                    progress.setCancelable(false);
                    progress.show();
                    RegistrationPojo registrationPojo = getUserForAuthenticate();
                    if (registrationPojo != null) {
                        if (registrationPojo.getPassword().equals(mEtPassword.getText().toString())) {
                            progress.dismiss();
                            Intent intentDashboard = new Intent(LoginActivity.this, MainActivity.class);
                            intentDashboard.putExtra("personObject", registrationPojo);
                            startActivity(intentDashboard);
                        } else {
                            progress.dismiss();
                            Toast.makeText(getBaseContext(), "Password does not match", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        progress.dismiss();
                        Toast.makeText(getBaseContext(), "Username does not exists", Toast.LENGTH_SHORT).show();

                    }


                } else {
                    Toast.makeText(getBaseContext(), "Username or password can not empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getSupportFragmentManager().popBackStack();

    }

    public RegistrationPojo getUserForAuthenticate() {

        Constant.FIREBASE_REF.child("person").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                String allUserData = String.valueOf(snapshot.getValue());
                if (allUserData != null && !allUserData.isEmpty()) {
                    try {
                        JSONObject jsonData = new JSONObject(allUserData);
                        JSONObject personObject = jsonData.getJSONObject(mEtUsername.getText().toString());
                        Gson gson = new Gson();
                        registrationPojo = gson.fromJson(personObject.toString(), RegistrationPojo.class);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        });

        return registrationPojo;
    }
}



