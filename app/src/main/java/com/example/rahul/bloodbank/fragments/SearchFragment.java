package com.example.rahul.bloodbank.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import android.widget.Toast;

import com.example.rahul.bloodbank.R;
import com.example.rahul.bloodbank.adapters.CustomListAdapterSearch;
import com.example.rahul.bloodbank.constants.Constant;
import com.example.rahul.bloodbank.pojo.RegistrationPojo;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul on 11/25/2016.
 */

public class SearchFragment extends Fragment {
    EditText mEtCity;
    ImageButton mBtSearch;
    Spinner mSpBgroup;
    String bloodGroup;
    List<RegistrationPojo> registrationPojoList, finalList;
    ListView mListView;
    RelativeLayout mRlSearch;
    Button mBtBack;
    boolean isFound = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, container, false);
        initializeViews(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setPersonList();

    }

    private void initializeViews(View view) {
        mEtCity = (EditText) view.findViewById(R.id.et_city_search);
        mBtSearch = (ImageButton) view.findViewById(R.id.bt_search);
        mSpBgroup = (Spinner) view.findViewById(R.id.sp_btype_search);
        mListView = (ListView) view.findViewById(R.id.list_persons_search);
        mRlSearch = (RelativeLayout) view.findViewById(R.id.rl_search);
        mBtBack = (Button) view.findViewById(R.id.bt_back);

        registrationPojoList = new ArrayList<>();
        finalList = new ArrayList<>();

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
        mSpBgroup.setAdapter(dataAdapter);


        mSpBgroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    bloodGroup = bType.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mBtSearch.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             finalList = new ArrayList<RegistrationPojo>();

                                             if (mEtCity.getText().toString() != null && !mEtCity.getText().toString().isEmpty() && bloodGroup != null && !bloodGroup.equals("")) {
                                                 if (registrationPojoList != null) {
                                                     for (int i = 0; i < registrationPojoList.size(); i++) {
                                                         RegistrationPojo registrationPojo = registrationPojoList.get(i);
                                                         if (registrationPojo != null) {
                                                             if (mEtCity.getText().toString().trim().equals(registrationPojo.getCity()) && bloodGroup.equals(registrationPojo.getBgType())) {
                                                                 finalList.add(registrationPojo);
                                                             }
                                                         }

                                                     }
                                                     if (finalList != null) {
                                                         if (finalList.size() > 0) {
                                                             mRlSearch.setVisibility(View.GONE);
                                                             mListView.setVisibility(View.VISIBLE);
                                                             mBtBack.setVisibility(View.VISIBLE);
                                                             mEtCity.setText("");
                                                             mSpBgroup.setSelection(0);
                                                             mListView.setAdapter(new CustomListAdapterSearch(getActivity(), (ArrayList<RegistrationPojo>) finalList));

                                                         } else {
                                                             finalList = new ArrayList<RegistrationPojo>();
                                                             mEtCity.setText("");
                                                             mSpBgroup.setSelection(0);
                                                             mListView.setEmptyView(v.findViewById(R.id.empty));
                                                             mRlSearch.setVisibility(View.GONE);
                                                             mListView.setVisibility(View.VISIBLE);
                                                             mBtBack.setVisibility(View.VISIBLE);
                                                             mListView.setAdapter(new CustomListAdapterSearch(getActivity(), (ArrayList<RegistrationPojo>) finalList));

                                                             Toast.makeText(getActivity(), "no data found", Toast.LENGTH_SHORT).show();

                                                         }

                                                     }


                                                 }
                                             } else
                                                 Toast.makeText(getActivity(), "please enter needed values", Toast.LENGTH_SHORT).show();

                                         }
                                     }

        );

        mBtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRlSearch.setVisibility(View.VISIBLE);
                mListView.setVisibility(View.GONE);
                mBtBack.setVisibility(View.GONE);
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DialogFragmentPerson dFragment = new DialogFragmentPerson();
                Bundle args = new Bundle();
                args.putSerializable("object", finalList.get(position));
                dFragment.setArguments(args);
                dFragment.show(getActivity().getSupportFragmentManager(), "Dialog Fragment");
            }
        });

    }

    public void setPersonList() {
        Constant.FIREBASE_REF.child("person").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    RegistrationPojo registrationPojo = postSnapshot.getValue(RegistrationPojo.class);
                    if (!registrationPojo.getUsername().equals(Constant.registrationPojo.getUsername()))
                        registrationPojoList.add(registrationPojo);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        });


    }
}
