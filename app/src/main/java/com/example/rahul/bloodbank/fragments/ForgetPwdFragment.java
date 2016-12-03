package com.example.rahul.bloodbank.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.rahul.bloodbank.R;
import com.example.rahul.bloodbank.constants.Constant;
import com.example.rahul.bloodbank.pojo.RegistrationPojo;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul on 11/21/2016.
 */

public class ForgetPwdFragment extends Fragment {
    EditText mEtusername, mEtpassword, mEtconfirmpwd;
    Button mBtforgotpwd;
    RelativeLayout mRlForgotPwd;
    List<RegistrationPojo> registrationPojoList;

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
            boolean isSetting = getArguments().getBoolean("isSetting");
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
        mBtforgotpwd = (Button) view.findViewById(R.id.bt_forgotpwd);
        mRlForgotPwd = (RelativeLayout) view.findViewById(R.id.rl_forgotpwd);
        registrationPojoList = new ArrayList<>();


        mBtforgotpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateDetails()) {
                    Constant.registrationPojo.setPassword(mEtpassword.getText().toString());
                    Constant.FIREBASE_REF.child("person").child(Constant.registrationPojo.getUsername()).setValue(Constant.registrationPojo);

                    Toast.makeText(getActivity(), "Password changed", Toast.LENGTH_SHORT).show();
                    getActivity().getSupportFragmentManager().popBackStack();
                } else {

                }
            }
        });

        mEtusername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (registrationPojoList != null) {

                    for (int i = 0; i < registrationPojoList.size(); i++) {
                        if (s.toString().equals(registrationPojoList.get(i).getUsername())) {
                            mEtusername.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_right_edittext), null, null, null);
                        mEtusername.setCompoundDrawablePadding(4);

                        } else {
                            mEtusername.setError("username does not exists");
                        }

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
}
