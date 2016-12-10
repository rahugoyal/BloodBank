package com.example.rahul.bloodbank.applications;

/**
 * Created by Rahul on 12/9/2016.
 */

import android.app.Application;

import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
import com.example.rahul.bloodbank.fragments.RegistrationFragment;
import com.example.rahul.bloodbank.interfaces.Communicator;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import io.fabric.sdk.android.Fabric;


public class DemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        TwitterAuthConfig authConfig = new TwitterAuthConfig("8XU9JSRsiHTqSFxU1vaVhailL", "ETWw3CTNxjQCkWJsCN7PJ9R2PTp0wBmcbctu2ALSiCoK7ZUZV6");
        Fabric.with(this, new TwitterCore(authConfig), new Digits.Builder().build());

    }


}
