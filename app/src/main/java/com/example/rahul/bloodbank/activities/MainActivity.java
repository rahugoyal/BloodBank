package com.example.rahul.bloodbank.activities;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.rahul.bloodbank.R;
import com.example.rahul.bloodbank.fragments.DashboardFragment;


public class MainActivity extends AppCompatActivity {
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.mainactivity_toolbar);
        setSupportActionBar(mToolbar);

        getSupportFragmentManager().beginTransaction().add(R.id.mainactivity_container, new DashboardFragment()).commit();

    }

}


