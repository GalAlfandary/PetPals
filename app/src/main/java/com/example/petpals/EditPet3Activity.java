package com.example.petpals;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petpals.Adapters.WalkingTimesAdapter;
import com.example.petpals.Interface.WalkingTimesListener;
import com.example.petpals.Models.Pet;
import com.example.petpals.Models.WalkingDay;
import com.example.petpals.Utilities.SignalManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class EditPet3Activity extends AppCompatActivity implements WalkingTimesListener {

    private Pet pet;
    private ShapeableImageView sunday, monday, tuesday, wednesday, thursday, friday, saturday;
    private RecyclerView walking_LST;
    private WalkingTimesAdapter walkingTimeAdapter;
    private ArrayList<String> walkingTimes;
    private ArrayList<WalkingDay> walkingDays;
    private HashMap<String, Boolean> selectedDaysMap = new HashMap<>();
    private ExtendedFloatingActionButton add_hour;
    private MaterialButton saveButton, discardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pet3);
        pet = getIntent().getParcelableExtra("pet");
        if (pet != null) {
            walkingTimes = pet.getWalkingTimes() != null ? new ArrayList<>(pet.getWalkingTimes()) : new ArrayList<>();
            walkingDays = pet.getWalkingDays() != null ? new ArrayList<>(pet.getWalkingDays()) : new ArrayList<>();
        }

        findViews();
        initRecyclerView();
        initViews();
    }

    private void findViews() {
        sunday = findViewById(R.id.sunday);
        monday = findViewById(R.id.monday);
        tuesday = findViewById(R.id.tuesday);
        wednesday = findViewById(R.id.wednesday);
        thursday = findViewById(R.id.thursday);
        friday = findViewById(R.id.friday);
        saturday = findViewById(R.id.saturday);
        walking_LST = findViewById(R.id.walking_LST);
        add_hour = findViewById(R.id.add_hour);
        saveButton = findViewById(R.id.save_button);
        discardButton = findViewById(R.id.discard_button);
    }

    private void initRecyclerView() {
        walkingTimeAdapter = new WalkingTimesAdapter(walkingTimes, this);
        walking_LST.setLayoutManager(new LinearLayoutManager(this));
        walking_LST.setAdapter(walkingTimeAdapter);
    }

    private void initViews() {
        initDays();
        add_hour.setOnClickListener(v -> showTimePickerDialog(null, -1));
        saveButton.setOnClickListener(v -> saveChanges());
        discardButton.setOnClickListener(v -> finish());
        updateSetWalkingButtonState();
    }

    private void saveChanges() {
        checkSelectedDays();
        pet.setWalkingDays(walkingDays);
        pet.setWalkingTimes(walkingTimes);

        DatabaseReference petsRef = FirebaseDatabase.getInstance().getReference("Pets/pets");
        petsRef.child(pet.getId()).setValue(pet)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent();
                        intent.putExtra("updatedPet", pet);
                        setResult(RESULT_OK, intent);
                        SignalManager.getInstance().toast("Pet edited successfully!");
                        finish();
                    } else {
                        SignalManager.getInstance().toast("Failed to update pet.");
                    }
                });
    }


    private void updateSetWalkingButtonState() {
        saveButton.setEnabled(!walkingDays.isEmpty() || !walkingTimes.isEmpty());
    }

    private void initDays() {
        for (WalkingDay day : walkingDays) {
            switch (day.getDay()) {
                case SUNDAY:
                    selectedDaysMap.put("Sunday", true);
                    sunday.setImageResource(R.drawable.s_selected);
                    break;
                case MONDAY:
                    selectedDaysMap.put("Monday", true);
                    monday.setImageResource(R.drawable.m_selected);
                    break;
                case TUESDAY:
                    selectedDaysMap.put("Tuesday", true);
                    tuesday.setImageResource(R.drawable.t_selected);
                    break;
                case WEDNESDAY:
                    selectedDaysMap.put("Wednesday", true);
                    wednesday.setImageResource(R.drawable.w_selected);
                    break;
                case THURSDAY:
                    selectedDaysMap.put("Thursday", true);
                    thursday.setImageResource(R.drawable.t_selected);
                    break;
                case FRIDAY:
                    selectedDaysMap.put("Friday", true);
                    friday.setImageResource(R.drawable.f_selected);
                    break;
                case SATURDAY:
                    selectedDaysMap.put("Saturday", true);
                    saturday.setImageResource(R.drawable.s_selected);
                    break;
            }
        }

        initDaySelector(sunday, "Sunday", R.drawable.s_init, R.drawable.s_selected);
        initDaySelector(monday, "Monday", R.drawable.m_init, R.drawable.m_selected);
        initDaySelector(tuesday, "Tuesday", R.drawable.t_init, R.drawable.t_selected);
        initDaySelector(wednesday, "Wednesday", R.drawable.w_init, R.drawable.w_selected);
        initDaySelector(thursday, "Thursday", R.drawable.t_init, R.drawable.t_selected);
        initDaySelector(friday, "Friday", R.drawable.f_init, R.drawable.f_selected);
        initDaySelector(saturday, "Saturday", R.drawable.s_init, R.drawable.s_selected);
    }

    private void checkSelectedDays() {
        walkingDays.clear();

        if (Boolean.TRUE.equals(selectedDaysMap.getOrDefault("Sunday", false))) {
            walkingDays.add(new WalkingDay().setDay(WalkingDay.DayOfWeek.SUNDAY));
        }
        if (Boolean.TRUE.equals(selectedDaysMap.getOrDefault("Monday", false))) {
            walkingDays.add(new WalkingDay().setDay(WalkingDay.DayOfWeek.MONDAY));
        }
        if (Boolean.TRUE.equals(selectedDaysMap.getOrDefault("Tuesday", false))) {
            walkingDays.add(new WalkingDay().setDay(WalkingDay.DayOfWeek.TUESDAY));
        }
        if (Boolean.TRUE.equals(selectedDaysMap.getOrDefault("Wednesday", false))) {
            walkingDays.add(new WalkingDay().setDay(WalkingDay.DayOfWeek.WEDNESDAY));
        }
        if (Boolean.TRUE.equals(selectedDaysMap.getOrDefault("Thursday", false))) {
            walkingDays.add(new WalkingDay().setDay(WalkingDay.DayOfWeek.THURSDAY));
        }
        if (Boolean.TRUE.equals(selectedDaysMap.getOrDefault("Friday", false))) {
            walkingDays.add(new WalkingDay().setDay(WalkingDay.DayOfWeek.FRIDAY));
        }
        if (Boolean.TRUE.equals(selectedDaysMap.getOrDefault("Saturday", false))) {
            walkingDays.add(new WalkingDay().setDay(WalkingDay.DayOfWeek.SATURDAY));
        }

        updateSetWalkingButtonState();
    }

    private void showTimePickerDialog(@Nullable final String walkingTime, final int position) {
        final Calendar calendar = Calendar.getInstance();
        if (walkingTime != null) {
            try {
                SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm", Locale.getDefault());
                calendar.setTime(timeFormatter.parse(walkingTime));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        TimePickerDialog.OnTimeSetListener timeSetListener = (view, hourOfDay, minute) -> {
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);
            SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm", Locale.getDefault());
            String formattedTime = timeFormatter.format(calendar.getTime());

            if (walkingTime == null) { // Add new walking time
                if (!walkingTimes.contains(formattedTime)) {
                    walkingTimes.add(formattedTime);
                }
            } else { // Update existing walking time
                walkingTimes.set(position, formattedTime);
            }
            walkingTimeAdapter.notifyDataSetChanged();
            updateSetWalkingButtonState();
        };

        new TimePickerDialog(EditPet3Activity.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
    }

    private void initDaySelector(ShapeableImageView dayImageView, String day, int initDrawableResId, int selectedDrawableResId) {
        dayImageView.setOnClickListener(v -> {
            boolean isSelected = Boolean.TRUE.equals(selectedDaysMap.getOrDefault(day, false));
            if (isSelected) {
                selectedDaysMap.put(day, false);
                dayImageView.setImageResource(initDrawableResId);
            } else {
                selectedDaysMap.put(day, true);
                dayImageView.setImageResource(selectedDrawableResId);
            }
            updateSetWalkingButtonState();
        });
    }

    @Override
    public void onClockButtonClick(int position, String walkingTime) {
        showTimePickerDialog(walkingTime, position);
    }

    @Override
    public void onDeleteButtonClick(int position, String walkingTime) {
        walkingTimes.remove(position);
        walkingTimeAdapter.notifyItemRemoved(position);
        updateSetWalkingButtonState();
    }
}
