package com.example.petpals;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.petpals.Adapters.VetTimesAdapter;
import com.example.petpals.Adapters.WalkingHoursAdapter;
import com.example.petpals.Models.Pet;
import com.example.petpals.Models.VetVisit;
import com.example.petpals.Models.WalkingDay;
import com.example.petpals.Utilities.SignalManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.android.material.textview.MaterialTextView;
import com.saadahmedev.popupdialog.PopupDialog;
import com.saadahmedev.popupdialog.listener.StandardDialogActionListener;

import java.util.ArrayList;

public class PetInfoActivity extends AppCompatActivity {

    private Pet pet;
    private MaterialTextView petName, petAge, petSex, no_walking_times, no_vet_visits;
    private ShapeableImageView sunday, monday, tuesday, wednesday, thursday, friday, saturday;
    private AppCompatImageView pet_info_img;
    private RecyclerView walking_hours, vet_times;
    private MaterialButton edit_general_btn, edit_walking_btn, edit_vet_btn, delete_pet;
    private LinearLayout week_days_sec;
    private String petId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_info);
        findViews();

        petId = getIntent().getStringExtra("petId");
//        if (petId != null) {
//            loadPetFromDatabase(petId);
//        } else {
//            Log.e("PetInfoActivity", "No petId provided in the intent");
//            finish();
//        }
    }

    private void findViews() {
        pet_info_img = findViewById(R.id.pet_info_img);
        petName = findViewById(R.id.pet_name);
        petAge = findViewById(R.id.pet_age);
        petSex = findViewById(R.id.pet_sex);
        sunday = findViewById(R.id.sunday);
        monday = findViewById(R.id.monday);
        tuesday = findViewById(R.id.tuesday);
        wednesday = findViewById(R.id.wednesday);
        thursday = findViewById(R.id.thursday);
        friday = findViewById(R.id.friday);
        saturday = findViewById(R.id.saturday);
        walking_hours = findViewById(R.id.walking_hours);
        vet_times = findViewById(R.id.vet_times);
        edit_vet_btn = findViewById(R.id.edit_vet_btn);
        edit_general_btn = findViewById(R.id.edit_general_btn);
        edit_walking_btn = findViewById(R.id.edit_walking_btn);
        delete_pet = findViewById(R.id.delete_pet);
        no_walking_times = findViewById(R.id.no_walking_times);
        no_vet_visits = findViewById(R.id.no_vet_visits);
        week_days_sec = findViewById(R.id.week_days_sec);
    }

    private void loadPetFromDatabase(String petId) {
        DatabaseReference petRef = FirebaseDatabase.getInstance().getReference("Pets/pets").child(petId);
        petRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pet = snapshot.getValue(Pet.class);
                if (pet != null) {
                    initViews();  // Initialize views with the loaded pet data
                } else {
                    Log.e("PetInfoActivity", "Failed to retrieve pet data for petId: " + petId);
                    finish();  // Close the activity if the pet is not found
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("PetInfoActivity", "Database error: " + error.getMessage());
                finish();  // Close the activity in case of a database error
            }
        });
    }

    private void initViews() {
        String imageUrl = pet.getImageUri();
        petName.setText(pet.getName());
        petAge.setText(String.valueOf(pet.getAge()));
        petSex.setText(String.valueOf(pet.getSex()));

        Glide.with(this)
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.no_image_svgrepo_com)
                .into(pet_info_img);

        setupWalkingHoursView();
        delete_pet.setOnClickListener(v -> deletePet());
        edit_general_btn.setOnClickListener(v -> editGeneralInfo());
        edit_walking_btn.setOnClickListener(v-> editWalkingInfo());
        edit_vet_btn.setOnClickListener(v-> editVetInfo());
    }

    private void editWalkingInfo() {
        Intent intent = new Intent(PetInfoActivity.this, EditPet3Activity.class);
        intent.putExtra("pet", pet);
        startActivity(intent);
    }

    private void editVetInfo() {
        Intent intent = new Intent(PetInfoActivity.this, EditPet2Activity.class);
        intent.putExtra("pet", pet);
        startActivity(intent);
    }

    private void editGeneralInfo() {
        Intent intent = new Intent(PetInfoActivity.this, EditPet1Activity.class);
        intent.putExtra("pet", pet);
        startActivity(intent);
    }


    private void deletePet() {
        PopupDialog.getInstance(this)
                .standardDialogBuilder()
                .createAlertDialog()
                .setHeading("Delete " + pet.getName() + "?")
                .setDescription("Are you sure you want to delete this pet? All the details will be lost.")
                .build(new StandardDialogActionListener() {
                    @Override
                    public void onPositiveButtonClicked(Dialog dialog) {
                        deletePetFromDatabase(pet.getId());
                        dialog.dismiss();
                    }

                    @Override
                    public void onNegativeButtonClicked(Dialog dialog) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void deletePetFromDatabase(String petId) {
        DatabaseReference petsRef = FirebaseDatabase.getInstance().getReference("Pets/pets");
        petsRef.child(petId).removeValue()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        SignalManager.getInstance().toast("Pet deleted successfully!");
                        moveToMainActivity();
                    } else {
                        SignalManager.getInstance().toast("Failed to delete pet.");
                    }
                });
    }

    private void moveToMainActivity() {
        Intent intent = new Intent(PetInfoActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void setupWalkingHoursView() {
        ArrayList<String> walkingHours = pet.getWalkingTimes();
        if (!updateWalkingDays()) {
            setupVetView();
        }
        if (walkingHours == null || walkingHours.isEmpty()) {
            walking_hours.setVisibility(View.GONE);
        } else {
            walking_hours.setVisibility(View.VISIBLE);
            no_walking_times.setVisibility(View.GONE);
            WalkingHoursAdapter walkingHoursAdapter = new WalkingHoursAdapter(walkingHours);
            walking_hours.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            walking_hours.setAdapter(walkingHoursAdapter);
            setupVetView();
        }
    }

    private void setupVetView() {
        ArrayList<VetVisit> vetVisits = pet.getVetVisits();
        if (vetVisits == null || vetVisits.isEmpty()) {
            vet_times.setVisibility(View.GONE);
            no_vet_visits.setVisibility(View.VISIBLE);
        } else {
            vet_times.setVisibility(View.VISIBLE);
            no_vet_visits.setVisibility(View.GONE);
            VetTimesAdapter vetTimesAdapter = new VetTimesAdapter(vetVisits);
            vet_times.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            vet_times.setAdapter(vetTimesAdapter);
        }
    }

    private boolean updateWalkingDays() {
        ArrayList<WalkingDay> walkingDays = pet.getWalkingDays();
        if (walkingDays == null || walkingDays.isEmpty()) {
            week_days_sec.setVisibility(View.GONE);
            walking_hours.setVisibility(View.GONE);
            no_walking_times.setVisibility(View.VISIBLE);
            return false;
        } else {
            for (WalkingDay day : walkingDays) {
                switch (day.getDay()) {
                    case SUNDAY:
                        sunday.setImageResource(R.drawable.s_selected);
                        break;
                    case MONDAY:
                        monday.setImageResource(R.drawable.m_selected);
                        break;
                    case TUESDAY:
                        tuesday.setImageResource(R.drawable.t_selected);
                        break;
                    case WEDNESDAY:
                        wednesday.setImageResource(R.drawable.w_selected);
                        break;
                    case THURSDAY:
                        thursday.setImageResource(R.drawable.t_selected);
                        break;
                    case FRIDAY:
                        friday.setImageResource(R.drawable.f_selected);
                        break;
                    case SATURDAY:
                        saturday.setImageResource(R.drawable.s_selected);
                        break;
                }
            }
            return true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPetFromDatabase(petId);
    }
}
