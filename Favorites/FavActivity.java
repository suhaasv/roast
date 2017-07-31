package com.example.android.roast.Activities.Favorites;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.roast.Activities.PreWritten.PreActivityAdapter;
import com.example.android.roast.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by svnktsh3 on 4/23/17.
 * Displays the roasts you have favorited, and adds new roasts accordingly.
 */

public class FavActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ArrayList<String> favorites = new ArrayList<>();
    private PreActivityAdapter mFavAdapter;
    private SharedPreferences prefs;

    public static final String MyPREFERENCES = "myprefs";
    public static final String value = "key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String newFav = intent.getStringExtra("newFavRoast");
        String deleteRoast = intent.getStringExtra("deleteRoast");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fav_layout);

        prefs = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Set<String> set = prefs.getStringSet("favorites", null);
        favorites = new ArrayList<String>(set);
        boolean added = false;


        Button create = (Button) findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());

                final EditText newFavorite = new EditText(v.getContext());

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(newFavorite);

                // set dialog message
                alertDialogBuilder.setCancelable(true).setPositiveButton
                        ("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String addFav = newFavorite.getText().toString();
                                boolean newAdded = false;
                                String favorite = "";
                                if (addFav != null) {
                                    for (int i = 0; i < favorites.size(); i++) {
                                        favorite = favorites.get(i);
                                        if (favorite.equalsIgnoreCase(addFav)) {
                                            Toast.makeText(getApplicationContext(),
                                                    "Already in favorites!",
                                                    Toast.LENGTH_SHORT).show();
                                            newAdded = true;
                                        }
                                    }
                                    if (newAdded == false) {
                                        favorites.add(addFav);

                                    }
                                }
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
            }

        });

        if (newFav != null) {
            String favorite = "";
            for (int i = 0; i < favorites.size(); i++) {
                favorite = favorites.get(i);
                if (favorite.equalsIgnoreCase(newFav)) {
                    Toast.makeText(getApplicationContext(), "Already in favorites!",
                            Toast.LENGTH_SHORT).show();
                    added = true;
                }
            }
            if (added == false) {
                favorites.add(newFav);

            }
        }

        if(deleteRoast != null) {
            Toast.makeText(getApplicationContext(), "not null",
                    Toast.LENGTH_SHORT).show();

            for (int i = 0; i < favorites.size(); i++) {
                if(favorites.get(i).equals(deleteRoast)) {
                    favorites.remove(i);
                    mFavAdapter = new PreActivityAdapter(favorites);
                    mFavAdapter.notifyDataSetChanged();
                    mFavAdapter.notifyItemRangeChanged(i, favorites.size());
                    break;
                }
            }


        }

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager
                (new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mFavAdapter = new PreActivityAdapter(favorites);
        mRecyclerView.setAdapter(mFavAdapter);
    }

    @Override
    public void onBackPressed() {
        prefs = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();

        Set<String> set = new HashSet<String>();
        set.addAll(favorites);
        edit.putStringSet("favorites", set);
        edit.commit();

        super.onBackPressed();

    }


    @Override
    public void onResume() {
        super.onResume();
        //prefs = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        //Set<String> set = prefs.getStringSet("favorites", null);
        //favorites = new ArrayList<String>(set);

    }

}
