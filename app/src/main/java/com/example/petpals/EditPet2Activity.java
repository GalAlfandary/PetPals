package com.example.petpals;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petpals.Adapters.VetVisitAdapter;
import com.example.petpals.Interface.ItemClickListener;
import com.example.petpals.Models.Pet;
import com.example.petpals.Models.VetVisit;
import com.example.petpals.Utilities.SignalManager;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class EditPet2Activity extends AppCompatActivity implements ItemClickListener {

    private Pet pet;
    private ArrayList<VetVisit> vetVisits;
    private RecyclerView vetVisitRecyclerView;
    private VetVisitAdapter vetVisitAdapter;
    private MaterialButton saveButton, discardButton, addVisitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pet2);

        findViews();
        loadPetData();
        setupRecyclerView();
        setOnClickListeners();
    }

    private void findViews() {
        vetVisitRecyclerView = findViewById(R.id.vet_LST);
        saveButton = findViewById(R.id.save_button);
        discardButton = findViewById(R.id.discard_button);
        addVisitButton = findViewById(R.id.add_visit_btn);
    }

    private void loadPetData() {
        pet = getIntent().getParcelableExtra("pet");
        if (pet != null) {
            vetVisits = pet.getVetVisits();
            if (vetVisits == null) {
                vetVisits = new ArrayList<>();
            }
        } else {
            vetVisits = new ArrayList<>();
        }
    }

    private void setupRecyclerView() {
        vetVisitAdapter = new VetVisitAdapter(vetVisits, this);
        vetVisitRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        vetVisitRecyclerView.setAdapter(vetVisitAdapter);
    }

    private void setOnClickListeners() {
        addVisitButton.setOnClickListener(v -> showDateTimeDialog(null, -1));
        saveButton.setOnClickListener(v -> saveChanges());
        discardButton.setOnClickListener(v -> finish());
    }

    private void showDateTimeDialog(@Nullable VetVisit vetVisit, int position) {
        final Calendar calendar = Calendar.getInstance();

        if (vetVisit != null) {
            parseDateTimeToCalendar(vetVisit, calendar);
        }
        showDatePickerDialog(calendar, vetVisit, position);
    }

    private void parseDateTimeToCalendar(VetVisit vetVisit, Calendar calendar) {
        try {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm", Locale.getDefault());

            calendar.setTime(dateFormatter.parse(vetVisit.getVisitDate()));
            Calendar timeCal = Calendar.getInstance();
            timeCal.setTime(timeFormatter.parse(vetVisit.getVisitTime()));
            calendar.set(Calendar.HOUR_OF_DAY, timeCal.get(Calendar.HOUR_OF_DAY));
            calendar.set(Calendar.MINUTE, timeCal.get(Calendar.MINUTE));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showDatePickerDialog(Calendar calendar, @Nullable VetVisit vetVisit, int position) {
        new DatePickerDialog(EditPet2Activity.this, (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            showTimePickerDialog(calendar, vetVisit, position);

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showTimePickerDialog(Calendar calendar, @Nullable VetVisit vetVisit, int position) {
        new TimePickerDialog(EditPet2Activity.this, (view, hourOfDay, minute) -> {
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);

            addOrUpdateVetVisit(calendar, vetVisit, position);

        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
    }

    private void addOrUpdateVetVisit(Calendar calendar, @Nullable VetVisit vetVisit, int position) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm", Locale.getDefault());

        String formattedDate = dateFormatter.format(calendar.getTime());
        String formattedTime = timeFormatter.format(calendar.getTime());

        if (vetVisit == null) { // Add new visit
            VetVisit newVisit = new VetVisit();
            newVisit.setVisitDate(formattedDate);
            newVisit.setVisitTime(formattedTime);
            vetVisits.add(newVisit);
        } else { // Update existing visit
            vetVisit.setVisitDate(formattedDate);
            vetVisit.setVisitTime(formattedTime);
            vetVisits.set(position, vetVisit);
        }
        vetVisitAdapter.notifyDataSetChanged();
    }

    private void saveChanges() {
        pet.setVetVisits(vetVisits);

        DatabaseReference petsRef = FirebaseDatabase.getInstance().getReference("Pets/pets");
        petsRef.child(pet.getId()).setValue(pet)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        SignalManager.getInstance().toast("Pet edited successfully!");
                        finish();
                    } else {
                        SignalManager.getInstance().toast("Failed to update pet.");
                    }
                });
    }

    @Override
    public void onCalendarButtonClick(int position, VetVisit vetVisit) {
        showDateTimeDialog(vetVisit, position);
    }

    @Override
    public void onDeleteButtonClick(int position, VetVisit vetVisit) {
        vetVisits.remove(position);
        vetVisitAdapter.notifyItemRemoved(position);
    }
}
