package com.example.petpals.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petpals.Models.VetVisit;
import com.example.petpals.Interface.ItemClickListener;
import com.example.petpals.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class VetVisitAdapter extends RecyclerView.Adapter<VetVisitAdapter.VetVisitViewHolder> {

    private final List<VetVisit> vetVisits;
    private final ItemClickListener itemClickListener;

    public VetVisitAdapter(List<VetVisit> vetVisits, ItemClickListener itemClickListener) {
        this.vetVisits = vetVisits;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public VetVisitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.times_item, parent, false);
        return new VetVisitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VetVisitViewHolder holder, int position) {
        VetVisit vetVisit = vetVisits.get(position);
        holder.date.setText(vetVisit.getVisitDate());
        holder.time.setText(vetVisit.getVisitTime());

        holder.calendarButton.setOnClickListener(v -> itemClickListener.onCalendarButtonClick(position, vetVisit));
        holder.deleteButton.setOnClickListener(v -> itemClickListener.onDeleteButtonClick(position, vetVisit));
    }

    @Override
    public int getItemCount() {
        return vetVisits.size();
    }

    public static class VetVisitViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView date, time;
        MaterialButton calendarButton;
        ImageButton deleteButton;

        public VetVisitViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.hour_visit);
            calendarButton = itemView.findViewById(R.id.calendar_btn);
            deleteButton = itemView.findViewById(R.id.delete_btn);
        }
    }
}
