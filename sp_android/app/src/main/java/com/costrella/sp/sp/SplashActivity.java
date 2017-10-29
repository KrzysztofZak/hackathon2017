package com.costrella.sp.sp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        //Execution of method that gets app version definied in build.gradle
        getAppVersionCode();
        //Local variable for splash screen timeout in miliseconds
        int SPLASH_TIME_OUT = 1000;
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {

                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(i);
                // Close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    public int getAppVersionCode() {

        Context context = getApplicationContext();
        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();

        String myVersionName = "Version not available"; // initialize String

        try {
            myVersionName = packageManager.getPackageInfo(packageName, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        TextView tvVersionName = (TextView) findViewById(R.id.tv_VersionName);
        tvVersionName.setText(getString(R.string.app_name) + " " + myVersionName);

        return 0;
    }
}