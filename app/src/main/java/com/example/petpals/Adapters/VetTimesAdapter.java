package com.example.petpals.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petpals.Models.VetVisit;
import com.example.petpals.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class VetTimesAdapter extends RecyclerView.Adapter<VetTimesAdapter.VetVisitsViewHolder> {
    private ArrayList<VetVisit> vetVisits;

    public VetTimesAdapter(ArrayList<VetVisit> vetVisits) {
        this.vetVisits = vetVisits;
    }

    @NonNull
    @Override
    public VetVisitsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vet_times_item, parent, false);
        return new VetVisitsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VetVisitsViewHolder holder, int position) {
        VetVisit visit = vetVisits.get(position);
        holder.visitingDate.setText(visit.getVisitDate());
        holder.visitingHour.setText(visit.getVisitTime());
    }

    @Override
    public int getItemCount() {
        return vetVisits.size();
    }

    public static class VetVisitsViewHolder extends RecyclerView.ViewHolder {
        MaterialTextView visitingDate, visitingHour;

        public VetVisitsViewHolder(@NonNull View itemView) {
            super(itemView);
            visitingDate = itemView.findViewById(R.id.visiting_date);
            visitingHour = itemView.findViewById(R.id.visiting_hour);
        }
    }
}

