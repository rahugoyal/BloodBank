package com.example.rahul.bloodbank.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.rahul.bloodbank.R;

/**
 * Created by Rahul on 11/20/2016.
 */

public class DashboardFragment extends Fragment {

    RelativeLayout mRlDonor, mRlAcceptor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dashboard_fragment, container, false);
        initializeViews(view);
        return view;
    }

    private void initializeViews(View view) {
        mRlDonor = (RelativeLayout) view.findViewById(R.id.rl_dashboard_bd);
        mRlAcceptor = (RelativeLayout) view.findViewById(R.id.rl_dashboard_ba);

        mRlDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.mainactivity_container, new BllodDonorFragment()).commit();

            }
        });
        mRlAcceptor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.mainactivity_container, new BllodAcceptorFragment()).commit();

            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
