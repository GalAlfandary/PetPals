package com.example.petpals;

import static java.security.AccessController.getContext;

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
import com.example.petpals.Utilities.DataGenerator;
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
import com.google.gson.Gson;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private PetAdapter petAdapter;
    private MaterialTextView greeting;
    private MaterialButton sign_out_btn;
    private ImageButton add_pet_btn;
    private RecyclerView main_LST_pets;
    private PetList petList;
    private Callback_ListItemClicked callbackListItemClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
//        saveDb();
    }

    private void initViews() {

        petAdapter = new PetAdapter(petList.getPets(), new Callback_ListItemClicked() {
            @Override
            public void onListItemClicked(int position) {
                Pet pet = petList.getPets().get(position);
                Log.d("Pet info",pet.toString());
                Intent intent = new Intent(MainActivity.this, PetInfoActivity.class);
                intent.putExtra("pet", new Gson().toJson(pet));
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
        DatabaseReference petsRef = FirebaseDatabase.getInstance().getReference("Pets");
        petsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Gson gson = new Gson();
                String json = gson.toJson(snapshot.getValue());
                petList = gson.fromJson(json, PetList.class);
                if (petList != null) {
                    Log.d("db", "Fetched pet list: " + petList.toString());
                    initViews();
                } else {
                    Log.d("db", "PetList is null after parsing JSON.");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //pass
            }
        });
    }

    private void transactToAdd() {
        Intent intent =new Intent(MainActivity.this,addPet1Activity.class);
        startActivity(intent);
        //finish();
    }

    private void findViews() {
        main_LST_pets = findViewById(R.id.main_LST_pets);
        greeting  =findViewById(R.id.greeting);
        sign_out_btn =findViewById(R.id.sign_out_btn);
        add_pet_btn =findViewById(R.id.add_pet_btn);
    }

    private void signOut(){
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        transactToLogin();
                    }
                });
    }

    private void transactToLogin() {
        Intent intent =new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private String greetingText(){
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 5 && timeOfDay < 12){
            return "Good Morning! ☀️";
        }else if(timeOfDay >= 12 && timeOfDay < 17){
            return "Good Afternoon! ☀️";
        }else if(timeOfDay >= 17 && timeOfDay < 21){
            return "Good Evening! 🌅";
        }else {
            return "Good Night! 🌜";
        }
    }

    public void saveDb() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Pets");
        myRef.setValue(DataGenerator.generatePetList());
    }
    @Override
    protected void onResume() {
        super.onResume();
        updatePetsFromDB();
    }

}