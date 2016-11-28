package com.example.rahul.bloodbank.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rahul.bloodbank.R;
import com.example.rahul.bloodbank.constants.Constant;

/**
 * Created by Rahul on 11/25/2016.
 */

public class ProfileFragment extends Fragment {
    EditText mEtEmail, mEtPhone, mEtAddress, mEtCity;
    CheckBox mCbDonor, mCbAcceptor;
    ImageView mIvPhoto;
    ImageButton mBtSaveEdit;

    boolean editStatus = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile__fragment, container, false);
        initialization(view);
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        new AlertDialog.Builder(getActivity())
                .setTitle("Warning!")
                .setMessage("Do you want to save data?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        saveData();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();

    }

    private void saveData() {
        Toast.makeText(getActivity(), "Data saved successfully", Toast.LENGTH_SHORT).show();
    }

    public void initialization(View view) {


        mEtAddress = (EditText) view.findViewById(R.id.et_address_profile);
        mEtEmail = (EditText) view.findViewById(R.id.et_mail_profile);
        mEtCity = (EditText) view.findViewById(R.id.et_city_profile);
        mEtPhone = (EditText) view.findViewById(R.id.et_mobile_profile);
        mCbDonor = (CheckBox) view.findViewById(R.id.cb_donor_profile);
        mCbAcceptor = (CheckBox) view.findViewById(R.id.cb_acceptor_profile);
        mIvPhoto = (ImageView) view.findViewById(R.id.im_choose_profile);
        mBtSaveEdit = (ImageButton) view.findViewById(R.id.bt_edit_save_profile);


        mEtAddress.setText(Constant.registrationPojo.getAddress());
        mEtCity.setText(Constant.registrationPojo.getCity());
        mEtEmail.setText(Constant.registrationPojo.getEmail());
        mEtPhone.setText(Constant.registrationPojo.getPhone());
        mIvPhoto.setImageResource(R.drawable.user_browse_ic);

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
        mIvPhoto.setEnabled(false);
        mEtPhone.setEnabled(false);
        mCbAcceptor.setEnabled(false);
        mCbDonor.setEnabled(false);


        mBtSaveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editStatus) {
                    editStatus = false;
                    mBtSaveEdit.setBackgroundResource(R.drawable.ic_save_profile);
                    mEtAddress.setEnabled(true);
                    mEtEmail.setEnabled(true);
                    mEtCity.setEnabled(true);
                    mIvPhoto.setEnabled(true);
                    mEtPhone.setEnabled(true);
                    mCbAcceptor.setEnabled(true);
                    mCbDonor.setEnabled(true);
                } else {
                    editStatus = true;
                    mBtSaveEdit.setBackgroundResource(R.drawable.ic_edit_profile);
                    mEtAddress.setEnabled(false);
                    mEtEmail.setEnabled(false);
                    mEtCity.setEnabled(false);
                    mIvPhoto.setEnabled(false);
                    mEtPhone.setEnabled(false);
                    mCbAcceptor.setEnabled(false);
                    mCbDonor.setEnabled(false);

                    saveData();
                }
            }
        });

    }

}
