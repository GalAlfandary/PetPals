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

public class addPet1Activity extends AppCompatActivity {
    private TextInputEditText pet_name;
    private String[] gender = {"Male", "Female"};
    private AutoCompleteTextView auto_complete_pet_sex;
    private ArrayAdapter<String> adapterGender;
    private MaterialButton date_picker;
    private MaterialTextView date;
    private MaterialButton continue_button;
    private ExtendedFloatingActionButton upload_pet_img;
    private AppCompatImageView pet_img;
    public static final String DEFAULT_IMAGE_URI = "https://firebasestorage.googleapis.com/v0/b/pet-pals-100e1.appspot.com/o/pets_images%2Fno_image.jpg?alt=media&token=e688a46c-5d37-419f-aa1e-f00a9ad9599b";
    private Uri selectedImgUri;
    private String selectedSex, selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet1);
        findViews();
        initViews();
        updateContinueButtonState();

    }

    private void initViews() {

        adapterGender = new ArrayAdapter<>(this, R.layout.list_item, gender);
        auto_complete_pet_sex.setAdapter(adapterGender);
        auto_complete_pet_sex.setOnItemClickListener((parent, view, position, id) -> {
            selectedSex = parent.getItemAtPosition(position).toString();
            updateContinueButtonState();
        });
        date_picker.setOnClickListener(v -> {
            MaterialDatePicker<Long> materialDatePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select Date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build();
            materialDatePicker.addOnPositiveButtonClickListener(selection -> {
                selectedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date(selection));
                date.setText(selectedDate);
                updateContinueButtonState();
            });
            materialDatePicker.show(getSupportFragmentManager(), "tag");
        });
        continue_button.setOnClickListener(v -> {
            String petName = Objects.requireNonNull(pet_name.getText()).toString().trim();
            Intent intent = new Intent(this, AddPet2Activity.class);
            Bundle bundle = new Bundle();
            bundle.putString("name", petName);
            bundle.putString("sex", selectedSex);
            bundle.putString("date", selectedDate);
            if (selectedImgUri != null) {
                bundle.putString("imageUri", selectedImgUri.toString());
            }
            intent.putExtras(bundle);
            startActivity(intent);
        });
        upload_pet_img.setOnClickListener(v -> ImagePicker.with(this)
                .crop(1f, 1f)
                .maxResultSize(1080, 1080)
                .start());
    }

    private void findViews() {
        pet_name = findViewById(R.id.pet_name);
        auto_complete_pet_sex = findViewById(R.id.auto_complete_pet_sex);
        date_picker = findViewById(R.id.date_picker);
        date = findViewById(R.id.date);
        upload_pet_img = findViewById(R.id.upload_pet_img);
        continue_button = findViewById(R.id.continue_button);
        pet_img = findViewById(R.id.pet_img);
        selectedImgUri = Uri.parse(DEFAULT_IMAGE_URI);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            selectedImgUri = data.getData();
            pet_img.setImageURI(selectedImgUri);
            updateContinueButtonState();
        } else {
            Log.d("upload", "Error uploading image or operation canceled");
        }
    }


    private void updateContinueButtonState() {
        String petName = Objects.requireNonNull(pet_name.getText()).toString().trim();
        boolean isFormValid = !petName.isEmpty() && selectedSex != null && selectedDate != null;
        Log.d("FormState", "Name: " + petName + ", Sex: " + selectedSex + ", Date: " + selectedDate + ", Image: " + selectedImgUri.toString());
        continue_button.setEnabled(isFormValid);
    }

}