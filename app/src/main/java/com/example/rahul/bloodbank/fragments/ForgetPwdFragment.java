package com.example.rahul.bloodbank.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.rahul.bloodbank.R;

/**
 * Created by Rahul on 11/21/2016.
 */

public class ForgetPwdFragment extends Fragment{
    EditText mEtusername,mEtpassword,mEtconfirmpwd;
    Button mBtforgotpwd;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forget_pwd_fragment,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViews(view);
    }

    private void initializeViews(View view) {
        mEtusername = (EditText) view.findViewById(R.id.et_username_forgotpwd);
        mEtpassword = (EditText) view.findViewById(R.id.et_password_forgotpwd);
        mEtconfirmpwd = (EditText) view.findViewById(R.id.et_confirm_password_forgotpwd);
        mBtforgotpwd = (Button) view.findViewById(R.id.bt_forgotpwd);


        mBtforgotpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }
}
