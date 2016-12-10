package com.example.rahul.bloodbank.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.AuthConfig;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsAuthButton;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
import com.example.rahul.bloodbank.R;
import com.example.rahul.bloodbank.applications.DemoApplication;
import com.example.rahul.bloodbank.constants.Constant;
import com.example.rahul.bloodbank.pojo.RegistrationPojo;
import com.example.rahul.bloodbank.utils.Utils;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul on 11/21/2016.
 */

public class ForgetPwdFragment extends Fragment {
    EditText mEtusername, mEtpassword, mEtconfirmpwd;
    Button mBtforgotpwd;
    DigitsAuthButton mAuthBtn;
    RelativeLayout mRlForgotPwd;
    List<RegistrationPojo> registrationPojoList;
    RegistrationPojo registrationPojo;
    boolean isSetting;
    AuthCallback mAuthCallback;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forget_pwd_fragment, container, false);
        initializeViews(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setList();

        if (getArguments() != null) {
            isSetting = getArguments().getBoolean("isSetting");
            if (isSetting) {
                mRlForgotPwd.setBackgroundResource(R.mipmap.im_dashboard_bg);
                mEtusername.setText(Constant.registrationPojo.getUsername());
                mEtusername.setEnabled(false);
            }
        }

    }

    private void initializeViews(View view) {
        mEtusername = (EditText) view.findViewById(R.id.et_username_forgotpwd);
        mEtpassword = (EditText) view.findViewById(R.id.et_password_forgotpwd);
        mEtconfirmpwd = (EditText) view.findViewById(R.id.et_confirm_password_forgotpwd);
        mRlForgotPwd = (RelativeLayout) view.findViewById(R.id.rl_forgotpwd);
        mBtforgotpwd = (Button) view.findViewById(R.id.bt_forgotpwd);
        registrationPojoList = new ArrayList<>();
        mAuthBtn = (DigitsAuthButton) view.findViewById(R.id.bt_auth_forgotpwd);
        mAuthBtn.setText("Authentication");


        mBtforgotpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Digits.clearActiveSession();

                if (Utils.isNetworkAvailable(getActivity().getApplicationContext())) {
                    Utils.hideKeyboard(v, getActivity().getApplicationContext());

                    if (validateDetails()) {
                        getUser();
                        if (registrationPojo != null) {
                            if (registrationPojo.getUsername().equals(mEtusername.getText().toString())) {
                                if (isSetting) {
                                    mAuthCallback = new AuthCallback() {
                                        @Override
                                        public void success(DigitsSession session, String phoneNumber) {
                                            Constant.registrationPojo.setPassword(mEtpassword.getText().toString());
                                            Constant.FIREBASE_REF.child("person").child(Constant.registrationPojo.getUsername()).setValue(Constant.registrationPojo);
                                            Toast.makeText(getActivity(), "Password changed", Toast.LENGTH_SHORT).show();
                                            refreshFragment();

                                        }

                                        @Override
                                        public void failure(DigitsException exception) {
                                            Toast.makeText(getActivity(), "Failed to authenticate", Toast.LENGTH_SHORT).show();
                                            refreshFragment();

                                        }
                                    };
                                    mAuthBtn.setCallback(mAuthCallback);
                                    AuthConfig.Builder authConfigBuilder = new AuthConfig.Builder()
                                            .withAuthCallBack(mAuthCallback)
                                            .withPhoneNumber("+91" + Constant.registrationPojo.getPhone());
                                    Digits.authenticate(authConfigBuilder.build());
                                } else {
                                    mAuthCallback = new AuthCallback() {
                                        @Override
                                        public void success(DigitsSession session, String phoneNumber) {
                                            registrationPojo.setPassword(mEtpassword.getText().toString());
                                            Constant.FIREBASE_REF.child("person").child(registrationPojo.getUsername()).setValue(registrationPojo);
                                            Toast.makeText(getActivity(), "Password changed", Toast.LENGTH_SHORT).show();
                                            refreshFragment();
                                        }

                                        @Override
                                        public void failure(DigitsException exception) {
                                            Toast.makeText(getActivity(), "Failed to authenticate", Toast.LENGTH_SHORT).show();
                                            refreshFragment();

                                        }
                                    };
                                    mAuthBtn.setCallback(mAuthCallback);
                                    AuthConfig.Builder authConfigBuilder = new AuthConfig.Builder()
                                            .withAuthCallBack(mAuthCallback)
                                            .withPhoneNumber("+91" + registrationPojo.getPhone());
                                    Digits.authenticate(authConfigBuilder.build());

                                }
                            } else {
                                Toast.makeText(getActivity(), "username does not exists", Toast.LENGTH_LONG).show();

                            }

                        } else {
                            Toast.makeText(getActivity(), "username does not exists", Toast.LENGTH_LONG).show();
                        }
                    }
                } else
                    Toast.makeText(getActivity(), "No internet connection", Toast.LENGTH_SHORT).show();


            }
        });

    }

    public void getUser() {
        registrationPojo = null;
        if (registrationPojoList != null) {
            for (int i = 0; i < registrationPojoList.size(); i++) {
                if (mEtusername.getText().toString().equals(registrationPojoList.get(i).getUsername())) {
                    registrationPojo = registrationPojoList.get(i);
                }
            }


        }

    }


    private boolean validateDetails() {
        boolean passwordChanged = false;
        final String PASSWORD_PATTERN =
                "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";

        if (mEtusername.getText().toString().isEmpty()) {
            mEtusername.setError("User name can not be empty");
        } else if (mEtpassword.getText().toString().isEmpty()) {
            mEtpassword.setError("Password can not be empty");
        } else if (!mEtpassword.getText().toString().trim().matches(PASSWORD_PATTERN)) {
            mEtpassword.setError("Password must contain Minimum 8 characters at least 1 Uppercase Alphabet, 1 Lowercase Alphabet, 1 Number and 1 Special Character");

        } else if (mEtconfirmpwd.getText().toString().isEmpty()) {
            mEtconfirmpwd.setError("Confirm Password can not be empty");
        } else if (!mEtpassword.getText().toString().trim().equals(mEtconfirmpwd.getText().toString().trim())) {
            mEtconfirmpwd.setError("Password and Confirm password does not match");
        } else {
            passwordChanged = true;
        }
        return passwordChanged;
    }

    public void setList() {
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

        });


    }

    public void refreshFragment() {
        if (isSetting) {
            mEtconfirmpwd.setText("");
            mEtpassword.setText("");
        } else {
            mEtconfirmpwd.setText("");
            mEtpassword.setText("");
            mEtusername.setText("");
        }
    }
}
