package com.example.rahul.bloodbank.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.rahul.bloodbank.R;

/**
 * Created by Rahul on 11/25/2016.
 */

public class SettingFragment extends Fragment {
    LinearLayout mLlChangePwd, mLlDeleteAc, mLlAbout, mLlContactUs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_fragment, container, false);
        initializeViews(view);
        return view;
    }

    private void initializeViews(View view) {
        mLlChangePwd = (LinearLayout) view.findViewById(R.id.ll_changepwd_setting);
        mLlDeleteAc = (LinearLayout) view.findViewById(R.id.ll_delete_setting);
        mLlContactUs = (LinearLayout) view.findViewById(R.id.ll_contact_setting);
        mLlAbout = (LinearLayout) view.findViewById(R.id.ll_about_setting);

        mLlChangePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgetPwdFragment forgetPwdFragment = new ForgetPwdFragment();
                Bundle args = new Bundle();
                args.putBoolean("isSetting", true);
                forgetPwdFragment.setArguments(args);
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.mainactivity_container, forgetPwdFragment).commit();
            }
        });

        mLlDeleteAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Warning!")
                        .setMessage("Do you want to delete account?")
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

            }
        });


        mLlContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.mainactivity_container, new ContactUsFragment()).commit();

            }
        });

        mLlAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.mainactivity_container, new AboutFragment()).commit();

            }
        });
    }
}
