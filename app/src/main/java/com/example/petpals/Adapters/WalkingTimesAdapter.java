package com.example.petpals.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petpals.Interface.WalkingTimesListener;
import com.example.petpals.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class WalkingTimesAdapter extends RecyclerView.Adapter<WalkingTimesAdapter.WalkingTimeViewHolder> {

    private final ArrayList<String> walkingTimes;
    private final WalkingTimesListener walkingTimesListener;

    public WalkingTimesAdapter(ArrayList<String> walkingTimes, WalkingTimesListener walkingTimesListener) {
        this.walkingTimes = walkingTimes;
        this.walkingTimesListener = walkingTimesListener;
    }

    public ArrayList<String> getWalkingTimes() {
        return walkingTimes;
    }

    @NonNull
    @Override
    public WalkingTimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.walking_times_item, parent, false);
        return new WalkingTimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WalkingTimeViewHolder holder, int position) {
        String walkingTime = walkingTimes.get(position);
        holder.time.setText(walkingTime);

        holder.clockButton.setOnClickListener(v -> walkingTimesListener.onClockButtonClick(position, walkingTime));
        holder.deleteButton.setOnClickListener(v -> walkingTimesListener.onDeleteButtonClick(position, walkingTime));
    }

    @Override
    public int getItemCount() {
        return walkingTimes.size();
    }

    public static class WalkingTimeViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView time;
        MaterialButton clockButton;
        ImageButton deleteButton;

        public WalkingTimeViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.hour_visit);
            clockButton = itemView.findViewById(R.id.clock_btn);
            deleteButton = itemView.findViewById(R.id.delete_btn);
        }
    }
}
