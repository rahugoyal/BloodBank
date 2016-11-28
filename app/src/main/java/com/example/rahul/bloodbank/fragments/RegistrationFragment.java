package com.example.rahul.bloodbank.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import com.example.rahul.bloodbank.R;
import com.example.rahul.bloodbank.constants.Constant;
import com.example.rahul.bloodbank.pojo.RegistrationPojo;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
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
    String gender = "", bloodGroupType = "", userType = "";
    Uri uri;
    byte[] encodedImage;

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
        mRgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == 1) {
                    gender = "male";
                } else {
                    gender = "female";
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


        mIvPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);

            }
        });

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
                if (validateLogin()) {
                    RegistrationPojo registrationPojo = new RegistrationPojo();

                    registrationPojo.setName(mEtName.getText().toString());
                    registrationPojo.setGender(gender);
                    registrationPojo.setEmail(mEtEmail.getText().toString());
                    registrationPojo.setPhone(mEtPhone.getText().toString());
                    registrationPojo.setAddress(mEtAddress.getText().toString());
                    registrationPojo.setCity(mEtCity.getText().toString());
                    registrationPojo.setUsername(mEtUsername.getText().toString());
                    registrationPojo.setPassword(mEtPwd.getText().toString());


                    if (encodedImage != null) {
                        String decoded = null;
                        try {
                            decoded = new String(encodedImage, "ISO-8859-1");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        registrationPojo.setPhoto(decoded);
                    } else
                        registrationPojo.setPhoto(null);

                    registrationPojo.setBgType(bloodGroupType);
                    registrationPojo.setUserType(userType);

                    Constant.FIREBASE_REF.child("person").child(registrationPojo.getUsername()).setValue(registrationPojo);

                    Toast.makeText(getActivity(), "Register successfully", Toast.LENGTH_SHORT).show();

                    getActivity().getSupportFragmentManager().popBackStack();
                } else {
                    Toast.makeText(getActivity(), "Not Registered, due to internal error", Toast.LENGTH_SHORT).show();

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
        }

    }

    private boolean validateLogin() {
        boolean registerStatus = false;
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        final String PASSWORD_PATTERN =
                "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
        if (mEtName.getText().toString().isEmpty()) {
            mEtName.setError("Name can not be empty");

        } else if (gender.isEmpty()) {
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
            ;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            if (data != null) {
                uri = data.getData();

                //base 64
                final InputStream imageStream;
                try {
                    imageStream = getActivity().getContentResolver().openInputStream(uri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                    encodedImage = encodeImage(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


                mIvPhoto.setImageURI(uri);
            } else {
                Toast.makeText(getActivity(), "No Image selected", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public byte[] encodeImage(Bitmap bitmap) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
//    private Bitmap encodeImage(Bitmap bm) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
//        byte[] b = baos.toByteArray();
//
//        Bitmap encImage = BitmapFactory.decodeByteArray(b, 0, b.length);
//
//
//        return encImage;
//    }
}
