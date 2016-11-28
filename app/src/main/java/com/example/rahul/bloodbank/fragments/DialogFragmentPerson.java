package com.example.rahul.bloodbank.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rahul.bloodbank.R;
import com.example.rahul.bloodbank.pojo.RegistrationPojo;

/**
 * Created by Rahul on 11/27/2016.
 */

public class DialogFragmentPerson extends DialogFragment {
    TextView mTvName;
    ImageButton mBtCall, mBtEmail, mBtMesage;
    RegistrationPojo registrationPojo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragmnet_person, container,
                false);
        getDialog().setTitle("Detail - ");
        initializeViews(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registrationPojo = (RegistrationPojo) getArguments().getSerializable("object");
        if (registrationPojo != null) {
            mTvName.setText(registrationPojo.getName());
        }
    }

    private void initializeViews(View view) {
        mTvName = (TextView) view.findViewById(R.id.tv_name_dialog);
        mBtCall = (ImageButton) view.findViewById(R.id.bt_dialog_call);
        mBtEmail = (ImageButton) view.findViewById(R.id.bt_dialog_email);
        mBtMesage = (ImageButton) view.findViewById(R.id.bt_dialog_message);

        mBtCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (registrationPojo != null) {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + registrationPojo.getPhone()));
                    startActivity(callIntent);
                } else {
                    Toast.makeText(getActivity(), "can't open dial", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mBtEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (registrationPojo != null) {
                    Intent email = new Intent(Intent.ACTION_SEND);
                    email.putExtra(Intent.EXTRA_EMAIL, new String[]{registrationPojo.getEmail()});
                    email.putExtra(Intent.EXTRA_SUBJECT, "Regarding blood donation");
                    email.putExtra(Intent.EXTRA_TEXT, "Please contact me if you want to donate blood ");
                    email.setType("message/rfc822");
                    startActivity(Intent.createChooser(email, "Choose an Email client :"));
                } else {
                    Toast.makeText(getActivity(), "can't send message", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mBtMesage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (registrationPojo != null) {
                    Uri uri = Uri.parse("smsto:" + registrationPojo.getPhone());
                    Intent smsSIntent = new Intent(Intent.ACTION_SENDTO, uri);
                    smsSIntent.putExtra("sms_body", "Please contact me if you want to donate blood ");
                    try {
                        startActivity(smsSIntent);

                    } catch (Exception ex) {

                        Toast.makeText(getActivity(), "Your sms has failed...", Toast.LENGTH_LONG).show();
                        ex.printStackTrace();
                    }
                } else {
                    Toast.makeText(getActivity(), "can't send message", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
