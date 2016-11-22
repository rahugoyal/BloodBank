package com.example.rahul.bloodbank.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rahul.bloodbank.R;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Rahul on 11/21/2016.
 */

public class RegistrationFragment extends Fragment {
    EditText mEtName, mEtEmail, mEtPhone, mEtAddress, mEtCity, mEtUsername, mEtPwd;
    Button mBtRegister;
    CheckBox mCbDonor, mCbAcceptor;
    ImageView mIvPhoto;
    Spinner mSpBType;
    RadioButton mRbMale, mRbFemale;
    RadioGroup mRgGender;

    int PICK_IMAGE = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.registration_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViews(view);

    }

    private void initializeViews(View view) {
        mEtName = (EditText) view.findViewById(R.id.et_name_registration);
        mEtAddress = (EditText) view.findViewById(R.id.et_address_registration);
        mEtEmail = (EditText) view.findViewById(R.id.et_mail_registration);
        mEtCity = (EditText) view.findViewById(R.id.et_city_registration);
        mEtPhone = (EditText) view.findViewById(R.id.et_mobile_registration);
        mEtUsername = (EditText) view.findViewById(R.id.et_username_registration);
        mEtPwd = (EditText) view.findViewById(R.id.et_password_registration);
        mBtRegister = (Button) view.findViewById(R.id.bt_registration);
        mCbDonor = (CheckBox) view.findViewById(R.id.cb_donor_registration);
        mCbAcceptor = (CheckBox) view.findViewById(R.id.cb_acceptor_registration);
        mRbMale = (RadioButton) view.findViewById(R.id.rb_male_registration);
        mRbFemale = (RadioButton) view.findViewById(R.id.rb_female_registration);
        mRgGender = (RadioGroup) view.findViewById(R.id.rg_gender_registration);
        mSpBType = (Spinner) view.findViewById(R.id.sp_btype_registration);
        mIvPhoto = (ImageView) view.findViewById(R.id.im_choose_registration);


        List<String> bType = new ArrayList<String>();
        bType.add("Select Blood Group");
        bType.add("A+");
        bType.add("A-");
        bType.add("B+");
        bType.add("B-");
        bType.add("o+");
        bType.add("o-");
        bType.add("AB+");
        bType.add("AB-");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, bType);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpBType.setAdapter(dataAdapter);


        int genderId = mRgGender.getCheckedRadioButtonId();
        if (genderId == 0) {

        } else {

        }

        mIvPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);

            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                mIvPhoto.setImageURI(uri);
            } else {
                Toast.makeText(getActivity(), "No Image selected", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
