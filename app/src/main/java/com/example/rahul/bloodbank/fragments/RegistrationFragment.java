package com.example.rahul.bloodbank.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
import com.example.rahul.bloodbank.interfaces.Communicator;
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

public class RegistrationFragment extends Fragment {
    EditText mEtName, mEtEmail, mEtPhone, mEtAddress, mEtCity, mEtUsername, mEtPwd;
    Button mBtRegister;
    DigitsAuthButton mAuthbtn;
    CheckBox mCbDonor, mCbAcceptor;
    Spinner mSpBType;
    RadioButton mRbMale, mRbFemale;
    RadioGroup mRgGender;
    String gender = "", bloodGroupType = "", userType = "";
    List<RegistrationPojo> registrationPojoList;
    boolean isUserExists = false, isEmailExists = false;
    RegistrationPojo registrationPojoUser, registrationPojoEmail;
    CharSequence username = "", email = "";
    RegistrationPojo registrationPojo = null;
    AuthCallback mAuthCallback;


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
        setList();

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
        registrationPojoList = new ArrayList<>();

        mAuthbtn = (DigitsAuthButton) view.findViewById(R.id.bt_auth_registration);
        mAuthbtn.setText("Authentication");


        mRbMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    gender = "male";
                } else {
                    gender = "";
                }
            }
        });
        mRbFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    gender = "female";
                } else {
                    gender = "";
                }
            }
        });
        final List<String> bType = new ArrayList<String>();
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


        mSpBType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    bloodGroupType = bType.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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

        mBtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Utils.isNetworkAvailable(getActivity().getApplicationContext())) {
                    Utils.hideKeyboard(v, getActivity().getApplicationContext());
                    Digits.clearActiveSession();

                    if (validateLogin()) {
                        registrationPojo = new RegistrationPojo();

                        registrationPojo.setName(mEtName.getText().toString());
                        registrationPojo.setGender(gender);
                        registrationPojo.setEmail(mEtEmail.getText().toString());
                        registrationPojo.setPhone(mEtPhone.getText().toString());
                        registrationPojo.setAddress(mEtAddress.getText().toString());
                        registrationPojo.setCity(mEtCity.getText().toString());
                        registrationPojo.setUsername(mEtUsername.getText().toString());
                        registrationPojo.setPassword(mEtPwd.getText().toString());
                        registrationPojo.setAcceptorStatus(false);
                        registrationPojo.setDonorStatus(false);
                        registrationPojo.setBgType(bloodGroupType);
                        registrationPojo.setUserType(userType);
                        mAuthCallback = new AuthCallback() {
                            @Override
                            public void success(DigitsSession session, String phoneNumber) {

                                Constant.FIREBASE_REF.child("person").child(registrationPojo.getUsername()).setValue(registrationPojo);
                                Toast.makeText(getActivity(), "Register successfully", Toast.LENGTH_SHORT).show();
                                refreshProfileFragment();
                            }

                            @Override
                            public void failure(DigitsException exception) {
                                Toast.makeText(getActivity(), "Failed to authenticate", Toast.LENGTH_SHORT).show();
                                refreshProfileFragment();
                            }
                        };
                        mAuthbtn.setCallback(mAuthCallback);
                        AuthConfig.Builder authConfigBuilder = new AuthConfig.Builder()
                                .withAuthCallBack(mAuthCallback)
                                .withPhoneNumber("+91" + registrationPojo.getPhone());
                        Digits.authenticate(authConfigBuilder.build());

                    }

                } else {
                    Toast.makeText(getActivity(), "No internet connection", Toast.LENGTH_SHORT).show();

                }
            }
        });

        mEtUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                username = s;
                if (registrationPojoList != null) {
                    for (int i = 0; i < registrationPojoList.size(); i++) {
                        if (s.toString().equals(registrationPojoList.get(i).getUsername())) {
                            registrationPojoUser = registrationPojoList.get(i);
                            mEtUsername.setError("username exists , please use different username");
                        }

                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        mEtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (registrationPojoList != null) {
                    email = s;
                    if (registrationPojoList != null) {
                        for (int i = 0; i < registrationPojoList.size(); i++) {
                            if (s.toString().equals(registrationPojoList.get(i).getEmail())) {
                                registrationPojoEmail = registrationPojoList.get(i);
                                mEtEmail.setError("email exists , please use different email");
                            }

                        }

                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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

    private void getUserType() {
        if (mCbDonor.isChecked() && mCbAcceptor.isChecked()) {
            userType = "Donor and Acceptor";
        } else if (mCbDonor.isChecked()) {
            userType = "Donor";

        } else if (mCbAcceptor.isChecked()) {
            userType = "Acceptor";
        }

    }

    private boolean validateLogin() {
        if (registrationPojoUser != null) {
            if (registrationPojoUser.getUsername().equals(username.toString())) {
                isUserExists = true;
            } else isUserExists = false;
        }
        if (registrationPojoEmail != null) {
            if (registrationPojoEmail.getEmail().equals(email.toString())) {
                isEmailExists = true;
            } else isEmailExists = false;
        }

        boolean registerStatus = false;
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        final String PASSWORD_PATTERN =
                "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
        if (mEtName.getText().toString().isEmpty()) {
            mEtName.setError("Name can not be empty");

        } else if (gender.isEmpty() || gender.equals("")) {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Warning!")
                    .setMessage("Pleease select Gender")
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

        } else if (mEtEmail.getText().toString().isEmpty()) {
            mEtEmail.setError("Email can not be empty");

        } else if (!mEtEmail.getText().toString().matches(emailPattern)) {
            mEtEmail.setError("Invalid Email");
        } else if (isEmailExists) {
            mEtEmail.setError("email exists , please use different email");

        } else if (mEtPhone.getText().toString().isEmpty()) {
            mEtPhone.setError("Phone can not be empty");

        } else if (mEtPhone.getText().toString().length() != 10) {
            mEtPhone.setError("Please enter valid phone number");

        } else if (mEtAddress.getText().toString().isEmpty()) {
            mEtAddress.setError("Address can not be empty");

        } else if (mEtCity.getText().toString().isEmpty()) {
            mEtCity.setError("City can not be empty");

        } else if (mEtUsername.getText().toString().isEmpty()) {
            mEtUsername.setError("Username can not be empty");

        } else if (isUserExists) {
            mEtUsername.setError("username exists , please use different username");

        } else if (mEtPwd.getText().toString().isEmpty()) {
            mEtPwd.setError("Password can not be empty");
        } else if (!mEtPwd.getText().toString().matches(PASSWORD_PATTERN)) {
            mEtPwd.setError("Password must contain Minimum 8 characters at least 1 Uppercase Alphabet, 1 Lowercase Alphabet, 1 Number and 1 Special Character");
        } else if (bloodGroupType.isEmpty()) {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Warning!")
                    .setMessage("Pleease select Blood Group")
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

        } else if (!mCbAcceptor.isChecked() && !mCbDonor.isChecked()) {
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

    public void refreshProfileFragment() {
        mEtAddress.setText("");
        mEtCity.setText("");
        mEtName.setText("");
        mRbFemale.setSelected(false);
        mRbMale.setSelected(false);
        mEtEmail.setText("");
        mEtPhone.setText("");
        mEtUsername.setText("");
        mEtPwd.setText("");
        mSpBType.setSelection(0);
        mCbAcceptor.setChecked(false);
        mCbDonor.setChecked(false);
        registrationPojo = null;

    }
}
