package com.example.rahul.bloodbank.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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

public class BllodDonorFragment extends Fragment {
    TextView mTvName, mTvBloodGroup, mTvPhone, mTvEmail, mTvCity;
    Button mBtDontateStatus;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.blood_donor_fragment, container, false);
        initializeViews(view);
        return view;
    }

    private void initializeViews(View view) {
        mTvName = (TextView) view.findViewById(R.id.tv_bd_name);
        mTvBloodGroup = (TextView) view.findViewById(R.id.tv_bd_bloodgroup);
        mTvCity = (TextView) view.findViewById(R.id.tv_bd_city);
        mTvEmail = (TextView) view.findViewById(R.id.tv_bd_email);
        mTvPhone = (TextView) view.findViewById(R.id.tv_bd_phone);
        mBtDontateStatus = (Button) view.findViewById(R.id.bt_donor_status);

        mTvCity.setText("City : " + Constant.registrationPojo.getCity());
        mTvBloodGroup.setText("BloodGroup : " + Constant.registrationPojo.getBgType());
        mTvPhone.setText("Phone : " + Constant.registrationPojo.getPhone());
        mTvName.setText("Name : " + Constant.registrationPojo.getName());
        mTvEmail.setText("Email : " + Constant.registrationPojo.getEmail());

        if (Constant.registrationPojo.isDonorStatus()) {
            mBtDontateStatus.setText("Not Now");
        } else {
            mBtDontateStatus.setText("Ready to donate");
        }

        mBtDontateStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBtDontateStatus.getText().equals("Ready to donate")) {
                    mBtDontateStatus.setText("Not Now");
                    Constant.registrationPojo.setDonorStatus(true);
                    Constant.FIREBASE_REF.child("person").child(Constant.registrationPojo.getUsername()).setValue(Constant.registrationPojo);

                } else {
                    Constant.registrationPojo.setDonorStatus(false);
                    mBtDontateStatus.setText("Ready to donate");
                    Constant.FIREBASE_REF.child("person").child(Constant.registrationPojo.getUsername()).setValue(Constant.registrationPojo);
                }
            }
        });

    }


}
