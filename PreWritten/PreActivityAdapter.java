package com.example.android.roast.Activities.PreWritten;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.roast.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by SuhaasVenkatesh on 5/2/17.
 * Adapter class for the prewritten display, as it uses a recycler view to display data. Allows
 * users to click on each roast as well, then going to the detail activity
 */

public class PreActivityAdapter extends RecyclerView.Adapter<PreActivityAdapter.ViewHolder> {
    ArrayList<String> roasts;

    public PreActivityAdapter(ArrayList<String> roasts) {
        this.roasts = roasts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // a LayoutInflater turns a layout XML resource into a View object.
        final View roastItem = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.roast_item, parent, false);
        return new ViewHolder(roastItem);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String roast = roasts.get(position);

        holder.roastDisplay.setText(roast);


        // Attach a click listener that launches a new Activity
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PreWrittenDetailActivity.class);
                intent.putExtra("ROAST", roast);

                v.getContext().startActivity(intent);


            }

        });
    }
    public void removeAt(int position, ArrayList<String> roastList) {
        roastList.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return roasts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView roastDisplay;


        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            roastDisplay = (TextView) itemView.findViewById(R.id.roastDisplay);

        }
    }
}