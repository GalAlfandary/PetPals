package com.example.petpals.Models;

import androidx.annotation.NonNull;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Pet {
    public enum Sex {
        MALE, FEMALE
    }

    private String name;
    private String dob;
    private Sex sex;
    private String imageUri;
    private ArrayList<VetVisit> vetVisits = null;
    private ArrayList<WalkingDay> walkingDays = null;
    private ArrayList<String> walkingTimes = null;

    public Pet() {
    }

    public String getName() {
        return name;
    }

    public Pet setName(String name) {
        this.name = name;
        return this;
    }

    public String getDob() {
        return dob;
    }

    public Pet setDob(String dob) {
        this.dob = dob;
        return this;
    }

    public Sex getSex() {
        return sex;
    }

    public Pet setSex(Sex sex) {
        this.sex = sex;
        return this;
    }

    public ArrayList<VetVisit> getVetVisits() {
        return vetVisits;
    }

    public Pet setVetVisits(ArrayList<VetVisit> vetVisits) {
        this.vetVisits = vetVisits;
        return this;
    }

    public ArrayList<WalkingDay> getWalkingDays() {
        return walkingDays;
    }

    public Pet setWalkingDays(ArrayList<WalkingDay> walkingDays) {
        this.walkingDays = walkingDays;
        return this;
    }

    public ArrayList<String> getWalkingTimes() {
        return walkingTimes;
    }

    public Pet setWalkingTimes(ArrayList<String> walkingTimes) {
        this.walkingTimes = walkingTimes;
        return this;
    }

    public int getAge() {


        LocalDate birthDate = LocalDate.parse(dob);
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    public String getImageUri() {
        return imageUri;
    }

    public Pet setImageUri(String imageUri) {
        this.imageUri = imageUri;
        return this;
    }

    @NonNull
    @Override
    public String toString() {
        return "Pet{" +
                "name='" + name + '\'' +
                ", dob='" + dob + '\'' +
                ", sex=" + sex +
                ", imageUri='" + imageUri + '\'' +
                ", vetVisits=" + vetVisits +
                ", walkingDays=" + walkingDays +
                ", walkingTimes=" + walkingTimes +
                '}';
    }
}
