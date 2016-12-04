package com.example.rahul.bloodbank.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.Toast;

import com.example.rahul.bloodbank.R;
import com.example.rahul.bloodbank.constants.Constant;
import com.example.rahul.bloodbank.interfaces.Communicator;
import com.example.rahul.bloodbank.utils.Utils;

/**
 * Created by Rahul on 11/25/2016.
 */

public class ProfileFragment extends Fragment implements Communicator {
    EditText mEtEmail, mEtPhone, mEtAddress, mEtCity;
    CheckBox mCbDonor, mCbAcceptor;
    ImageButton mBtSaveEdit;
    String userType = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile__fragment, container, false);
        initialization(view);
        return view;
    }

    public void initialization(View view) {
        mEtAddress = (EditText) view.findViewById(R.id.et_address_profile);
        mEtEmail = (EditText) view.findViewById(R.id.et_mail_profile);
        mEtCity = (EditText) view.findViewById(R.id.et_city_profile);
        mEtPhone = (EditText) view.findViewById(R.id.et_mobile_profile);
        mCbDonor = (CheckBox) view.findViewById(R.id.cb_donor_profile);
        mCbAcceptor = (CheckBox) view.findViewById(R.id.cb_acceptor_profile);
        mBtSaveEdit = (ImageButton) view.findViewById(R.id.bt_edit_save_profile);


        mEtAddress.setText(Constant.registrationPojo.getAddress());
        mEtCity.setText(Constant.registrationPojo.getCity());
        mEtEmail.setText(Constant.registrationPojo.getEmail());
        mEtPhone.setText(Constant.registrationPojo.getPhone());
        userType = Constant.registrationPojo.getUserType();

        if (Constant.registrationPojo.getUserType().equals("Donor")) {
            mCbDonor.setChecked(true);
        } else if (Constant.registrationPojo.getUserType().equals("Acceptor")) {
            mCbAcceptor.setChecked(true);
        } else if (Constant.registrationPojo.getUserType().equals("Donor and Acceptor")) {
            mCbDonor.setChecked(true);
            mCbAcceptor.setChecked(true);
        }

        mEtAddress.setEnabled(false);
        mEtEmail.setEnabled(false);
        mEtCity.setEnabled(false);
        mEtPhone.setEnabled(false);
        mCbAcceptor.setEnabled(false);
        mCbDonor.setEnabled(false);

        mCbDonor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getUserType();
            }
        });
        mCbAcceptor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getUserType();
            }
        });
        mBtSaveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constant.editStatus) {
                    Constant.editStatus = false;
                    Constant.updateStatus = true;
                    mBtSaveEdit.setBackgroundResource(R.drawable.ic_save_profile);
                    mEtAddress.setEnabled(true);
                    mEtEmail.setEnabled(true);
                    mEtCity.setEnabled(true);
                    mEtPhone.setEnabled(true);
                    mCbAcceptor.setEnabled(true);
                    mCbDonor.setEnabled(true);
                } else {
                    Utils.hideKeyboard(v, getActivity().getApplicationContext());

                    updateProfile();
                }
            }
        });

    }

    private void getUserType() {
        if (mCbDonor.isChecked() && mCbAcceptor.isChecked()) {
            userType = "Donor and Acceptor";
        } else if (mCbDonor.isChecked()) {
            userType = "Donor";

        } else if (mCbAcceptor.isChecked()) {
            userType = "Acceptor";
        } else if (!mCbAcceptor.isChecked() && !mCbDonor.isChecked()) {
            userType = "";
        }

    }

    private boolean validateLogin() {
        boolean registerStatus = false;
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (mEtEmail.getText().toString().isEmpty()) {
            mEtEmail.setError("Email can not be empty");

        } else if (!mEtEmail.getText().toString().matches(emailPattern)) {
            mEtEmail.setError("Invalid Email");
        } else if (mEtPhone.getText().toString().isEmpty()) {
            mEtPhone.setError("Phone can not be empty");

        } else if (mEtPhone.getText().toString().length() != 10) {
            mEtPhone.setError("Please enter valid phone number");

        } else if (mEtAddress.getText().toString().isEmpty()) {
            mEtAddress.setError("Address can not be empty");

        } else if (mEtCity.getText().toString().isEmpty()) {
            mEtCity.setError("City can not be empty");

        } else if (userType.equals("")) {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Warning!")
                    .setMessage("Pleease select at least one category")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
        } else
            registerStatus = true;
        return registerStatus;
    }

    @Override
    public void updateProfile() {
        if (Utils.isNetworkAvailable(getActivity().getApplicationContext())) {

            if (validateLogin()) {
                Constant.registrationPojo.setCity(mEtCity.getText().toString().trim());
                Constant.registrationPojo.setEmail(mEtEmail.getText().toString().trim());
                Constant.registrationPojo.setPhone(mEtPhone.getText().toString().trim());
                Constant.registrationPojo.setAddress(mEtAddress.getText().toString().trim());
                Constant.registrationPojo.setUserType(userType);

                Constant.FIREBASE_REF.child("person").child(Constant.registrationPojo.getUsername()).setValue(Constant.registrationPojo);


                Constant.editStatus = true;
                mBtSaveEdit.setBackgroundResource(R.drawable.ic_edit_profile);
                mEtAddress.setEnabled(false);
                mEtEmail.setEnabled(false);
                mEtCity.setEnabled(false);
                mEtPhone.setEnabled(false);
                mCbAcceptor.setEnabled(false);
                mCbDonor.setEnabled(false);
                Constant.updateStatus = false;
            }
        } else Toast.makeText(getActivity(), "No internet connection", Toast.LENGTH_SHORT).show();

    }
}
