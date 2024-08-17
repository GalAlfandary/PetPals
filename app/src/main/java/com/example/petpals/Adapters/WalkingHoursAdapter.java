package com.example.petpals.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petpals.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class WalkingHoursAdapter extends RecyclerView.Adapter<WalkingHoursAdapter.WalkingHoursViewHolder> {
    private ArrayList<String> walkingHours;

    public WalkingHoursAdapter(ArrayList<String> walkingHours) {
        this.walkingHours = walkingHours;
    }

    @NonNull
    @Override
    public WalkingHoursViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.walking_hours_item, parent, false);
        return new WalkingHoursViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WalkingHoursViewHolder holder, int position) {
        String hour = walkingHours.get(position);
        holder.walkingHour.setText(hour);
    }

    @Override
    public int getItemCount() {
        return walkingHours.size();
    }

    public static class WalkingHoursViewHolder extends RecyclerView.ViewHolder {
        MaterialTextView walkingHour;

        public WalkingHoursViewHolder(@NonNull View itemView) {
            super(itemView);
            walkingHour = itemView.findViewById(R.id.walking_hour);
        }
    }
}

