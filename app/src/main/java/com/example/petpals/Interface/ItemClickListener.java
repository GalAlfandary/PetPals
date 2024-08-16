package com.example.petpals.Interface;

import com.example.petpals.Models.VetVisit;

public interface ItemClickListener {
    void onCalendarButtonClick(int position, VetVisit vetVisit);
    void onDeleteButtonClick(int position, VetVisit vetVisit);
}
