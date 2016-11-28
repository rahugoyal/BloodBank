package com.example.rahul.bloodbank.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.rahul.bloodbank.R;
import com.example.rahul.bloodbank.constants.Constant;

/**
 * Created by Rahul on 11/26/2016.
 */

public class BllodAcceptorFragment extends Fragment {
    TextView mTvName, mTvBloodGroup, mTvPhone, mTvEmail, mTvCity;
    Button mBtAcceptStatus;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.blood_acceptor_fragment, container, false);
        initializeViews(view);
        return view;
    }

    private void initializeViews(View view) {
        mTvName = (TextView) view.findViewById(R.id.tv_ba_name);
        mTvBloodGroup = (TextView) view.findViewById(R.id.tv_ba_bloodgroup);
        mTvCity = (TextView) view.findViewById(R.id.tv_ba_city);
        mTvEmail = (TextView) view.findViewById(R.id.tv_ba_email);
        mTvPhone = (TextView) view.findViewById(R.id.tv_ba_phone);
        mBtAcceptStatus = (Button) view.findViewById(R.id.bt_acceptor_status);

        mTvCity.setText("City : " + Constant.registrationPojo.getCity());
        mTvBloodGroup.setText("BloodGroup : " + Constant.registrationPojo.getBgType());
        mTvPhone.setText("Phone : " + Constant.registrationPojo.getPhone());
        mTvName.setText("Name : " + Constant.registrationPojo.getName());
        mTvEmail.setText("Email : " + Constant.registrationPojo.getEmail());

        mBtAcceptStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBtAcceptStatus.getText().equals("Ready to Accept")) {
                    mBtAcceptStatus.setText("Not Now");
                } else {
                    mBtAcceptStatus.setText("Ready to Accept");
                }
            }
        });

    }

}
