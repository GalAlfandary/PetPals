package com.example.petpals;

import static com.example.petpals.AddPet1Activity.DEFAULT_IMAGE_URI;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petpals.Adapters.WalkingTimesAdapter;
import com.example.petpals.Interface.WalkingTimesListener;
import com.example.petpals.Models.Pet;
import com.example.petpals.Models.VetVisit;
import com.example.petpals.Models.WalkingDay;
import com.example.petpals.Utilities.SignalManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class AddPet3Activity extends AppCompatActivity implements WalkingTimesListener {

    private Bundle bundle;
    private ShapeableImageView sunday, monday, tuesday, wednesday, thursday, friday, saturday;
    private RecyclerView walking_LST;
    private WalkingTimesAdapter walkingTimeAdapter;
    private ArrayList<String> walkingTimes = new ArrayList<>();
    private ArrayList<WalkingDay> walkingDays = new ArrayList<>();
    private HashMap<String, Boolean> selectedDaysMap = new HashMap<>();
    private ExtendedFloatingActionButton add_hour;
    private MaterialButton st_walk_button, later_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet3);
        bundle = getIntent().getExtras();
        findViews();
        initRecyclerView();
        initViews();

    }

    private void initRecyclerView() {
        walkingTimeAdapter = new WalkingTimesAdapter(walkingTimes, this);
        walking_LST = findViewById(R.id.walking_LST);
        walking_LST.setLayoutManager(new LinearLayoutManager(this));
        walking_LST.setAdapter(walkingTimeAdapter);
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
        st_walk_button = findViewById(R.id.st_walk_button);
        later_button = findViewById(R.id.later_button);
    }

    private void initViews() {
        initDays();
        add_hour.setOnClickListener(v -> showTimePickerDialog(null, -1));
        st_walk_button.setOnClickListener(v -> savePetWithWalkingData());
        later_button.setOnClickListener(v -> savePetWithoutWalkingData());
        updateSetWalkingButtonState();
    }

    private void savePetWithWalkingData() {
        checkSelectedDays();
        checkSelectedTimes();
        Log.d("walking days", walkingDays.toString());

        savePetToDatabase();
    }

    private void savePetWithoutWalkingData() {
        walkingDays.clear();
        walkingTimes.clear();
        savePetToDatabase();
    }

    private void updateSetWalkingButtonState() {
        boolean isAnyDaySelected = false;
        for (Boolean isSelected : selectedDaysMap.values()) {
            if (isSelected) {
                isAnyDaySelected = true;
                break;
            }
        }
        st_walk_button.setEnabled(isAnyDaySelected);
    }

    private void initDays() {
        initDaySelector(sunday, "Sunday", R.drawable.s_init, R.drawable.s_selected);
        initDaySelector(monday, "Monday", R.drawable.m_init, R.drawable.m_selected);
        initDaySelector(tuesday, "Tuesday", R.drawable.t_init, R.drawable.t_selected);
        initDaySelector(wednesday, "Wednesday", R.drawable.w_init, R.drawable.w_selected);
        initDaySelector(thursday, "Thursday", R.drawable.t_init, R.drawable.t_selected);
        initDaySelector(friday, "Friday", R.drawable.f_init, R.drawable.f_selected);
        initDaySelector(saturday, "Saturday", R.drawable.s_init, R.drawable.s_selected);

        selectedDaysMap.put("Sunday", false);
        selectedDaysMap.put("Monday", false);
        selectedDaysMap.put("Tuesday", false);
        selectedDaysMap.put("Wednesday", false);
        selectedDaysMap.put("Thursday", false);
        selectedDaysMap.put("Friday", false);
        selectedDaysMap.put("Saturday", false);
    }

    private void uploadPetImageAndSaveData(String imageUriString, Pet newPet) {
        if (imageUriString != null && !imageUriString.equals(DEFAULT_IMAGE_URI)) {
            Uri imageUri = Uri.parse(imageUriString);
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            StorageReference petImageRef = storageRef.child("pets_images/" + newPet.getName() + ".jpg");
            UploadTask uploadTask = petImageRef.putFile(imageUri);
            uploadTask.addOnSuccessListener(taskSnapshot -> petImageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                newPet.setImageUri(uri.toString());
                savePetDataToDatabase(newPet);
            }).addOnFailureListener(e -> SignalManager.getInstance().toast("Failed to get download URL"))).addOnFailureListener(e -> SignalManager.getInstance().toast("Failed to upload image"));
        } else {
            newPet.setImageUri(DEFAULT_IMAGE_URI);
            savePetDataToDatabase(newPet);
        }
    }

    private void savePetToDatabase() {
        String name = bundle.getString("name");
        String dob = bundle.getString("date");
        String sexString = bundle.getString("sex");
        assert sexString != null;
        Pet.Sex sex = Pet.Sex.valueOf(sexString.toUpperCase());
        String imageUri = bundle.getString("imageUri");
        ArrayList<VetVisit> vetVisits = bundle.getParcelableArrayList("vetVisits");

        Pet newPet = new Pet();
        newPet.setName(name)
                .setDob(dob)
                .setSex(sex)
                .setImageUri(imageUri)
                .setVetVisits(vetVisits)
                .setWalkingDays(walkingDays)
                .setWalkingTimes(walkingTimes);

        Log.d("pet:", newPet.toString());

        uploadPetImageAndSaveData(newPet.getImageUri(), newPet);
    }

    private void savePetDataToDatabase(Pet newPet) {
        DatabaseReference petsRef = FirebaseDatabase.getInstance().getReference("Pets/pets");
        String petId = petsRef.push().getKey();
        newPet.setId(petId);
        assert petId != null;
        petsRef.child(petId).setValue(newPet)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        SignalManager.getInstance().toast("Pet added successfully!");
                        moveToMainActivity();
                    } else {
                        SignalManager.getInstance().toast("Failed to add pet.");
                    }
                });
    }


    private void moveToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    private void checkSelectedTimes() {
        ArrayList<String> times = walkingTimeAdapter.getWalkingTimes();
        for (String time : times) {
            if (!isDuplicateTime(time)) {
                walkingTimes.add(time);
            }
        }
        Log.d("walking times", walkingTimes.toString());
    }

    private boolean isDuplicateTime(String time) {
        return walkingTimes.contains(time);
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

        Log.d("walking days", walkingDays.toString());
        updateSetWalkingButtonState();
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

            // Log the selected time here, after it's set
            Log.d("walk time selected:", formattedTime);
        };

        new TimePickerDialog(AddPet3Activity.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
    }





    @Override
    public void onClockButtonClick(int position, String walkingTime) {
        showTimePickerDialog(walkingTime, position);
    }

    @Override
    public void onDeleteButtonClick(int position, String walkingTime) {
        walkingTimes.remove(position);
        walkingTimeAdapter.notifyItemRemoved(position);
    }
}