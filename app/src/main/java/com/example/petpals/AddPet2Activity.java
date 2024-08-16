package com.example.petpals;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petpals.Adapters.VetVisitAdapter;
import com.example.petpals.Interface.ItemClickListener;
import com.example.petpals.Models.VetVisit;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddPet2Activity extends AppCompatActivity implements ItemClickListener {

    private MaterialButton add_visit_btn;
    private RecyclerView vet_LST;
    private MaterialButton st_health_button, later_button;
    private ArrayList<VetVisit> vetVisits = new ArrayList<>();
    private Bundle bundle;
    private VetVisitAdapter vetVisitAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet2);
        bundle = getIntent().getExtras();
        findViews();
        initRecyclerView();
        initViews();
    }

    private void findViews() {
        add_visit_btn = findViewById(R.id.add_visit_btn);
        vet_LST = findViewById(R.id.vet_LST);
        st_health_button = findViewById(R.id.st_health_button);
        later_button = findViewById(R.id.later_button);
    }

    private void initViews() {
        add_visit_btn.setOnClickListener(v -> {
            showDateTimeDialog(null,-1);
        });

        st_health_button.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddPet3Activity.class);
            bundle.putParcelableArrayList("vetVisits", vetVisits);
            intent.putExtras(bundle);
            startActivity(intent);
        });

        later_button.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddPet3Activity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        });
        updateSetHealthButtonState();

    }

    private void updateSetHealthButtonState() {
        st_health_button.setEnabled(!vetVisits.isEmpty());
    }

    private void initRecyclerView() {
        vetVisitAdapter = new VetVisitAdapter(vetVisits,this);
        vet_LST.setLayoutManager(new LinearLayoutManager(this));
        vet_LST.setAdapter(vetVisitAdapter);
    }

    private void showDateTimeDialog(final VetVisit vetVisit, final int position) {
        final Calendar calendar = Calendar.getInstance();

        if (vetVisit != null) {
            // Parse and set the existing date and time
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

        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            TimePickerDialog.OnTimeSetListener timeSetListener = (view1, hourOfDay, minute) -> {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

                SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm", Locale.getDefault());

                String formattedDate = dateFormatter.format(calendar.getTime());
                String formattedTime = timeFormatter.format(calendar.getTime());

                if (vetVisit == null) { //add visit
                    VetVisit newVisit = new VetVisit();
                    newVisit.setVisitDate(formattedDate);
                    newVisit.setVisitTime(formattedTime);
                    vetVisits.add(newVisit);
                } else { //edit visit
                    vetVisit.setVisitDate(formattedDate);
                    vetVisit.setVisitTime(formattedTime);
                    vetVisits.set(position, vetVisit);
                }

                vetVisitAdapter.notifyDataSetChanged();
                updateSetHealthButtonState();
            };

            new TimePickerDialog(AddPet2Activity.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
        };

        new DatePickerDialog(AddPet2Activity.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    public void onCalendarButtonClick(int position, VetVisit vetVisit) {
        showDateTimeDialog(vetVisit, position);
    }

    @Override
    public void onDeleteButtonClick(int position, VetVisit vetVisit) {
        vetVisits.remove(position);
        vetVisitAdapter.notifyItemRemoved(position);
        updateSetHealthButtonState();
    }
};
