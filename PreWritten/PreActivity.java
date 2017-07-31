package com.example.android.roast.Activities.PreWritten;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.roast.R;

import java.util.ArrayList;


/**
 * Created by svnktsh3 on 4/23/17.
 * Displays the pre written roasts, and allows users to click on them, then allowing them to favorite
 * or copy them.
 */

public class PreActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ArrayList<String> roasts = new ArrayList<>();
    private PreActivityAdapter mPreAdapter;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pre_written);
        final String [] roastArray ={"Lopsided head boy", "Your nose looks like a confident pizza roll",
                "Your hair looks like burnt hamburger meat", "Your classes aren't packaged",
                "You're ugly", "Boyy your shoes! what are those the george washington 1s?",
                "Your grandma probably codes better than you",
                "You look like baljeet from Phineas and Ferb",
                "You're built like a log", "Alex your code isn't even that good (jk you're a god)"};
        for (int i = 0; i < roastArray.length; i++) {
            roasts.add(roastArray[i]);
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager
                (new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mPreAdapter = new PreActivityAdapter(roasts);
        mRecyclerView.setAdapter(mPreAdapter);






    }
}
