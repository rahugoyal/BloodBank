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

import com.example.rahul.bloodbank.R;

/**
 * Created by Rahul on 11/21/2016.
 */

public class ForgetPwdFragment extends Fragment {
    EditText mEtusername, mEtpassword, mEtconfirmpwd;
    Button mBtforgotpwd;
    RelativeLayout mRlForgotPwd;

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
        if (getArguments() != null) {
            boolean isSetting = getArguments().getBoolean("isSetting");
            if (isSetting) {
                mRlForgotPwd.setBackgroundResource(R.drawable.im_dashboard_bg);
            }
        }

    }

    private void initializeViews(View view) {
        mEtusername = (EditText) view.findViewById(R.id.et_username_forgotpwd);
        mEtpassword = (EditText) view.findViewById(R.id.et_password_forgotpwd);
        mEtconfirmpwd = (EditText) view.findViewById(R.id.et_confirm_password_forgotpwd);
        mBtforgotpwd = (Button) view.findViewById(R.id.bt_forgotpwd);
        mRlForgotPwd = (RelativeLayout) view.findViewById(R.id.rl_forgotpwd);


        mBtforgotpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateDetails()) {
                    Toast.makeText(getActivity(), "Password changed", Toast.LENGTH_SHORT).show();
                    getActivity().getSupportFragmentManager().popBackStack();
                } else {

                }
            }
        });
    }

    private boolean validateDetails() {
        boolean passwordChanged = false;
        if (mEtusername.getText().toString().isEmpty()) {
            mEtusername.setError("User name can not be empty");
        } else if (mEtpassword.getText().toString().isEmpty()) {
            mEtpassword.setError("Password can not be empty");
        } else if (mEtconfirmpwd.getText().toString().isEmpty()) {
            mEtconfirmpwd.setError("Confirm Password can not be empty");
        } else if (!mEtpassword.equals(mEtconfirmpwd)) {
            mEtconfirmpwd.setError("Password and Confirm password does not match");
        } else {
            passwordChanged = true;
        }
        return passwordChanged;
    }
}
