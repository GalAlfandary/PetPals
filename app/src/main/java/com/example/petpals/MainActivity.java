package com.example.petpals;

import static com.example.petpals.Utilities.DataGenerator.generatePetList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petpals.Interface.Callback_ListItemClicked;
import com.example.petpals.Models.Pet;
import com.example.petpals.Adapters.PetAdapter;
import com.example.petpals.Models.PetList;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private PetAdapter petAdapter;
    private MaterialTextView greeting;
    private MaterialButton sign_out_btn;
    private ImageButton add_pet_btn;
    private RecyclerView main_LST_pets;
    private ArrayList<Pet> petList;  // Store the pets list
    private ArrayList<String> petIds;  // Store the petIds list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        //saveDb();
        updatePetsFromDB();
    }

    private void initViews(ArrayList<Pet> petList, ArrayList<String> petIds) {
        petAdapter = new PetAdapter(petList, petIds, new Callback_ListItemClicked() {
            @Override
            public void onListItemClicked(int position, String petId) {
                Log.d("selected pet",petId.toString());
                Intent intent = new Intent(MainActivity.this, PetInfoActivity.class);
                intent.putExtra("petId", petId);
                startActivity(intent);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        main_LST_pets.setLayoutManager(linearLayoutManager);
        main_LST_pets.setAdapter(petAdapter);

        sign_out_btn.setOnClickListener(v -> signOut());
        greeting.setText(greetingText());
        add_pet_btn.setOnClickListener(v -> transactToAdd());
    }

    private void updatePetsFromDB() {
        DatabaseReference petsRef = FirebaseDatabase.getInstance().getReference("Pets/pets");
        petsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                petList = new ArrayList<>();
                petIds = new ArrayList<>();
                for (DataSnapshot petSnapshot : snapshot.getChildren()) {
                    Pet pet = petSnapshot.getValue(Pet.class);
                    if (pet != null) {
                        petList.add(pet);
                        petIds.add(pet.getId()); // Add the petId to the list
                    }
                }
                if (!petList.isEmpty()) {
                    Log.d("db", "Fetched pet list: " + petList.toString());
                    initViews(petList, petIds);
                } else {
                    Log.d("db", "PetList is null or empty after fetching data from Firebase.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("db", "Database error: " + error.getMessage());
            }
        });
    }

    private void transactToAdd() {
        Intent intent = new Intent(MainActivity.this, addPet1Activity.class);
        startActivity(intent);
    }

    private void findViews() {
        main_LST_pets = findViewById(R.id.main_LST_pets);
        greeting = findViewById(R.id.greeting);
        sign_out_btn = findViewById(R.id.sign_out_btn);
        add_pet_btn = findViewById(R.id.add_pet_btn);
    }

    private void signOut() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        transactToLogin();
                    }
                });
    }

    private void transactToLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private String greetingText() {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 5 && timeOfDay < 12) {
            return "Good Morning! ☀️";
        } else if (timeOfDay >= 12 && timeOfDay < 17) {
            return "Good Afternoon! ☀️";
        } else if (timeOfDay >= 17 && timeOfDay < 21) {
            return "Good Evening! 🌅";
        } else {
            return "Good Night! 🌜";
        }
    }

    public static void saveDb() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Pets/pets");
        PetList petList = generatePetList();
        for (Pet pet : petList.getPets()) {
            myRef.child(pet.getId()).setValue(pet);
        }
    }
}
