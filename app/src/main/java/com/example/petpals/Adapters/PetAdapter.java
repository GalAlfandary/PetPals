package com.example.petpals.Adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.petpals.Interface.Callback_ListItemClicked;
import com.example.petpals.Models.Pet;
import com.example.petpals.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.PetViewHolder> {

    private final ArrayList<Pet> pets;
    private final ArrayList<String> petIds;  // Add a list to hold petIds
    private final Callback_ListItemClicked callbackListItemClicked;

    public PetAdapter(ArrayList<Pet> pets, ArrayList<String> petIds, Callback_ListItemClicked callbackListItemClicked) {
        this.pets = pets;
        this.petIds = petIds;  // Initialize the petIds list
        this.callbackListItemClicked = callbackListItemClicked;
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pet_item, parent, false);
        return new PetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
        Pet pet = pets.get(position);
        Uri imageUri = Uri.parse(pet.getImageUri());
        Glide
                .with(holder.itemView.getContext())
                .load(imageUri)
                .centerCrop()
                .placeholder(R.drawable.no_image_svgrepo_com)
                .into(holder.petImage);
        holder.petName.setText(pet.getName());
        holder.petAge.setText(String.valueOf(pet.getAge()));
        holder.petSex.setText(String.valueOf(pet.getSex()));

        holder.itemView.setOnClickListener(v -> {
            if (callbackListItemClicked != null) {
                String petId = petIds.get(position);  // Get the petId from the list based on position
                callbackListItemClicked.onListItemClicked(position, petId);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pets.size();
    }

    public static class PetViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView petName, petAge, petSex;
        ImageView petImage;

        public PetViewHolder(@NonNull View itemView) {
            super(itemView);
            petName = itemView.findViewById(R.id.pet_name);
            petAge = itemView.findViewById(R.id.pet_age);
            petSex = itemView.findViewById(R.id.pet_sex);
            petImage = itemView.findViewById(R.id.pet_img);
        }
    }
}
