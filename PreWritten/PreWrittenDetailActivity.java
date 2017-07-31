package com.example.android.roast.Activities.PreWritten;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.roast.Activities.Favorites.FavActivity;
import com.example.android.roast.R;

/**
 * Created by svnktsh3 on 4/29/17.
 * Activity that displays the roast and buttons to copy and/or favorite a given roast
 */

public class PreWrittenDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prewritten_detail);
        TextView roastView = (TextView) findViewById(R.id.roastDisplay);
        roastView.setText(intent.getStringExtra("ROAST"));
        final TextView newRoast = new TextView(this);
        newRoast.setText(intent.getStringExtra("ROAST"));

        Button copy = (Button) findViewById(R.id.copyButton);
        copy.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView roastDisplay = (TextView) findViewById(R.id.roastDisplay);
                String roast = roastDisplay.getText().toString();
                //some code taken from stackoverflow, handles the clipboard
                ClipboardManager clipboard = (ClipboardManager)
                        getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("roast", roast);
                clipboard.setPrimaryClip(clip);
                //displays success message
                Toast.makeText(getApplicationContext(), "Copied to Clipboard!",
                        Toast.LENGTH_SHORT).show();
            }
        });

        Button favorite = (Button) findViewById(R.id.favButton);
        favorite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent Intent = new Intent(v.getContext(), FavActivity.class);
                Intent.putExtra("newFavRoast", newRoast.getText().toString());
                v.getContext().startActivity(Intent);
            }

        });

        Button deleteFav = (Button) findViewById(R.id.deleteFav);
        deleteFav.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent Intent = new Intent(v.getContext(), FavActivity.class);
                Intent.putExtra("deleteRoast", newRoast.getText().toString());
                v.getContext().startActivity(Intent);
            }

        });
    }
}
