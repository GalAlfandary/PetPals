package com.example.petpals;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.example.petpals.Models.Pet;
import com.example.petpals.Utilities.SignalManager;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

public class EditPet1Activity extends AppCompatActivity {

    private TextInputEditText petNameEditText;
    private String[] genders = {"Male", "Female"};
    private AutoCompleteTextView petSexDropdown;
    private ArrayAdapter<String> genderAdapter;
    private MaterialButton datePickerButton;
    private MaterialTextView selectedDateText;
    private MaterialButton saveButton;
    private ExtendedFloatingActionButton uploadImageButton;
    private AppCompatImageView petImageView;
    private Uri selectedImageUri;
    private String selectedSex, selectedDate;
    private Pet pet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pet1);
        findViews();
        pet = getIntent().getParcelableExtra("pet");
        if (pet != null) {
            initializeGenderDropdown();
            initViews();
        }
    }

    private void findViews() {
        petNameEditText = findViewById(R.id.pet_name);
        petSexDropdown = findViewById(R.id.auto_complete_pet_sex);
        datePickerButton = findViewById(R.id.date_picker);
        selectedDateText = findViewById(R.id.date);
        uploadImageButton = findViewById(R.id.upload_pet_img);
        saveButton = findViewById(R.id.save_button);
        petImageView = findViewById(R.id.pet_img);
    }

    private void initializeGenderDropdown() {
        genderAdapter = new ArrayAdapter<>(this, R.layout.list_item, genders);
        petSexDropdown.setAdapter(genderAdapter);
        petSexDropdown.setOnItemClickListener((parent, view, position, id) -> {
            selectedSex = parent.getItemAtPosition(position).toString();
            updateSaveButtonState();
        });
    }

    private void initViews() {
        if (pet != null) {
            petNameEditText.setText(pet.getName());

            // Set sex if not null
            if (pet.getSex() != null) {
                selectedSex = pet.getSex().toString();
                petSexDropdown.setText(selectedSex, false);
            }

            // Set DOB
            selectedDateText.setText(pet.getDob());
            selectedDate = pet.getDob();

            // Load image using Glide
            if (pet.getImageUri() != null) {
                selectedImageUri = Uri.parse(pet.getImageUri());
                Glide.with(this)
                        .load(selectedImageUri)
                        .centerCrop()
                        .placeholder(R.drawable.no_image_svgrepo_com)
                        .into(petImageView);
            } else {
                petImageView.setImageResource(R.drawable.no_image_svgrepo_com);
            }
        }

        uploadImageButton.setOnClickListener(v -> ImagePicker.with(this)
                .crop(1f, 1f)
                .maxResultSize(1080, 1080)
                .start());

        datePickerButton.setOnClickListener(v -> {
            MaterialDatePicker<Long> materialDatePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select Date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build();
            materialDatePicker.addOnPositiveButtonClickListener(selection -> {
                selectedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selection);
                selectedDateText.setText(selectedDate);
            });
            materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
        });

        saveButton.setOnClickListener(v -> saveChanges());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            petImageView.setImageURI(selectedImageUri);
        }
    }

    private void saveChanges() {
        pet.setName(Objects.requireNonNull(petNameEditText.getText()).toString());
        pet.setSex(Pet.Sex.valueOf(selectedSex.toUpperCase()));
        pet.setDob(selectedDate);
        if (selectedImageUri != null) {
            pet.setImageUri(selectedImageUri.toString());
        }
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

    private void updateSaveButtonState() {
        boolean isFormValid = selectedSex != null && !selectedSex.isEmpty() && !Objects.requireNonNull(petNameEditText.getText()).toString().isEmpty();
        saveButton.setEnabled(isFormValid);
    }
}
