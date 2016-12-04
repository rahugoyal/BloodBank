package com.example.rahul.bloodbank.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rahul.bloodbank.R;

/**
 * Created by Rahul on 11/28/2016.
 */

public class ContactUsFragment extends Fragment {
    TextView mNumber1, mNumber2, mEmail;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_us_fragment, container, false);
        initializeViews(view);
        return view;
    }

    private void initializeViews(View view) {
        mNumber1 = (TextView) view.findViewById(R.id.tv_contact_us_number1);
        mNumber2 = (TextView) view.findViewById(R.id.tv_contact_us_number2);
        mEmail = (TextView) view.findViewById(R.id.tv_contact_us_email);

        mNumber1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + "7416307478"));
                startActivity(callIntent);
            }
        });

        mNumber2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + "7354266244"));
                startActivity(callIntent);
            }
        });

        mEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"rahulgoyalsbl005@gmail.com"});
                email.putExtra(Intent.EXTRA_SUBJECT, "Regarding blood bak app");
                email.putExtra(Intent.EXTRA_TEXT, "Please contact me");
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose an Email client :"));

            }
        });
    }
}
