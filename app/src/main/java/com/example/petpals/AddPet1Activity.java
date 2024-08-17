package com.example.petpals;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.github.dhaval2404.imagepicker.ImagePicker;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class AddPet1Activity extends AppCompatActivity {
    private TextInputEditText petNameInput;
    private String[] genders = {"Male", "Female"};
    private AutoCompleteTextView petSexDropdown;
    private ArrayAdapter<String> genderAdapter;
    private MaterialButton datePickerButton;
    private MaterialTextView selectedDateText;
    private MaterialButton continueButton;
    private ExtendedFloatingActionButton uploadImageButton;
    private AppCompatImageView petImageView;
    public static final String DEFAULT_IMAGE_URI = "https://firebasestorage.googleapis.com/v0/b/pet-pals-100e1.appspot.com/o/pets_images%2Fno_image.jpg?alt=media&token=e688a46c-5d37-419f-aa1e-f00a9ad9599b";
    private Uri selectedImageUri;
    private String selectedSex, selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet1);
        findViews();
        initializeGenderDropdown();
        setupDatePicker();
        setupUploadImageButton();
        setupContinueButton();
        updateContinueButtonState();
    }

    private void findViews() {
        petNameInput = findViewById(R.id.pet_name);
        petSexDropdown = findViewById(R.id.auto_complete_pet_sex);
        datePickerButton = findViewById(R.id.date_picker);
        selectedDateText = findViewById(R.id.date);
        uploadImageButton = findViewById(R.id.upload_pet_img);
        continueButton = findViewById(R.id.continue_button);
        petImageView = findViewById(R.id.pet_img);
        selectedImageUri = Uri.parse(DEFAULT_IMAGE_URI); // Set default image URI
    }

    private void initializeGenderDropdown() {
        genderAdapter = new ArrayAdapter<>(this, R.layout.list_item, genders);
        petSexDropdown.setAdapter(genderAdapter);
        petSexDropdown.setOnItemClickListener((parent, view, position, id) -> {
            selectedSex = parent.getItemAtPosition(position).toString();
            updateContinueButtonState();
        });
    }

    private void setupDatePicker() {
        datePickerButton.setOnClickListener(v -> {
            MaterialDatePicker<Long> materialDatePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select Date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build();
            materialDatePicker.addOnPositiveButtonClickListener(selection -> {
                selectedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date(selection));
                selectedDateText.setText(selectedDate);
                updateContinueButtonState();
            });
            materialDatePicker.show(getSupportFragmentManager(), "tag");
        });
    }

    private void setupUploadImageButton() {
        uploadImageButton.setOnClickListener(v -> ImagePicker.with(this)
                .crop(1f, 1f)
                .maxResultSize(1080, 1080)
                .start());
    }

    private void setupContinueButton() {
        continueButton.setOnClickListener(v -> {
            String petName = Objects.requireNonNull(petNameInput.getText()).toString().trim();
            Intent intent = new Intent(this, AddPet2Activity.class);
            Bundle bundle = new Bundle();
            bundle.putString("name", petName);
            bundle.putString("sex", selectedSex);
            bundle.putString("date", selectedDate);
            if (selectedImageUri != null) {
                bundle.putString("imageUri", selectedImageUri.toString());
            }
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }

    private void updateContinueButtonState() {
        String petName = Objects.requireNonNull(petNameInput.getText()).toString().trim();
        boolean isFormValid = !petName.isEmpty() && selectedSex != null && selectedDate != null && selectedImageUri != null;
        assert selectedImageUri != null;
        Log.d("FormState", "Name: " + petName + ", Sex: " + selectedSex + ", Date: " + selectedDate + ", Image: " + selectedImageUri);
        continueButton.setEnabled(isFormValid);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        handleImageResult(resultCode, data);
    }

    private void handleImageResult(int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            petImageView.setImageURI(selectedImageUri);
            updateContinueButtonState();
        } else {
            Log.d("upload", "Error uploading image or operation canceled");
        }
    }
}
