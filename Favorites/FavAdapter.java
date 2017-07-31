package com.example.android.roast.Activities.Favorites;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.roast.R;

import java.util.ArrayList;

/**
 * Created by svnktsh3 on 5/2/17.
 * adapter class for the list view for the favorites list
 */

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder> {
    ArrayList<String> favorites;

    public FavAdapter(ArrayList<String> favorites) {
        this.favorites = favorites;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // a LayoutInflater turns a layout XML resource into a View object.
        final View favItem = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.fav_item, parent, false);
        return new ViewHolder(favItem);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String roast = favorites.get(position);

        holder.favDisplay.setText(roast);


    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView favDisplay;


        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            favDisplay = (TextView) itemView.findViewById(R.id.favDisplay);

        }
    }

}