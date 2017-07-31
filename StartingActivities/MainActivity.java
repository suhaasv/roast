package com.example.android.roast.Activities.StartingActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.roast.R;
import com.firebase.client.Firebase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);
    }
}
